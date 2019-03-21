package com.buct.concurrency.chapter3;

public class ThreadCreate4 {
    static  int count = 0;


    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);

                } catch (Error error) {
                    error.printStackTrace();
                }
            }

            public void add(int n) {
                count++;
                add(n+1);
            }
        }, "t1");

        t1.start();
    }
}
