package com.buct.currencybeauty.chapter01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Auther: fb
 * @Date: 2019/4/11 15:42
 * @Description: 多线程
 */
public class CallerTask {

    public static class FutureJob implements Callable<String> {

        @Override
        public String call(){
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return "ok";
        }
    }

    public static void main(String[] args) {
        Callable<String> futureJob = new FutureJob();
        FutureTask<String> futureTask = new FutureTask<String>(futureJob);

        new Thread(futureTask).start();

        try {
            String s = futureTask.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
