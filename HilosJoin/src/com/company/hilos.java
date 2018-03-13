package com.company;

public class hilos extends Thread {

    String s;

    public hilos(String s) {
        this.s=s;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo"+ s);
        }
    }
}
