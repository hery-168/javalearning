package com.hery.designpattern;

/**
 * @author heng
 * @date 2020-03-29 15:51
 * @desc 单例模式，下面的写法是最安全，最有效率的
 * 参考：https://www.cnblogs.com/codingmengmeng/p/9846131.html
 * https://blog.csdn.net/ACreazyCoder/article/details/80982578
 * 1.双重检查
 * 2.volatile 禁止指令重排序
 */
public class Singleton {
    private volatile static Singleton instance = null;// 禁止指令重排
private int age ;
    private Singleton() {
        this.age=18;
    }

    public static Singleton getInstance() {
        if (null == instance) {
            synchronized (Singleton.class) {
                if (null == instance) {
                    // instance = new Singleton(); 会翻译成下面三哥任务，可能会指令重排
                    //1给instance实例分配内存
                    //2初始化instance的构造器
                    //3将instance对象指向分配的内存空间（注意到这步时instance就非null了）
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    public int getAge(){
        return age;
    }
}
