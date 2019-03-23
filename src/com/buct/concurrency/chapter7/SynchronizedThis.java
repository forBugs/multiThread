package com.buct.concurrency.chapter7;

/**
 * @Auther: fb
 * @Date: 2019/3/22 20:03
 * @Description: 证明this锁的存在
 */
public class SynchronizedThis {

    public static void main(String[] args) {
        ThisLock thisLock = new ThisLock();

        new Thread("t1"){
            @Override
            public void run() {
                thisLock.m1();
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                thisLock.m2();
            }
        }.start();
    }
}

class ThisLock {

    private Object LOCK = new Object();

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void m2() {
        synchronized (LOCK) {

            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
