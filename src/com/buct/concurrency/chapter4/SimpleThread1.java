package com.buct.concurrency.chapter4;

import java.util.Optional;

public class SimpleThread1 {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            Optional.of("hello t1").ifPresent(System.out::println);
            try {
                Thread.sleep(1_1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        Optional.of(t1.getName()).ifPresent(System.out::println);
        Optional.of(t1.getId()).ifPresent(System.out::println);
        Optional.of(t1.getPriority()).ifPresent(System.out::println);
    }
}
