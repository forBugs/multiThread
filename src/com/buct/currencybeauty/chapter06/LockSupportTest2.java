package com.buct.currencybeauty.chapter06;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: fb
 * @Date: 2019/4/26 16:15
 * @Description: LockSupport类
 */
public class LockSupportTest2 {

    public static void main(String[] args) {
        System.out.println("begin park");

        // 让当前线程获得与LockSupport先关的许可证
        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("end park");
    }
}
