package com.buct.concurrency.chapter1;

public class TryConcurrency {

    public static void main(String[] args) {
        new Thread("thread-1"){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"----" + i);
                }
            }
        }.start();

        new Thread("Thread-2"){
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName()+"----" + i);
                }
            }
        }.start();

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"----" + i);

        }
    }
}
