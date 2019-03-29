package com.buct.concurrency.chapter10;

/**
 * @Auther: fb
 * @Date: 2019/3/25 23:03
 * @Description:
 */
public class SynchronizedProblem {

    public static void main(String[] args) {

        new Thread("t1"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
                SynchronizedProblem.run();
            }
        }.start();

        try {
            Thread.sleep(10_000  );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread("t2"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
                SynchronizedProblem.run();
            }
        }.start();
    }

    public static void run() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
