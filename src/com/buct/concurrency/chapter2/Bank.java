package com.buct.concurrency.chapter2;

public class Bank {

    public static void main(String[] args) {
        Thread window1 = new TicketWindow("柜台1");
        window1.start();

        Thread window2 = new TicketWindow("柜台2");
        window2.start();

        Thread window3 = new TicketWindow("柜台3");
        window3.start();

    }
}
