package com.buct.concurrency.chapter7;

public class TicketWindowRunnable2 implements Runnable {
    private int index = 1;

    private final static int MAX = 500;

    @Override
    public synchronized void run() {

        while (true) {

                if (index > MAX) {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " 的号码是:" + (index++));
        }

    }
}
