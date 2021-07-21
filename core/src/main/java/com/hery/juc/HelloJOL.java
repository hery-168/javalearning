package com.hery.juc;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author heng
 * @date 2020-04-04 20:44
 * @desc
 */
public class HelloJOL {
    public static void main(String[] args) {
        Object o = new Object();
       String s =  ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);

        synchronized (o){
            // 给对象加锁就是在对象的头信息上添加信息，Markword上
            String s1 =  ClassLayout.parseInstance(o).toPrintable();
            System.out.println("lock:"+s1);
        }
    }
}
