package com.hery.juc.hbase.procedureV2;

/**
 * @ClassName AbstractStateMachineTableProcedure
 * @Description TODO
 * @Date 2021/12/20 18:02
 * @Author yongheng
 * @Version V1.0
 **/
public abstract class AbstractStateMachineTableProcedure<TState> extends StateMachineProcedure<MasterProcedureEnv, TState> {

    protected AbstractStateMachineTableProcedure() {
        // Required by the Procedure framework to create the procedure on replay
        //syncLatch = null;
    }
}
