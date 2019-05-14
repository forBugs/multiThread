package com.buct.juc.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/5/7 17:34
 * @Description: ThreadPoolExecutor各种方法测试
 */
public class ExecutorServiceExample4 {
    public static void main(String[] args) throws InterruptedException {
//        test();
//        testCoreThreadTimeount();
//        testRemove();
//        testPreStartCoreThread();
//        testPreStartAllCoreThread();
        testAdviceExecute();
    }


    private static void test() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executorService.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executorService.getActiveCount());

        executorService.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("------I am finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executorService.getActiveCount());

        executorService.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executorService.getActiveCount());


    }

    // 使线程池中的核心线程能够自动释放回收
    public static void testCoreThreadTimeount() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        // 调用此方法可以回收空闲状态核心线程
        executorService.setKeepAliveTime(5, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);

        IntStream.range(0,2).boxed().forEach(i->{
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        });

        TimeUnit.SECONDS.sleep(5);

        System.out.println(executorService.getActiveCount());

    }

    // 删除任务队列中的某个任务(前提是此任务还没被执行)
    public static void testRemove() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        // 调用此方法可以回收空闲状态核心线程
        executorService.setKeepAliveTime(5, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);
        IntStream.range(0,2).boxed().forEach(i->{
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("I am finished--------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        });

        TimeUnit.MILLISECONDS.sleep(30);
        Runnable r = () -> {
            System.out.println("runnable will not execute");
        };

        executorService.execute(r);
        executorService.remove(r);
    }

    public static void testPreStartCoreThread() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartCoreThread());
        System.out.println(executor.getActiveCount());

        executor.execute(()-> System.out.println("---"));

        System.out.println(executor.prestartCoreThread());
        System.out.println(executor.getActiveCount());


    }

    public static void testPreStartAllCoreThread() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartAllCoreThreads());

        System.out.println(executor.getActiveCount());

        executor.execute(()-> System.out.println("---"));




    }

    public static void testAdviceExecute() {
        ExecutorService executorService = new MyThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1), r -> {
            Thread thread = new Thread(r);
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());

        executorService.execute(new MyRunnable(1) {
            @Override
            public void run() {
//                System.out.println("-----");
                int i = 1 / 0;
            }
        });


    }

    public static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        // 提交任务前的动作
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("init the no:" + ((MyRunnable) r).getData());
        }

        // 任务执行后的动作
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (t == null) {
                System.out.println("success no value:" + ((MyRunnable) r).getData());
            } else {
                t.printStackTrace();

            }
        }
    }

    public static abstract class MyRunnable implements Runnable {
        private int no;

        public MyRunnable(int no) {
            this.no = no;
        }

        public int getData() {
            return this.no;
        }
    }




}
