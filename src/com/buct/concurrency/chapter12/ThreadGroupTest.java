package com.buct.concurrency.chapter12;

import java.util.Optional;

/**
 * @Auther: fb
 * @Date: 2019/3/26 21:05
 * @Description: 线程组
 */
public class ThreadGroupTest {

    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("tg1");

        Thread t1 = new Thread(tg1,"t1") {
            @Override
            public void run() {
                System.out.println(this.getThreadGroup().getName());
                System.out.println(this.getThreadGroup().getParent());

                try {
                    Thread.sleep(10_1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup("tg2");
        Thread t2 = new Thread(tg2,"t2") {
            @Override
            public void run() {
                System.out.println(this.getThreadGroup().getName());
                System.out.println(this.getThreadGroup().getParent());
                System.out.println(t1.getThreadGroup().getName());

            }
        };

        t2.start();
    }
}
