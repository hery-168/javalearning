package com.hery.juc.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SemaphoreDemo
 * @Description 通过Semaphore来限制系统中的用户数
 * @Date 2021/12/8 19:41
 * @Author yongheng
 * @Version V1.0
 * 参考：https://blog.csdn.net/warybee/article/details/111240729
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        //允许最大的登录数
        int slots = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(slots);
        LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        //线程池模拟登录
        for (int i = 1; i <= slots; i++) {
            final int num = i;
            executorService.execute(() -> {
                if (loginQueue.tryLogin()) {
                    System.out.println("用户:" + num + "登录成功！");
                } else {
                    System.out.println("用户:" + num + "登录失败！");
                }
            });
        }
        executorService.shutdown();


        System.out.println("当前可用许可证数：" + loginQueue.availableSlots());

        //此时已经登录了10个用户，再次登录的时候会返回false
        if (loginQueue.tryLogin()) {
            System.out.println("登录成功！");
        } else {
            System.out.println("系统登录用户已满，登录失败！");
        }
        //有用户退出登录
        loginQueue.logout();

        //再次登录
        if (loginQueue.tryLogin()) {
            System.out.println("登录成功！");
        } else {
            System.out.println("系统登录用户已满，登录失败！");
        }
    }

}


class LoginQueueUsingSemaphore {

    private Semaphore semaphore;

    /**
     * @param slotLimit
     */
    public LoginQueueUsingSemaphore(int slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    boolean tryLogin() {
        //获取一个凭证
        return semaphore.tryAcquire();
    }

    void logout() {
        semaphore.release();
    }

    int availableSlots() {
        return semaphore.availablePermits();
    }
}
