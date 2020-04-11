package com.hery.thread.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author heng
 * @date 2020-04-11 10:44
 * @desc
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService mSingleThreadPool = Executors.newSingleThreadExecutor();
        for(int i = 0;i < 7;i++) {
            final int number = i;
            mSingleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("现在的时间:"+System.currentTimeMillis()+"第"+number+"个线程");
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
