package com.buct.concurrency.chapter6;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("haha");
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Interrupt");
                    }

                }
            }
        };

        t.start();

        System.out.println(t.isInterrupted());
        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
        System.out.println(t.isInterrupted());

    }
}
