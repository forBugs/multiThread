package com.buct.currencybeauty.chapter06;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: fb
 * @Date: 2019/4/26 16:15
 * @Description: LockSupport类的unpark方法可以获取线程的许可证
 */
public class LockSupportTest4 {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park");

            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }

            System.out.println("child thread end park");
        });
        thread.start();
        Thread.sleep(2000);

        System.out.println("main thread interrupt");
        thread.interrupt();

    }
}
