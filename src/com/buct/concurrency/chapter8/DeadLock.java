package com.buct.concurrency.chapter8;

/**
 * @Auther: fb
 * @Date: 2019/3/22 20:59
 * @Description: 死锁案例
 */
public class DeadLock {

    private OtherService otherService;


    private final Object LOCK = new Object();

    public DeadLock(OtherService otherService) {
        this.otherService = otherService;
    }

    public void m1() {

        synchronized (LOCK) {
            System.out.println("m1");
            otherService.s1();
        }
    }

    public void m2() {
        synchronized (LOCK) {
            System.out.println("m2");
        }
    }

}
