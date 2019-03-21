package com.buct.concurrency.chapter4;

import java.util.concurrent.TimeUnit;

public class DaemonThread1 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"--Running");
                Thread.sleep(10_000);
                System.out.println(Thread.currentThread().getName() + "---Fininshed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        t1.setDaemon(true);
        t1.start();

        System.out.println(Thread.currentThread().getName()+"--Finished");

    }
}
