package com.buct.currencybeauty.chapter01;

/**
 * @Auther: fb
 * @Date: 2019/4/19 16:18
 * @Description: ThreadLocal使用示例
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void print(String str) {
        System.out.println(str + threadLocal.get());
        threadLocal.remove();
    }

    public static void main(String[] args) {

        new Thread(()->{
            threadLocal.set("threadLocalVariable One");
            print("threadOne");
            System.out.println(threadLocal.get());
        }, "threadOne").start();


        new Thread(()->{
            threadLocal.set("threadLocalVariable Two");
            print("threadTwo");
            System.out.println(threadLocal.get());

        },"threadTwo").start();


    }
}
