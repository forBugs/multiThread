package com.buct.concurrency.chapter13;

import java.nio.channels.ClosedSelectorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/3/28 17:26
 * @Description: 自定义简单的线程池
 */
public class SimpleThreadPool {

    private int size;

    private final int DEFAULT_SIZE = 10;

    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private static final String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";


    private static volatile int seq = 0;

    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    public SimpleThreadPool() {
        this.size = DEFAULT_SIZE;
        init();
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
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addLast(task);
            TASK_QUEUE.notifyAll();
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
                            e.printStackTrace();
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
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();

        IntStream.rangeClosed(0, 40).forEach(n -> {
            simpleThreadPool.submit(() ->{
                System.out.println("runnable " + n + "is serviced by" + Thread.currentThread() + " started");
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable" + n + "is serviced by" + Thread.currentThread() + " finished");
            });
        });

        Thread.sleep(10_000);

    }
}
