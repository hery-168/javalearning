package com.hery.juc.hbase.procedureV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName AbstractProcedureScheduler
 * @Description TODO
 * @Date 2021/12/20 14:24
 * @Author yongheng
 * @Version V1.0
 **/
public abstract class AbstractProcedureScheduler implements ProcedureScheduler {
    private static final Logger logger = LoggerFactory.getLogger(AbstractProcedureScheduler.class);

    private boolean running = false;

    @Override
    public void addFront(Procedure proc) {
        push(proc, false);
    }

    @Override
    public void addBack(Procedure proc) {
        push(proc,false);
    }

    protected void push(Procedure proc, final boolean addFront) {
        enqueue(proc, addFront);
    }

    protected abstract void enqueue(Procedure procedure, boolean addFront);

    protected abstract Procedure dequeue();

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public Procedure pull() {
        return pull(-1);
    }

    @Override
    public Procedure pull(long timeout) {
        Procedure proc = dequeue();
        return proc;
    }
}
