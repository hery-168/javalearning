package com.hery.juc.sync;

/**
 * @ClassName ThreadShare
 * @Description 两个线程实现一个线程进行+1，另外一个线程-1，交替执行
 * a +1   b-1
 * a +1   b-1
 * a +1   b-1
 * a +1   b-1
 * @Date 2021/7/22 19:17
 * @Author yongheng
 * @Version V1.0
 * 虚假唤醒问题！！！！！ 需要注意
 * <p>
 * 下面写法会有虚假唤醒问题
 * if (number != 0) { //判断number值是否是0，如果不是0，等待
 * this.wait(); //在哪里睡，就在哪里醒
 * }
 * 下面是正确写法
 * while (number != 0) { //判断number值是否是0，如果不是0，等待
 * this.wait(); //在哪里睡，就在哪里醒
 * }
 **/
class Share {
    //第一步 创建资源类，定义属性和操作方法
    //初始值
    private int number = 0;

    //+1的方法
    public synchronized void incr() throws InterruptedException {
        //第二步 判断 干活 通知
        if (number != 0) { //判断number值是否是0，如果不是0，等待
            this.wait(); //在哪里睡，就在哪里醒
        }
        //如果number值是0，就+1操作
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }

    //-1的方法
    public synchronized void decr() throws InterruptedException {
        //判断
        while (number != 1) {
            this.wait();
        }
        //干活
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }
}

public class ThreadShare {
    //第三步 创建多个线程，调用资源类的操作方法
    public static void main(String[] args) {
        Share share = new Share();
        //创建线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr(); //+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr(); //-1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr(); //+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr(); //-1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}
