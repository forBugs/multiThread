package com.buct.currencybeauty.chapter10;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: fb
 * @Date: 2019/5/9 17:25
 * @Description: CyclicBarrier的回环重用性
 */
public class CyclicBarrierDemo2 {

    // 如果一个任务由三个阶段来完成，当多个线程执行任务时，保证所有的线程执行完第一阶段后才执行第二阶段，以此类推。。。
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("all steps is done"));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ":step1....");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + ":step2....");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + ":step3....");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ":step1....");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+":number:"+cyclicBarrier.getNumberWaiting());
                System.out.println(Thread.currentThread().getName() + ":step2....");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + ":step3....");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ":step1....");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+"count:"+cyclicBarrier.getParties());
                System.out.println(Thread.currentThread().getName()+":number:"+cyclicBarrier.getNumberWaiting());
                System.out.println(Thread.currentThread().getName() + ":step2....");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + ":step3....");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
