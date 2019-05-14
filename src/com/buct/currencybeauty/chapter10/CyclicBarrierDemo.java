package com.buct.currencybeauty.chapter10;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: fb
 * @Date: 2019/5/9 17:04
 * @Description: 回环屏障CyclicBarrier
 */
public class CyclicBarrierDemo {

    // 将一个任务分为两部分并分配两个线程去执行，当两线程执行完成对结果进行汇总
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println(Thread.currentThread().getName() + "task: merge the results");
        });

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "task-1 ");
                System.out.println(Thread.currentThread().getName() + "task-1 enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "task-1 waiting number:"+cyclicBarrier.getNumberWaiting());
                System.out.println(Thread.currentThread().getName() + "task-1 enter out barrier");


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "task-2 done");
                System.out.println(Thread.currentThread().getName() + "task-2 enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "task-2 waiting number:"+cyclicBarrier.getNumberWaiting());
                System.out.println(Thread.currentThread().getName() + "task-2 enter out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

    }
}
