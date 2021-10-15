package com.hery.juc.reference;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @ClassName Reference
 * @Description java 中的引用
 * @Date 2021/10/15 9:59
 * @Author yongheng
 * @Version V1.0
 **/
public class Reference {
    public static void main(String[] args) throws IOException {
        //strongRef();
        //softRef();
        //weakRef();
        phantomRef();
    }

    /**
     * 不会进行回收，即使oom
     *m---强引用--->Stu
     * @throws IOException
     */
    public static void strongRef() throws IOException {
        // 强引用
        Stu s = new Stu();
        s = null;
        System.gc(); // gc 会调用s的finalize 方法
        //System.out.println(s);

        System.in.read();// 阻塞 main线程，给垃圾回收线程时间执行
    }

    /**
     * 空间够，不回收
     * 空间不够，进行回收
     * -xmx 20M
     * 适合在缓存上使用
     * <p>
     * m---强引用--->SoftReference~~~~~软引用~~~~~~byte[]
     */
    public static void softRef() {
        // TODO_YH 2021/10/15 :软引用
        // 10M
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(m.get());

        System.gc();

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(m.get());

        byte[] b = new byte[1024 * 1024 * 15];
        System.out.println(m.get());
    }

    /**
     * 弱引用 看见就gc
     * s---强引用--->WeakReference~~~~~弱引用~~~~~~Stu
     */
    public static void weakRef() {
        WeakReference<Stu> s = new WeakReference<>(new Stu());
        System.out.println(s.get());

        System.gc();

        System.out.println(s.get());

    }

    /**
     * 虚引用
     * 用于管理直接内存
     * -Xmx20M
     * s---强引用--->PhantomReference~~~~~虚引用~~~~~~Stu
     */
    private static final ReferenceQueue<Stu> queue = new ReferenceQueue<>();

    public static void phantomRef() {
        PhantomReference<Stu> p = new PhantomReference<>(new Stu(), queue);
        // null  获取不到对象，因为对象在直接内存中
        System.out.println(p.get());


    }

}

class Stu {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Stu finalize");
    }
}
