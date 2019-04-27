package com.buct.currencybeauty.chapter06;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: fb
 * @Date: 2019/4/26 16:15
 * @Description: LockSupport类的unpark方法可以获取线程的许可证
 */
public class LockSupportTest3 {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("child thread begin park");

            LockSupport.parkNanos(2000000000L);
//            LockSupport.park();
            System.out.println("child thread end park");
        });
        thread.start();
//        Thread.sleep(1000);

        // 使用unpark方法使thread获取到许可证；thread线程从park方法处返回
        System.out.println("main thread unpark");
//        LockSupport.unpark(thread);

    }
}
