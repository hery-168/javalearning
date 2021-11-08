package com.hery.juc.threadpoll;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author heng
 * @date 2020-04-11 10:42
 * @desc
 */
public class ScheduledThreadPool {
    public static void main(String[] args) {
        //设置池中核心数量是2
        ScheduledExecutorService mScheduledThreadPool = Executors.newScheduledThreadPool(2);
        System.out.println("现在的时间:"+System.currentTimeMillis());


//        mScheduledThreadPool.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("现在的时间:"+System.currentTimeMillis());
//
//            }
//        }, 4, TimeUnit.SECONDS);//这里设置延迟4秒执行

        System.out.println("现在的时间:"+System.currentTimeMillis());
        mScheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("现在的时间:"+System.currentTimeMillis());
            }
        }, 2, 3,TimeUnit.SECONDS);//这里设置延迟2秒后每3秒执行一次



    }
}
