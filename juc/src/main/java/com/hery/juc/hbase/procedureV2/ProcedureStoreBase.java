package com.hery.juc.hbase.procedureV2;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName ProcedureStoreBase
 * @Description
 * @Date 2021/12/21 11:19
 * @Author yongheng
 * @Version V1.0
 **/
public abstract class ProcedureStoreBase implements ProcedureStore {
    private final AtomicBoolean running = new AtomicBoolean(false);


    protected boolean setRunning(boolean isRunning) {
        return running.getAndSet(isRunning) != isRunning;
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }
}
