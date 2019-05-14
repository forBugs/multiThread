package com.buct.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/5/6 10:54
 * @Description:
 */
public class ThreadPoolExecutorLongTimeTask {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0,10).boxed().forEach(i->{
            executorService.execute(()->{
                while (true) {

                }
            });
        });

        executorService.shutdownNow();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}
