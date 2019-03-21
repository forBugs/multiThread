package com.buct.concurrency.chapter6;

/**
 * 以优雅的方式结束线程
 */
public class ThreadCloseGraceful1 {

    static  class Worker extends Thread {

        private volatile boolean flag = true;
        @Override
        public void run() {
            while (flag) {
                //
//                System.out.println("worker线程工作");
            }
        }

        public void shutdown() {
            this.flag = false;
        }
    }

    public static void main(String[] args) {
        Worker t = new Worker();
        t.start();

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.shutdown();
    }
}
