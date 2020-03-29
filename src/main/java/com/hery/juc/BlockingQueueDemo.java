package com.hery.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author heng
 * @date 2020-03-29 14:59
 * @desc 阻塞队列
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(2);
    }
}
