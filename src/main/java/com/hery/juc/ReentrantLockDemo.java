package com.hery.juc;

import java.util.HashSet;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author heng
 * @date 2020-04-05 21:06
 * @desc
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        TransferQueue queue = new LinkedTransferQueue();
        HashSet hashSet = new HashSet();
    }
}
