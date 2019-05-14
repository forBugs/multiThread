package com.buct.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/5/6 14:59
 * @Description:
 */
public class ExecutorsExample1 {

    public static void main(String[] args) {
//        useCachedThreadPool();
//        useFixedThreadPool();
        useSingleThreadPool();
    }

    /**
     * 创建单个线程的线程池
     */
    private static void useSingleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        IntStream.range(0,50).boxed().forEach((i)->{
            executorService.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "[" + i + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });


        executorService.shutdown();
    }

    /**
     * 创建固定数量的线程池
     */
    private static void useFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        IntStream.range(0,50).boxed().forEach((i)->{
            executorService.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "[" + i + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        System.out.println(((ThreadPoolExecutor) executorService).getQueue().size());

        executorService.shutdown();
    }

    /**
     * 创建按需分配的线程池
     */
    private static void useCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            System.out.println("---------");
        });


        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        IntStream.range(0,100).boxed().forEach((i)->{
            executorService.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "[" + i + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
    }


}
