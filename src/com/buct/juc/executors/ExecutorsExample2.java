package com.buct.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/5/7 09:52
 * @Description:
 */
public class ExecutorsExample2 {
    public static void main(String[] args) throws InterruptedException {
        // 获取CPU核心数
//        System.out.println(Runtime.getRuntime().availableProcessors());

        //创建ForkJoin类型的线程池
        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callableList = IntStream.range(0, 50).boxed().map((Integer i) -> {
            return (Callable<String>) () -> {
                sleep(10);
                System.out.println("thread:" + Thread.currentThread().getName());
                return "task-" + i;
            };
        }).collect(Collectors.toList());

        executorService.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }).forEach(System.out::println);





    }


    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
