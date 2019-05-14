package com.buct.currencybeauty.chapter10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: fb
 * @Date: 2019/5/9 14:49
 * @Description: CountDownLatch使用案例
 */
public class CountDownLatchDemo {

    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            System.out.println("threadOne over---------------");
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            System.out.println("threadTwo over---------");
        }).start();

        System.out.println("wait child thread over");
        countDownLatch.await();
        System.out.println("main:" + "the child thread done");
    }
}
