package com.buct.concurrency.chapter9;

import java.util.stream.Stream;

/**
 * @Auther: fb
 * @Date: 2019/3/22 22:04
 * @Description: 使用notifyAll和while循环解决多个解决生产者消费者线程出现的程序假死问题
 */
public class ProducerConsumerVersion3 {
    private int i = 0;
    private final Object LOCK = new Object();
    private boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {

            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i++;
            System.out.println("P->"+i);
            LOCK.notify();
            isProduced = true;

        }
    }

    public void consume() {
        synchronized (LOCK) {

            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("C->" + i);
            LOCK.notify();
            isProduced = false;

        }
    }

    public static void main(String[] args) {
        ProducerConsumerVersion3 pc = new ProducerConsumerVersion3();


        Stream.of("P1","P2","P3").forEach(n -> {
            new Thread(n){
                @Override
                public void run() {
                    while (true) {
                        pc.produce();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });

        Stream.of("C1","C2","C3","C4").forEach(n -> {
            new Thread(n){
                @Override
                public void run() {
                    while (true) {
                        pc.consume();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });


    }
}
