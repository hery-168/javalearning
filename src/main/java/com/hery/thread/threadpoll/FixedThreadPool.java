package com.hery.thread.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author heng
 * @date 2020-04-11 10:40
 * @desc
 */
public class FixedThreadPool {
    public static void main(String[] args) {
        ExecutorService mFixedThreadPool = Executors.newFixedThreadPool(5);

        for(int i = 0;i < 7;i++ ) {
            final int index = i;
            mFixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println("时间是:"+System.currentTimeMillis()+"第" +index +"个线程" +Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }
}
