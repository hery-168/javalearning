package com.hery.juc.hbase.procedureV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName MasterProcedureScheduler
 * @Description TODO
 * @Date 2021/12/20 14:34
 * @Author yongheng
 * @Version V1.0
 **/
public class MasterProcedureScheduler extends AbstractProcedureScheduler {
    private static final Logger logger = LoggerFactory.getLogger(MasterProcedureScheduler.class);


    private final Queue<Procedure> tableRunQueue = new ConcurrentLinkedQueue<Procedure>();

    public MasterProcedureScheduler() {
    }

    @Override
    protected void enqueue(Procedure proc, boolean addFront) {
        logger.info("调度，将相关procedure 添加的队列");
        toAdd(tableRunQueue, proc);
    }

    private void toAdd(Queue<Procedure> tableRunQueue, Procedure proc) {
        tableRunQueue.add(proc);
    }

    @Override
    protected Procedure dequeue() {
        Procedure pollResult = doPoll(tableRunQueue);
        return pollResult;
    }

    private Procedure doPoll(Queue<Procedure> tableRunQueue) {
        Procedure proc = tableRunQueue.poll();
        return proc;
    }


}
