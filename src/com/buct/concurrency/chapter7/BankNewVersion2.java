package com.buct.concurrency.chapter7;

public class BankNewVersion2 {

    public static void main(String[] args) {
        TicketWindowRunnable2 ticketWindowRunnable = new TicketWindowRunnable2();

        // 定义线程示例
        Thread window1 = new Thread(ticketWindowRunnable, "柜台一");
        Thread window2 = new Thread(ticketWindowRunnable, "柜台二");
        Thread window3 = new Thread(ticketWindowRunnable, "柜台三");
        window1.start();
        window2.start();
        window3.start();

    }
}
