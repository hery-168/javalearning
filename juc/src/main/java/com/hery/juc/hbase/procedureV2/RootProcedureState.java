package com.hery.juc.hbase.procedureV2;

/**
 * @ClassName RootProcedureState
 * @Description TODO
 * @Date 2021/12/20 18:45
 * @Author yongheng
 * @Version V1.0
 **/
public class RootProcedureState<TEnvironment> {
    private enum State {
        RUNNING,         // The Procedure is running or ready to run
        FAILED,          // The Procedure failed, waiting for the rollback executing
        ROLLINGBACK,     // The Procedure failed and the execution was rolledback
    }
    private State state = State.RUNNING;
    private int running = 0;
    public synchronized boolean isFailed() {
        switch(state) {
            case ROLLINGBACK:
            case FAILED:
                return true;
            default:
                break;
        }
        return false;
    }
    protected synchronized void release(Procedure<TEnvironment> proc) {
        running--;
    }
}
