package com.buct.concurrency.chapter6;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt2 {
    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    synchronized (MONITOR) {
                        try {
                            MONITOR.wait(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.out.println("interrupterd");
                        }
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
