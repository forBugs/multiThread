package com.buct.currencybeauty.chapter01;

/**
 * @Auther: fb
 * @Date: 2019/4/24 11:04
 * @Description: ThreadLocal不支持继承性：子线程中不能访问父线程中设置的变量值
 */
public class ThreadLocalTest2 {

    //    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // 使用InheritableThreadLocal类定义的线程本地变量可以被子线程所访问到
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("main hello world");

        new Thread(()->{
            System.out.println("subThread:" + threadLocal.get());
        }, "subThread").start();

        System.out.println("main:" + threadLocal.get());


    }
}
