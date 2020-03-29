package com.hery.datastructure.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heng
 * @date 2020-03-27 22:29
 * @desc
 */
public class MapDemo {
    private volatile int age =0;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
//        ConcurrentHashMap map =new ConcurrentHashMap();
////        ArrayList arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();
//        Vector vector = new Vector();
//
//        atomicInteger.incrementAndGet();
//        System.out.println(atomicInteger.get());
        System.out.println(5>>1);
    }
}
