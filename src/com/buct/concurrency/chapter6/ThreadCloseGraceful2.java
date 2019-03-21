package com.buct.concurrency.chapter6;

/**
 * 使用中断的方式结束一个线程
 */
public class ThreadCloseGraceful2 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        break;
                    }

                    // 执行后面的业务逻辑

                }
            }
        };

        t.start();

        Thread.sleep(5_000);
        t.interrupt();
    }
}
