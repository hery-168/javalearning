package com.hery.juc.hbase.procedureV2;

import com.hery.juc.hbase.procedureV2.myenum.ProcedureState;

import java.util.Map;

/**
 * @ClassName Procedure
 * @Description TODO
 * @Date 2021/12/20 14:18
 * @Author yongheng
 * @Version V1.0
 **/
public abstract class Procedure<TEnvironment> {
    public static final long NO_PROC_ID = -1;

    public enum LockState {
        LOCK_ACQUIRED,       // Lock acquired and ready to execute
        LOCK_YIELD_WAIT,     // Lock not acquired, framework needs to yield
        LOCK_EVENT_WAIT,     // Lock not acquired, an event will yield the procedure
    }

    private long parentProcId = NO_PROC_ID;
    private long rootProcId = NO_PROC_ID;
    private long procId = NO_PROC_ID;

    // Runtime state, updated every operation
    private ProcedureState state = ProcedureState.INITIALIZING;

    /**
     * 抽象方法， 需要之类实现具体细节
     * @param env
     * @return
     */
    protected abstract Procedure<TEnvironment>[] execute(TEnvironment env);


    protected Procedure<TEnvironment>[] doExecute(TEnvironment env) {
        return execute(env);
    }

    protected synchronized void setState(final ProcedureState state) {
        this.state = state;
    }

    public synchronized ProcedureState getState() {
        return state;
    }

    protected void setProcId(long procId) {
        this.procId = procId;
        setState(ProcedureState.RUNNABLE);
    }

    public long getProcId() {
        return procId;
    }

    public boolean hasParent() {
        return parentProcId != NO_PROC_ID;
    }

    protected void setParentProcId(long parentProcId) {
        this.parentProcId = parentProcId;
    }

    protected void setRootProcId(long rootProcId) {
        this.rootProcId = rootProcId;
    }

    public synchronized boolean isSuccess() {
        return state == ProcedureState.SUCCESS;
    }

    public long getParentProcId() {
        return parentProcId;
    }

    protected static <T> Long getRootProcedureId(Map<Long, Procedure<T>> procedures, Procedure<T> proc) {
        while (proc.hasParent()) {
            proc = procedures.get(proc.getParentProcId());
            if (proc == null) {
                return null;
            }
        }
        return proc.getProcId();
    }

}
