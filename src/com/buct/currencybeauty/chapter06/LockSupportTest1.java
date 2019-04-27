package com.buct.currencybeauty.chapter06;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: fb
 * @Date: 2019/4/26 16:15
 * @Description: LockSupport类
 */
public class LockSupportTest1 {

    public static void main(String[] args) {
        System.out.println("main begin");

        LockSupport.park();

        System.out.println("main finish");
    }
}
