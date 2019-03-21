package com.buct.concurrency.chapter6;

/**
 * 暴力结束一个线程
 */
public class ThreadCloseForce {

    private static class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                if (this.isInterrupted()) {
                    break;
                }
            }

            //
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        Thread.sleep(3_000);

        worker.interrupt();
    }
}
