package com.company;

public class HilosVarios extends Thread {

    String s;

    public HilosVarios(String s) {
        this.s = s;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo"+ s);
        }
    }
}
