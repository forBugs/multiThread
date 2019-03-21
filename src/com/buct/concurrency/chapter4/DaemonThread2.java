package com.buct.concurrency.chapter4;

public class DaemonThread2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {

            Thread t2 = new Thread(()->{
                while (true) {
                    System.out.println("监控程序在后台运行");
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

//            t2.setDaemon(true);
            t2.start();
//            t2.isAlive();

            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"--Finished");
        });

        t1.start();
    }
}
