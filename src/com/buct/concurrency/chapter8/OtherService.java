package com.buct.concurrency.chapter8;

/**
 * @Auther: fb
 * @Date: 2019/3/22 20:59
 * @Description:
 */
public class OtherService {

    private DeadLock deadLock;
    private final Object lock = new Object();

    public void s1() {
        synchronized (lock) {
            System.out.println("s1");
        }
    }

    public void s2() {
        synchronized (lock) {
            System.out.println("s2");
            deadLock.m2();
        }
    }

    public void setDeadLock(DeadLock deadLock) {
        this.deadLock = deadLock;
    }
}
