package com.buct.juc.executors;

import java.sql.Time;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: fb
 * @Date: 2019/5/8 17:38
 * @Description:
 */
public class FutureExample2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testIsDone();
        testCancel();
    }

    public static void testIsDone() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {

            throw new RuntimeException();
//            System.out.println("---------task finished------------");
//            return 10;
        });

        try {
            Integer result = future.get();
            System.out.println();
        } catch (Exception e) {
            System.out.println("is done:" + future.isDone());
        }

//        System.out.println(future.isDone());
    }

    public static void testCancel() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);

        Future<Integer> future = executorService.submit(() -> {
//            while (!Thread.interrupted()) {
//
//            }
            while (running.get()) {

            }
            System.out.println("11111");
            return 10;
        });

        TimeUnit.MILLISECONDS.sleep(50);
        System.out.println(future.isDone());
        System.out.println(future.cancel(true));
//        Integer result = future.get();
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());


    }
}
