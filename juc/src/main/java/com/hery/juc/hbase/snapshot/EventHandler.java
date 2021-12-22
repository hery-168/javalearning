package com.hery.juc.hbase.snapshot;

/**
 * @ClassName EventHandler
 * @Description 事件处理器
 * @Date 2021/12/15 14:46
 * @Author yongheng
 * @Version V1.0
 **/
public abstract class EventHandler implements Runnable {
    /**
     * 预处理
     */
    public void prepare() {
        System.out.println("EventHandler prepare ...");
    }

    /**
     * 核心业务实现
     */
    @Override
    public void run() {
        System.out.println("EventHandler run ...");
        process();
    }

    /**
     * 等待子类去实现，用于处理真实的业务处理
     */
    public abstract void process();
}
