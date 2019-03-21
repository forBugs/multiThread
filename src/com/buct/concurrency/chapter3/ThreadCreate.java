package com.buct.concurrency.chapter3;

import java.util.Arrays;
import java.util.List;

public class ThreadCreate {

    public static void main(String[] args) {
        Thread t1 = new Thread(() ->
            System.out.println(Thread.currentThread().getName())
        , "t1");

        t1.start();

        System.out.println(Thread.currentThread().getName());
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(Thread.currentThread().getThreadGroup().activeCount());
        Thread[] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        threadGroup.enumerate(threads);

        List<Thread> threadList = Arrays.asList(threads);

        Arrays.asList(threads).forEach(System.out::println);
    }
}
