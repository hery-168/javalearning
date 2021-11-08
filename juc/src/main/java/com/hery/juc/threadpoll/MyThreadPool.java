package com.hery.juc.threadpoll;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heng
 * @date 2020-04-11 10:48
 * @desc 自定义线程池
 * https://www.jianshu.com/p/d7d41e1ae40d
 */
public class MyThreadPool {
    public static void main(String[] args) {
        /**手写线程池
         * @param corePoolSize     核心池大小 int
         * @param maximumPoolSize  最大池大小 int
         * @param keepAliveTime    保活时间   long（任务完成后要销毁的延时）
         * @param unit             时间单位    决定参数3的单位，枚举类型的时间单位
         * @param workQueue        工作队列    用于存储任务的工作队列（BlockingQueue接口类型）
         * @param threadFactory    线程工厂    用于创建线程
         *
         *线程不是越多越好，google工程师推荐  线程个数=cpu核心数+1（例如四核的开5个线程最好）
         * */
        // 参数任务上限
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(100);
        ThreadFactory threadFactory = new ThreadFactory() {
            //  int i = 0;  用并发安全的包装类
            AtomicInteger atomicInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                //创建线程 吧任务传进来
                Thread thread = new Thread(r);
                // 给线程起个名字
                thread.setName("MyThread" + atomicInteger.getAndIncrement());
                return thread;
            }
        };
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, blockingQueue, threadFactory);
        for (int i = 0; i < 100; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        method();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 编写代码（如下文的函数）同时只允许五个线程并发访问：
     * <p>
     * 1 若果使用 synchronized的话避免了并发安全问题，但是不满足题目的要求，
     * 因为题目要求一次让五个线程访问，使用锁的时候一次只能访问一个。
     * 2 解决思路：使用信号量或者使用线程池
     * <p>
     * 3 自己手动封装线程池（明白启动策略）
     */
    private static void method() throws InterruptedException {
        System.out.println("ThreadName" + Thread.currentThread().getName() + "进来了");
        Thread.sleep(2000);
        System.out.println("ThreadName" + Thread.currentThread().getName() + "出去了");
    }
}

