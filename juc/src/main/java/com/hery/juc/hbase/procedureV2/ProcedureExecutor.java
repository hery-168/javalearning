package com.hery.juc.hbase.procedureV2;


import com.hery.juc.hbase.procedureV2.Procedure.LockState;
import com.hery.juc.hbase.procedureV2.myenum.ProcedureState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName ProcedureExecutor
 * @Description TODO
 * @Date 2021/12/17 17:29
 * @Author yongheng
 * @Version V1.0
 **/
public class ProcedureExecutor<TEnvironment> {
    private static final Logger logger = LoggerFactory.getLogger(ProcedureExecutor.class);

    private final ProcedureStore store;
    private final ProcedureScheduler scheduler;

    private final AtomicLong lastProcId = new AtomicLong(-1);

    private boolean running = false;

    private ArrayList<WorkerThread> workerThreads;

    private final TEnvironment environment;

    private final ConcurrentHashMap<Long, RootProcedureState<TEnvironment>> rollbackStack = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Procedure<TEnvironment>> procedures = new ConcurrentHashMap<>();


    public ProcedureExecutor(final TEnvironment environment, final ProcedureStore store, final ProcedureScheduler scheduler) {
        this.environment = environment;
        this.store = store;
        this.scheduler = scheduler;
    }


    public void init(int numThreads) {
        workerThreads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            workerThreads.add(new WorkerThread());
        }

        // TODO_YH : 启动scheduler
        scheduler.start();
    }

    public void startWorkers() {
        running = true;

        for (WorkerThread worker : workerThreads) {
            worker.start();
        }
    }

    public TEnvironment getEnvironment() {
        return this.environment;
    }

    public long submitProcedure(Procedure proc) {
        logger.info("提交Procedure {}", proc);

        final Long currentProcId;
        currentProcId = nextProcId();

        proc.setProcId(currentProcId.longValue());

        // Commit the transaction
        store.insert(proc, null);
        logger.debug("Stored {}", proc);
        return pushProcedure(proc);
    }

    private long pushProcedure(Procedure proc) {
        final long currentProcId = proc.getProcId();
        // Submit the new subprocedures
        assert !procedures.containsKey(currentProcId);
        /********
         * TODO_YH
         *  注释： 回滚设置， rollbackStack
         *  假设一个 procedure 按照多个步骤顺序执行，则我们进行回滚的是偶，是反序进行的
         *  每次执行一个，则入栈，如果需要 rollback，从栈中，后进先出进行回滚恢复即可
         */
        // Create the rollback stack for the procedure
        RootProcedureState<TEnvironment> stack = new RootProcedureState<>();
        rollbackStack.put(currentProcId, stack);
        // TODO_YH : 维护在map结构中
        procedures.put(currentProcId, proc);

        scheduler.addBack(proc);
        return proc.getProcId();
    }

    private void executeProcedure(Procedure<TEnvironment> proc) {
        logger.info("{} is already finished, skipping execution", proc);
        // TODO_YH : 获取root proc id
        final Long rootProcId = getRootProcedureId(proc);

        if (rootProcId == null) {
            logger.warn("Rollback because parent is done/rolledback proc=" + proc);
        }
        RootProcedureState<TEnvironment> procStack = rollbackStack.get(rootProcId);
        do {

            LockState lockState = acquireLock(proc);

            switch (lockState) {
                case LOCK_ACQUIRED:

                    /********
                     * TODO_YH
                     *   注释： 执行 Procedure
                     */
                    execProcedure(procStack, proc);
                    break;

                case LOCK_YIELD_WAIT:
                    logger.info(lockState + " " + proc);
                    //scheduler.yield(proc);
                    break;
                case LOCK_EVENT_WAIT:
                    logger.debug(lockState + " " + proc);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            // TODO:  2021/12/20 19:29 @ YHeng msg: 空指针
            logger.info(" procStack.release(proc).............");
            procStack.release(proc);
            if (proc.isSuccess()) {
                // update metrics on finishing the procedure
                logger.info("Finished " + proc + " in " + System.currentTimeMillis());
                // Finalize the procedure state
                if (proc.getProcId() == rootProcId) {
                    procedureFinished(proc);
                } else {
                    // TODO:  2021/12/20 19:26 @ YHeng msg: 缺少内容，锁的释放
                    //execCompletionCleanup(proc);
                }
                break;
            }

        } while (procStack.isFailed());
    }

    private void procedureFinished(Procedure<TEnvironment> proc) {
        rollbackStack.remove(proc.getProcId());
        procedures.remove(proc.getProcId());
        // TODO:  2021/12/20 19:25 @ YHeng msg:缺少内容
    }

    private LockState acquireLock(Procedure proc) {
        return LockState.LOCK_ACQUIRED;
    }

    private void execProcedure(RootProcedureState<TEnvironment> procStack, Procedure<TEnvironment> procedure) {
        // 暂停标识
        boolean suspended = false;
        boolean reExecute = false;
        Procedure<TEnvironment>[] subprocs = null;
        do {
            try {
                reExecute = false;

                subprocs = procedure.doExecute(getEnvironment());
                if (subprocs != null && subprocs.length == 0) {
                    subprocs = null;
                }
            } catch (Exception e) {
                suspended = true;
                e.printStackTrace();
            }

            if (subprocs != null) {
                if (subprocs.length == 1 && subprocs[0] == procedure) {
                    // Procedure returned itself. Quick-shortcut for a state machine-like procedure;
                    // i.e. we go around this loop again rather than go back out on the scheduler queue.
                    subprocs = null;
                    reExecute = true;
                    logger.info("Short-circuit to next step on pid");
                } else {
                    // Yield the current procedure, and make the subprocedure runnable
                    // subprocs may come back 'null'.
                    //subprocs = initializeChildren(procStack, procedure, subprocs);
                    logger.info("Initialized subprocedures=");
                }
            } else if (procedure.getState() == ProcedureState.WAITING_TIMEOUT) {
                logger.info("Added to timeoutExecutor {}", procedure);

            } else if (!suspended) {
                // No subtask, so we are done
                procedure.setState(ProcedureState.SUCCESS);
            }


            // TODO:  2021/12/20 17:59 @ YHeng msg:

            assert (reExecute && subprocs == null) || !reExecute;
        } while (reExecute);
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (running) {
                try {
                    logger.info("WorkerThread run ...");
                    Procedure proc = scheduler.pull(100);
                    if (proc == null) {
                        logger.info("WorkerThread 暂时没有Procedure，等待 【2秒】。。。。");
                        Thread.sleep(2 * 1000);
                        continue;
                    }
                    // TODO_YH :  创建 proc
                    executeProcedure(proc);
                    logger.info("executeProcedure(proc)");
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long nextProcId() {
        long procId = lastProcId.incrementAndGet();
        if (procId < 0) {
            while (!lastProcId.compareAndSet(procId, 0)) {
                procId = lastProcId.get();
                if (procId >= 0) {
                    break;
                }
            }
            while (procedures.containsKey(procId)) {
                procId = lastProcId.incrementAndGet();
            }
        }
        assert procId >= 0 : "Invalid procId " + procId;
        return procId;
    }

    Long getRootProcedureId(Procedure<TEnvironment> proc) {
        return Procedure.getRootProcedureId(procedures, proc);
    }
}
