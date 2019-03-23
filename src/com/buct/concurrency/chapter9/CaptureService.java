package com.buct.concurrency.chapter9;

import java.util.*;

/**
 * @Auther: fb
 * @Date: 2019/3/23 22:59
 * @Description: 使用join方法结合等待通知机制实现多线程数据采集系统  但保证同一时刻最多只有5个线程在执行数据采集（实际生产中机器配置有限，线程创建以及上下文切换开销大）
 */
public class CaptureService {
    // 充当锁
    private static final LinkedList<Control> CONTROLS = new LinkedList<>();

    private static final int MAX_COUNT = 5;

    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();

        // 定义线程名  并启动各个线程
        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10").stream()
                .map(CaptureService::createThread)
                .forEach(thread -> {
                    threadList.add(thread);
                    thread.start();
                });

        // 主线程等待其他10个线程执行完毕之后再执行
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });




    }

    public static Thread createThread(String name) {
        return new Thread(()->{
            //  开始执行的时候加锁判断
            synchronized (CONTROLS) {
                Optional.of(Thread.currentThread().getName()+" begin capture data...").ifPresent(System.out::println);
                while (CONTROLS.size() > MAX_COUNT) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 放行
                CONTROLS.addLast(new Control());
            }

            // 执行逻辑  睡眠
            Optional.of(Thread.currentThread().getName()+" is working ").ifPresent(System.out::println);
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // 运行结束的时候加锁判断
            synchronized (CONTROLS) {
                Optional.of(Thread.currentThread().getName()+" capture end").ifPresent(System.out::println);
                // 通知
                CONTROLS.notifyAll();
                CONTROLS.removeFirst();
            }


        },name);
    }

    static class Control {}
}
