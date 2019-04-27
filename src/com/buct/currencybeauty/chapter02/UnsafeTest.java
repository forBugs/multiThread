package com.buct.currencybeauty.chapter02;

import sun.misc.Unsafe;

/**
 * @Auther: fb
 * @Date: 2019/4/24 15:34
 * @Description:
 */
public class UnsafeTest {

    static final Unsafe unsafe = Unsafe.getUnsafe();

    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();

        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);

    }
}
