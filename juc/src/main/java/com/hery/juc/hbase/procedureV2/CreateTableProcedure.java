package com.hery.juc.hbase.procedureV2;

import com.hery.juc.hbase.procedureV2.myenum.CreateTableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName CreateTableProcedure
 * @Description TODO
 * @Date 2021/12/20 17:16
 * @Author yongheng
 * @Version V1.0
 **/
public class CreateTableProcedure extends AbstractStateMachineTableProcedure<CreateTableState> {
    private static final Logger logger = LoggerFactory.getLogger(CreateTableProcedure.class);
    private String name;

    public CreateTableProcedure(String name) {
        super();
        this.name = name;
    }

    public CreateTableProcedure() {
        // Required by the Procedure framework to create the procedure on replay
        super();
    }

    @Override
    protected Flow executeFromState(MasterProcedureEnv env, CreateTableState state) {
        logger.info("execute state="+state);
        try {
            switch (state){
                case CREATE_TABLE_PRE_OPERATION:
                    logger.info("执行 CREATE_TABLE_PRE_OPERATION 操作");
                    setNextState(CreateTableState.CREATE_TABLE_WRITE_FS_LAYOUT);
                    break;
                case CREATE_TABLE_WRITE_FS_LAYOUT:
                    logger.info("执行 CREATE_TABLE_WRITE_FS_LAYOUT 操作");
                    setNextState(CreateTableState.CREATE_TABLE_ADD_TO_META);
                    break;
                case CREATE_TABLE_ADD_TO_META:
                    logger.info("执行 CREATE_TABLE_ADD_TO_META 操作");
                    setNextState(CreateTableState.CREATE_TABLE_ASSIGN_REGIONS);
                    break;
                case CREATE_TABLE_ASSIGN_REGIONS:
                    logger.info("执行 CREATE_TABLE_ASSIGN_REGIONS 操作");
                    setNextState(CreateTableState.CREATE_TABLE_UPDATE_DESC_CACHE);
                    break;
                case CREATE_TABLE_UPDATE_DESC_CACHE:
                    logger.info("执行 CREATE_TABLE_UPDATE_DESC_CACHE 操作");
                    setNextState(CreateTableState.CREATE_TABLE_POST_OPERATION);
                    break;
                case CREATE_TABLE_POST_OPERATION:
                    logger.info("执行 CREATE_TABLE_POST_OPERATION 操作");
                    return Flow.NO_MORE_STATE;
                default:
                    throw new UnsupportedOperationException("unhandled state=" + state);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Flow.HAS_MORE_STATE;
    }

    @Override
    protected CreateTableState getState(int stateId) {
        return CreateTableState.forNumber(stateId);
    }

    @Override
    protected int getStateId(CreateTableState state) {
        return state.getNumber();
    }

    @Override
    protected CreateTableState getInitialState() {
        return CreateTableState.CREATE_TABLE_PRE_OPERATION;
    }

    @Override
    public String toString() {
        return "CreateTableProcedure{" +
                "name='" + name + '\'' +
                '}';
    }
}
