package com.hery.juc.hbase.procedureV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @ClassName WALProcedureStore
 * @Description TODO
 * @Date 2021/12/21 11:20
 * @Author yongheng
 * @Version V1.0
 **/
public class WALProcedureStore extends ProcedureStoreBase {
    private static final Logger logger = LoggerFactory.getLogger(WALProcedureStore.class);

    private Thread syncThread;

    WALProcedureStore(final String walDir, final String walArchiveDir) {

    }

    @Override
    public void registerListener(ProcedureStoreListener listener) {

    }

    @Override
    public boolean unregisterListener(ProcedureStoreListener listener) {
        return false;
    }

    @Override
    public void start(int numThreads) throws IOException {

        if (!setRunning(true)) {
            return;
        }
        logger.info("{} 启动 start...", Thread.currentThread().getName());
        syncThread = new Thread("") {
            @Override
            public void run() {
                try {
                    syncLoop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        syncThread.start();
    }

    private void syncLoop() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(boolean abort) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getNumThreads() {
        return 0;
    }

    @Override
    public int setRunningProcedureCount(int count) {
        return 0;
    }

    @Override
    public void recoverLease() throws IOException {

    }

    @Override
    public void load(ProcedureLoader loader) throws IOException {

    }

    @Override
    public void insert(Procedure<?> proc, Procedure<?>[] subprocs) {
        try {
            long[] subProcIds = null;
            logger.info("{} 插入 进行保存proc {}", Thread.currentThread().getName(), proc);
            pushData(PushType.INSERT, null, proc.getProcId(), subProcIds);
        } catch (Exception e) {

        }

    }

    @Override
    public void insert(Procedure<?>[] procs) {
        logger.info("{} 插入 进行保存 procs {}", Thread.currentThread().getName(), procs);
    }

    public void pushData(final PushType type, final Byte slot, final long procId, final long[] subProcIds) {
        try {
            logger.info("{} pushData将数据进行持久化", Thread.currentThread().getName());
        } catch (Exception e) {

        }
    }

    @Override
    public void update(Procedure<?> proc) {

    }

    @Override
    public void delete(long procId) {

    }

    @Override
    public void delete(Procedure<?> parentProc, long[] subProcIds) {

    }

    @Override
    public void delete(long[] procIds, int offset, int count) {

    }

    private enum PushType {INSERT, UPDATE, DELETE}
}
