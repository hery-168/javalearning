package com.hery.juc.volatile_case;

/**
 * @ClassName DoubleCheckSingleton
 * @Description 单例模式 双重检查
 * volatile 可以保证可见性 ，禁止指令重排，但是不保证原子性 i++
 * @Date 2022/1/4 19:34
 * @Author yongheng
 * @Version V1.0
 **/
public class DoubleCheckSingleton {

    //private static  DoubleCheckSingleton instance;
    private static volatile DoubleCheckSingleton instance;

    public DoubleCheckSingleton getInstance() {
        // 第一次检查
        if (instance == null) {
            // TODO_YH : 多线程会运行到这里
            synchronized (DoubleCheckSingleton.class) {
                // 第一个线程运行到这里
                // 第二个线程运行到哲了，如果没有再次检查，或者 instance 没有被 volatile 修饰，多线程可见性问题，会直接new 对象
                if (instance == null) {// 第二次检查
                    instance = new DoubleCheckSingleton();
                }
            }
            // 第一个线程出去

        }
        return instance;
    }
}
