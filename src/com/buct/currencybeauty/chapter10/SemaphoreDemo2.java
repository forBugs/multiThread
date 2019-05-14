package com.buct.currencybeauty.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Auther: fb
 * @Date: 2019/5/9 22:37
 * @Description: Semaphore信号量使用示例二： 实现类似于CyclicBarrier的可复用功能
 */
public class SemaphoreDemo2 {

    private static volatile Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName()+":task1-1 over");
            System.out.println(Thread.currentThread().getName()+" availablePermits:"+semaphore.availablePermits());
            semaphore.release();
        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName()+":task1-2 over");
            System.out.println(Thread.currentThread().getName()+" availablePermits:"+semaphore.availablePermits());
            semaphore.release();
        });

        semaphore.acquire(2);

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName()+":task2-1 over");
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+" availablePermits:"+semaphore.availablePermits());

        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName()+":task2-2 over");
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+" availablePermits:"+semaphore.availablePermits());

        });

        semaphore.acquire(2);

        System.out.println(Thread.currentThread().getName() + ":task is over");

        executorService.shutdown();

    }
}
