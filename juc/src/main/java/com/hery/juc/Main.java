package com.hery.juc;

/**
 * 如果JVM中所有的线程都是守护线程,那么JVM就会退出，进而守护线程也会退出。
 * 如果JVM中还存在用户线程,那么JVM就会一直存活，不会退出。
 * 守护线程是依赖于用户线程，用户线程退出了，守护线程也就会退出，典型的守护线程如垃圾回收线程
 * 用户线程是独立存在的，不会因为其他用户线程退出而退出
 * 默认情况下启动的线程是用户线程,通过setDaemon(true)将线程设置成守护线程,这个函数务必在线程启动前进行调用
 */
public class Main {

    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            // 输出信息
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {
                System.out.println("守护线程心跳一次");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "aa");
        //设置守护线程 主线程结束，aa线程也结束
        aa.setDaemon(true);
        aa.start();

        System.out.println(Thread.currentThread().getName() + " over");
    }

    public void testThread() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("守护线程心跳一次");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        thread.setDaemon(true);//将该线程设置为守护线程

        thread.start();

        try {
            Thread.sleep(10000);
            Thread currentthread = Thread.currentThread();
            System.out.println("主线程" + currentthread.getName() + "退出！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testThread2() {
        Thread thread1 = new Thread(new Runnable() {


            @Override
            public void run() {
                while (true) {
                    try {
                        Thread currentthread1 = Thread.currentThread();
                        System.out.println("守护线程" + currentthread1.getName() + "心跳一次");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        thread1.setDaemon(true);//将该线程设置为守护线程
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {


            @Override
            public void run() {
                while (true) {
                    try {
                        Thread currentthread2 = Thread.currentThread();
                        System.out.println("用户线程" + currentthread2.getName() + "心跳一次");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        thread2.start();// 用户线程

        try {
            Thread.sleep(10000);
            Thread currentthread = Thread.currentThread();
            System.out.println("主线程" + currentthread.getName() + "退出！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
