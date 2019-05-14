package com.buct.currencybeauty.chapter10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther: fb
 * @Date: 2019/5/9 14:59
 * @Description:
 */
public class countDownLatchDemo2 {
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("the child thread" + j +"over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        System.out.println("waiting child thread to over");
        countDownLatch.await();
//        System.out.println(countDownLatch.getCount());
        System.out.println("child threads done");
        executorService.shutdown();

    }

}
