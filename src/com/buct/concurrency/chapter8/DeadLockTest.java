package com.buct.concurrency.chapter8;

/**
 * @Auther: fb
 * @Date: 2019/3/22 21:16
 * @Description:
 */
public class DeadLockTest {

    public static void main(String[] args) {
        OtherService otherService = new OtherService();
        DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);

        new Thread(()->{
            while (true)
                deadLock.m1();
        },"t1").start();

        new Thread(() -> {
            while (true)
                otherService.s2();
        },"t2").start();
    }



}
