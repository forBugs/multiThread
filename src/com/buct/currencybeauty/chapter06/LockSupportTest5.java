package com.buct.currencybeauty.chapter06;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: fb
 * @Date: 2019/4/26 17:10
 * @Description:
 */
public class LockSupportTest5 {

    @Test
    public void test() {

    }

    public void testPark() {
        LockSupport.park(this);

    }
    public static void main(String[] args) {
        LockSupportTest5 lockSupportTest5 = new LockSupportTest5();
        lockSupportTest5.testPark();
    }
}
