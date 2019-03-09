package com.buct.concurrency.chapter2;

public class BankNewVersion {

    public static void main(String[] args) {
        TicketWindowRunnable ticketWindowRunnable = new TicketWindowRunnable();

        // 定义线程示例
        Thread window1 = new Thread(ticketWindowRunnable, "柜台一");
        Thread window2 = new Thread(ticketWindowRunnable, "柜台二");
        Thread window3 = new Thread(ticketWindowRunnable, "柜台三");
        window1.start();
        window2.start();
        window3.start();

    }
}
