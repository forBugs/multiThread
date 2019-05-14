package com.buct.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: fb
 * @Date: 2019/5/7 16:37
 * @Description: 线程池的四中拒绝策略
 */
public class ExecutorServiceExample3 {
    public static void main(String[] args) throws InterruptedException {
//        testAbortPolicy();
//        testDiscardPolicy();
//        testDiscardOldestPolicy();
        testCallerRunPolicy();
    }

    // 取消当前任务并跑出异常
    public static void testAbortPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());

        for (int i= 0; i < 3; i++) {
            final int j = i;
            executorService.execute(() -> {

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task" + j + "done");
            });
        }

        executorService.execute(()-> System.out.println("the fourth task ....."));
    }

    // 抛弃当前任务但不抛出异常
    public static void testDiscardPolicy() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.DiscardPolicy());

        for (int i= 0; i < 3; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task" + j + "done");
            });
        }

        TimeUnit.SECONDS.sleep(1);

        executorService.execute(()-> System.out.println("the fourth task ....."));
        System.out.println("----------over-------------------");
    }

    // 抛弃任务队列中等待时间最久的一个队列  并将当前任务入队执行
    public static void testDiscardOldestPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        for (int i= 0; i < 3; i++) {
            final int j = i;
            executorService.execute(() -> {

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task" + j + "done");
            });
        }

        executorService.execute(()-> System.out.println("the fourth task ....."));
    }

    // 使用调用者线程（主线程处理当前任务）
    public static void testCallerRunPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i= 0; i < 3; i++) {
            final int j = i;
            executorService.execute(() -> {

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task" + j + "done" + " " + Thread.currentThread().getName());
            });
        }

        executorService.execute(() -> System.out.println("the fourth task ....." + Thread.currentThread().getName()));
    }
}
