package com.hery.juc.hbase.procedureV2;

/**
 * @ClassName Hmaster
 * @Description Hmaster
 * @Date 2021/12/20 10:16
 * @Author yongheng
 * @Version V1.0
 **/
public class Hmaster {
    private ProcedureExecutor procedureExecutor;

    public Hmaster() {
        MasterProcedureScheduler procedureScheduler = new MasterProcedureScheduler();
        WALProcedureStore walProcedureStore = new WALProcedureStore("/tmp/walDir", "/tmp/hbase/walA");
        this.procedureExecutor = new ProcedureExecutor(null,walProcedureStore, procedureScheduler);
    }

    private void startActiveMasterManager() {
        finishActiveMasterInitialization();
    }

    private void finishActiveMasterInitialization() {
        createProcedureExecutor();

        startServiceThreads();
    }

    private void createProcedureExecutor() {
        // TODO_YH : procedureExecutor 初始化 初始化 worker线程
        procedureExecutor.init(1);
    }

    private void startServiceThreads() {
        // TODO_YH :
        startProcedureExecutor();
    }

    public void startProcedureExecutor() {
        System.out.println("startProcedureExecutor");
        procedureExecutor.startWorkers();
    }

    public void create(String tableName) {
        //Procedure createProcedure= new CreateTableProcedure();
        procedureExecutor.submitProcedure(new CreateTableProcedure(tableName));
    }

    public static void main(String[] args) {
        Hmaster master = new Hmaster();
        master.startActiveMasterManager();

        for (int i = 0; i < 1; i++) {
            master.create("stu_" + i);
        }
    }

}
