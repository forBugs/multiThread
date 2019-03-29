package com.buct.concurrency.chapter13;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/3/28 17:26
 * @Description: 自定义简单的线程池
 */
public class SimpleThreadPool2 {

    private int size;


    // 任务队列的最大任务数
    private int queueSize;

    private DiscardPolicy discardPolicy;

    // 标识线程池是否已关闭
    private volatile boolean destory = false;

    private static final int DEFAULT_SIZE = 10;

    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private static volatile int seq = 0;

    private static final int DEFAULT_TASK_QUEUE_SIZE = 2000;

    private static DiscardPolicy DEFAULT_DISCARD_POLICY = new DiscardPolicy() {
        @Override
        public void discard() throws DiscardException {
            throw new DiscardException("the current task is discarded");
        }
    };

    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool2(int size, int queueSize, DiscardPolicy discardPolicy) {
        this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    SimpleThreadPool2() {
        this(DEFAULT_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            createWorkTask();
        }

        System.out.println(THREAD_QUEUE.size());
    }

    private void createWorkTask() {
        WorkerTask workerTask = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        workerTask.start();
        THREAD_QUEUE.add(workerTask);

    }


    public void submit(Runnable task) {
        if (destory)
            throw new IllegalStateException("thread pool has been shutdown");
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize)
                discardPolicy.discard();
            TASK_QUEUE.addLast(task);
            TASK_QUEUE.notifyAll();
        }
    }

    public void shutdown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }

        int initVal = THREAD_QUEUE.size();
        while (initVal > 0) {
            for (WorkerTask workerTask : THREAD_QUEUE) {
                if (workerTask.taskState == TaskState.BLOCKED) {
                    workerTask.interrupt();
                    workerTask.close();
                    initVal--;
                } else {
                    Thread.sleep(5);
                }
            }
        }


        System.out.println("the thread pool shutdown");
        destory = true;

    }

    interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    // 定义一个拒绝策略：抛出异常（任务队列满时）
    private static class DiscardException extends RuntimeException {
        DiscardException(String message) {
            super(message);
        }
    }


    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }
    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }

        public TaskState getTaskState() {
            return taskState;
        }

        @Override
        public void run() {
            outer:
            while (taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        taskState = TaskState.BLOCKED;
                        try {
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break outer;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {

                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }

            }

        }

        public void close() {
            taskState = TaskState.DEAD;
        }


    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool2 simpleThreadPool = new SimpleThreadPool2();

        IntStream.rangeClosed(0, 40).forEach(n -> {
            simpleThreadPool.submit(() ->{
                System.out.println("runnable " + n + "is serviced by" + Thread.currentThread() + " started");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable" + n + "is serviced by" + Thread.currentThread() + " finished");
            });
        });

        simpleThreadPool.shutdown();

        Thread.sleep(5_000);

    }
}
