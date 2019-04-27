package com.buct.currencybeauty.chapter03;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Auther: fb
 * @Date: 2019/4/24 21:20
 * @Description: 并发环境下的随机数生成器
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args) {

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(threadLocalRandom.nextInt(5));

        }
    }
}
