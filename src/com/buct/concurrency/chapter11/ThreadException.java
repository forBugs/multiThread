package com.buct.concurrency.chapter11;

/**
 * @Auther: fb
 * @Date: 2019/3/26 20:33
 * @Description: 捕获线程运行时异常  通过t.setUncaughtExceptionHandler()方法
 */
public class ThreadException {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            int a = 10 / 0;
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.setUncaughtExceptionHandler((thread,e) -> {
            System.out.println(e);
        });

        t.start();
    }
}
