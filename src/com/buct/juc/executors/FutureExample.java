package com.buct.juc.executors;

import java.util.concurrent.*;

/**
 * @Auther: fb
 * @Date: 2019/5/8 16:48
 * @Description:
 */
public class FutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testGet();
        testGetTimeout();
    }

    // get()方法会阻塞等待直到有结果返回或被中断
    public static void testGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            return 10;
        });

        Thread mainThread = Thread.currentThread();
        new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                    mainThread.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("----------main finished---------------");
        Integer ret = future.get();
        System.out.println(ret);

    }

    // get超时方法如果超时时间已到当前线程跑出超时异常并返回，但是对应的任务仍会继续执行
    public static void testGetTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("going to work");
            return 10;
        });

        System.out.println("----------main finished---------------");
        Integer ret = future.get(2, TimeUnit.SECONDS);
        System.out.println(ret);

    }
}
