package com.buct.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/5/8 15:22
 * @Description:
 */
public class ExecutorServiceExample5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testInvokeAny();
//        testInvokeAnyTimeout();
//        testInvokeAll();
//        testInvokeAllTimeout();
//        testSubmit();
        testSubmitObject();
    }

    // invokeAll()方法是阻塞同步方法，会等待其中一个任务执行完毕并返回结果后当前线程才继续往下运行，并取消其他任务的执行
    public static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map((Integer i) -> {
            return (Callable<String>) () -> {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                System.out.println(Thread.currentThread().getName() + " " + i);
                return i + "";
            };
        }).collect(Collectors.toList());

        String any = executorService.invokeAny(callableList);
        System.out.println("---------finished------------");
        System.out.println(any);

    }

    // 给invokeAny方法设置超时时间，如果到达超时时间抛出异常
    public static void testInvokeAnyTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map((Integer i) -> {
            return (Callable<String>) () -> {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " " + i);
                return i + "";
            };
        }).collect(Collectors.toList());

        String any = executorService.invokeAny(callableList, 2, TimeUnit.SECONDS);
        System.out.println("--------finished------------");

    }

    // 提交一组任务的集合，并返回每个任务的执行结果，同步阻塞,会等待所有的任务集合执行完毕后再返回
    public static void testInvokeAll() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map((Integer i) -> {
            return (Callable<String>) () -> {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(12));
                System.out.println(Thread.currentThread().getName() + " " + i);
                return i + "";
            };
        }).collect(Collectors.toList());

        executorService.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);

        System.out.println("-------finished--------------");
    }
    public static void testInvokeAllTimeout() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map((Integer i) -> {
            return (Callable<String>) () -> {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(14));
                System.out.println(Thread.currentThread().getName() + " " + i);
                return i + "";
            };
        }).collect(Collectors.toList());

        executorService.invokeAll(callableList, 1, TimeUnit.SECONDS).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);

        System.out.println("-------finished--------------");
    }

//
    public static void testSubmit() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<?> future = executorService.submit(() -> {
            System.out.println("-------------------");
        });
        System.out.println(future.get());

    }

    public static void testSubmitObject() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(() -> System.out.println("---finished----"), "done");
        System.out.println(future.get());

    }


}
