package com.buct.concurrency.chapter9;

import java.util.stream.Stream;

/**
 * @Auther: fb
 * @Date: 2019/3/23 22:06
 * @Description: sleep和wait区别
 */
public class DifferenceOfSleepAndWait {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Stream.of("t1", "t2").forEach(name -> {
            new Thread(name){
                @Override
                public void run() {
//                    m1();
                    m2();
                }
            }.start();
        });

    }


    public static void m1() {
        synchronized (LOCK) {
            try {
                System.out.println(Thread.currentThread().getName() + "---enter");
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2() {
        synchronized (LOCK) {
            try {
                System.out.println(Thread.currentThread().getName()+"---enter");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
