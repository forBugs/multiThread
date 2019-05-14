package com.buct.currencybeauty.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: fb
 * @Date: 2019/5/9 22:22
 * @Description: Semaphore信号量的使用示例一： 主线程等待两个子线程执行完毕后再继续往下执行
 * 注： 信号量Semaphore中的计数器是递增的
 */
public class SemaphoneDemo1 {
    private static volatile Semaphore semaphore = new Semaphore(0);
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+":task-A done");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+":task-B done");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        TimeUnit.SECONDS.sleep(1);
        semaphore.acquire();


        System.out.println(Thread.currentThread().getName()+":task-A and task-B over");
        executorService.shutdown();
    }

}
