package com.buct.concurrency.chapter9;

/**
 * @Auther: fb
 * @Date: 2019/3/22 21:57
 * @Description:
 */
public class ProducerConsumerVersion1 {

    private static int i = 0;

    private final Object LOCK = new Object();

    public void produce() {
        synchronized (LOCK) {
            i++;
            System.out.println("P->"+i);
        }
    }

    public void consume() {
        synchronized (LOCK) {
            System.out.println("C->"+i);
        }
    }


    public static void main(String[] args) {
        ProducerConsumerVersion1 pc = new ProducerConsumerVersion1();

        new Thread("P"){
            @Override
            public void run() {
                while (true) {
                    pc.produce();
                }
            }
        }.start();

        new Thread("C"){
            @Override
            public void run() {
                while (true) {
                    pc.consume();
                }
            }
        }.start();
    }
}
