package com.hery.juc.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadLocalDemo
 * @Description TODO
 * 参考：https://blog.csdn.net/weixin_44075132/article/details/115543608?utm_source=app&app_version=4.5.5
 * @Date 2021/10/15 9:55
 * @Author yongheng
 * @Version V1.0
 **/
public class ThreadLocalDemo {
    // TODO_YH 2021/10/15 :线程直接是隔离的
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
            tl.set(new Person("lisi"));
            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person("zhangsan"));
        }).start();
    }

    static class Person {
        String name = "zhangsan";

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }

    }
}
