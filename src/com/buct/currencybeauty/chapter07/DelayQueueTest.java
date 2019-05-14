package com.buct.currencybeauty.chapter07;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: fb
 * @Date: 2019/5/3 16:12
 * @Description: DelayQueue使用示例
 */
public class DelayQueueTest {

    public static class DelayEle implements Delayed {

        private long delayTime;
        private long expire;
        private String taskName;

        public DelayEle(long delayTime, String taskName) {
            this.delayTime = delayTime;
            this.taskName = taskName;
            this.expire = System.currentTimeMillis() + delayTime;
        }

        // 剩余过期时间：过期时间 - 当前时间
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "delayTime:" + delayTime + ", expire:" + expire + ", taskName:" + taskName;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayEle> queue = new DelayQueue<>();

        int delayTime;
        String taskName;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            delayTime = random.nextInt(500);
            taskName = "task:" + i;
            DelayEle ele = new DelayEle(delayTime, taskName);
            queue.offer(ele);
        }

        // 打印
        for (;;) {
            while (!queue.isEmpty()) {
                DelayEle take = queue.take();
                System.out.println(take);

            }
        }


        
    }

}
