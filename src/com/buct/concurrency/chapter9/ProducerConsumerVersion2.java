package com.buct.concurrency.chapter9;

import java.util.stream.Stream;

/**
 * @Auther: fb
 * @Date: 2019/3/22 22:04
 * @Description: 使用wait/notify机制线程同步解决生产者消费者问题
 */
public class ProducerConsumerVersion2 {
    private int i = 0;
    private final Object LOCK = new Object();
    private boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("P->"+i);
                LOCK.notify();
                isProduced = true;
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerVersion2 pc = new ProducerConsumerVersion2();

//        new Thread("P"){
//            @Override
//            public void run() {
//                while (true) {
//                    pc.produce();
//                }
//            }
//        }.start();
//
//        new Thread("C"){
//            @Override
//            public void run() {
//                while (true) {
//                    pc.consume();
//                }
//            }
//        }.start();

        Stream.of("P1","P2").forEach(n -> {
            new Thread(n){
                @Override
                public void run() {
                    while (true) {
                        pc.produce();
                    }
                }
            }.start();
        });

        Stream.of("C1","C2").forEach(n -> {
            new Thread(n){
                @Override
                public void run() {
                    while (true) {
                        pc.consume();
                    }
                }
            }.start();
        });


    }
}
