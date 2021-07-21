package com.hery.thread.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author heng
 * @date 2020-04-11 10:38
 * @desc
 */
public class CachedThreadPool {
    public static void main(String[] args) {

        ExecutorService mCachelThreadPool = Executors.newCachedThreadPool();

        for(int i = 0;i < 7;i++ ) {
            final int index = i;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mCachelThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println("第" +index +"个线程" +Thread.currentThread().getName());
                }
            });

        }
    }
}
