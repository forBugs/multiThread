package com.buct.currencybeauty.chapter02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Auther: fb
 * @Date: 2019/4/24 15:34
 * @Description: Unsafe类提供了一些列原子性操作方法，通过CAS的方式来实现
 *  CAS: compare and swap JDK提供的非阻塞原子性操作，通过硬件实现了比较-更新的原子性， 但是容易产生ABA问题
 */
public class UnsafeTest2 {

    static final Unsafe unsafe;

    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {

            // 通过反射获取Unsafe类示例对象
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(UnsafeTest2.class.getDeclaredField("state"));

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        UnsafeTest2 test = new UnsafeTest2();

        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(success);

    }
}
