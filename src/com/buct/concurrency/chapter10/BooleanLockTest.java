package com.buct.concurrency.chapter10;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Auther: fb
 * @Date: 2019/3/25 22:01
 * @Description:
 */
public class BooleanLockTest {

    public static void main(String[] args) {

        final BooleanLock booleanLock = new BooleanLock();

        Stream.of("T1","T2","T3","T4").forEach(name ->{
            new Thread(() -> {
                try {
                    booleanLock.lock(100);
                    Optional.of(Thread.currentThread().getName() + " get the lock monitor")
                            .ifPresent(System.out::println);
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Lock.TimeOutException e) {
                    Optional.of(Thread.currentThread() + " time out").ifPresent(System.out::println);
                } finally {
                    booleanLock.unlock();
                }

            }, name).start();
        });


    }

    public static void work() {
        Optional.of(Thread.currentThread().getName() + "is working")
                .ifPresent(System.out::println);
        try {
            Thread.sleep(40_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
