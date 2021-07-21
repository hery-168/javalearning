package com.hery.thread;

/**
 * @author heng
 * @date 2020-03-31 11:12
 * @desc 多线程
 */
public class MyThread implements  Runnable{
    @Override
    public void run() {
        System.out.println("MyThread.run()");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();
    }
}
