package com.buct.currencybeauty.chapter04;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Auther: fb
 * @Date: 2019/4/26 10:45
 * @Description:
 */
public class Atomic {

    private static AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrayOne = {0, 1, 11, 22, 0, 3, 0};
    private static Integer[] arrayTwo = {2, 12, 0, 34, 55, 0, 0,};

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int size = arrayOne.length;
            for (int i = 0; i < size; i++) {
                if (arrayOne[i] == 0) {

                    System.out.println("t1:0");
                    atomicLong.incrementAndGet();
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            int size = arrayTwo.length;
            for (int i = 0; i < size; i++) {
                if (arrayOne[i] == 0) {
                    System.out.println("t2:0");
                    atomicLong.incrementAndGet();
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("result:" + atomicLong.get());

    }
}
