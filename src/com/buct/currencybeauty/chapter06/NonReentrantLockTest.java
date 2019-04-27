package com.buct.currencybeauty.chapter06;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * @Auther: fb
 * @Date: 2019/4/27 15:48
 * @Description: 使用自定义的基于AQS的独占锁实现生产者消费者模型
 */
public class NonReentrantLockTest {
    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();

    static {
        queue.add("hello");
        queue.add("world");
        queue.add("java");
    }
    private static NonReentrantLock lock = new NonReentrantLock();
    // 通过锁获取相关的两个条件变量
    private static Condition notFull = lock.newCondition();
    private static Condition notEmpty = lock.newCondition();
    // 定义队列最大容量
    private static final int MAX_SIZE = 10;

    public void produce() {
        lock.lock();
        try {
            while (queue.size() == MAX_SIZE) {
                notEmpty.await();
            }
            System.out.println("producerThread produce ele");
            queue.add("ele");
            notFull.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                notFull.await();
            }
            String ele = queue.poll();
            System.out.println("consumerThread consume:" + ele);
            notEmpty.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    public static void main(String[] args) {
        NonReentrantLockTest test = new NonReentrantLockTest();
        Thread producer = new Thread(() -> {
            while (true) {
                test.produce();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                test.consume();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
