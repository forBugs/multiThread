package com.buct.concurrency.chapter10;


import java.util.*;

/**
 * @Auther: fb
 * @Date: 2019/3/25 21:38
 * @Description: 自定义一个显示Lock锁
 */
public class BooleanLock implements Lock {

    // 处于等待状态的线程列表
    private Collection<Thread> blockedThreads = new ArrayList<Thread>();

    // 初始状态  判断锁的状态   false代表当前锁释放状态 true:被占有状态
    private boolean initialValue = false;

    // 当前获取到锁的线程
    private Thread currentThread;

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initialValue) {
            blockedThreads.add(Thread.currentThread());
            this.wait();
        }

        currentThread = Thread.currentThread();
        blockedThreads.remove(Thread.currentThread());
        initialValue = true;
    }

    @Override
    public synchronized void lock(int mills) throws InterruptedException, TimeOutException {
        if (mills <= 0)
            lock();

        long hasRemaining = mills;
        long endTime = System.currentTimeMillis() + mills;
        while (initialValue) {
            if (hasRemaining <= 0)
                throw new TimeOutException("Time out");
            blockedThreads.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining = endTime - System.currentTimeMillis();
        }

        this.initialValue = true;
        blockedThreads.remove(Thread.currentThread());
        this.currentThread = Thread.currentThread();

    }

    @Override
    public synchronized void unlock(){
        if (Thread.currentThread() == currentThread) {

            initialValue = false;
            Optional.of(Thread.currentThread().getName() + " release the lock monitor")
                    .ifPresent(System.out::println);

            this.notifyAll();

        }


    }

    @Override
    public Collection<Thread> getBlockedThread(){
        return Collections.unmodifiableCollection(blockedThreads);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreads.size();
    }

}
