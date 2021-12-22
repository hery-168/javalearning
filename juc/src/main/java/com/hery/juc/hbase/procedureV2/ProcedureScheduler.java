package com.hery.juc.hbase.procedureV2;

/**
 * @ClassName ProcedureScheduler
 * @Description 调度
 * @Date 2021/12/17 17:27
 * @Author yongheng
 * @Version V1.0
 **/
public interface ProcedureScheduler {
    /**
     * Start the scheduler
     */
    void start();

    /**
     * Stop the scheduler
     */
    void stop();

    void addFront(Procedure proc);

    void addBack(Procedure proc);

    Procedure pull();

    Procedure pull(long timeout);


}
