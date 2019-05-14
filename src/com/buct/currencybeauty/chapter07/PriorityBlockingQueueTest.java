package com.buct.currencybeauty.chapter07;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Auther: fb
 * @Date: 2019/5/3 11:21
 * @Description: 带有优先级的无解阻塞队列案例使用
 */
public class PriorityBlockingQueueTest {

    static class Task implements Comparable<Task> {

        private String taskName;
        private int priority;

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(Task o) {
            if (this.getPriority() >= o.getPriority()) {
                return 1;
            } else {
                return -1;
            }
        }

        public void doSomething() {
            System.out.println(taskName + ":" + priority);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setTaskName("task" + i);
            task.setPriority(random.nextInt(10));
            queue.offer(task);
        }

        // 遍历并输出
        while (!queue.isEmpty()) {
            Task take = queue.take();
            take.doSomething();
        }
    }
}
