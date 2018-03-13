package com.company;

public class HilosVarios2 extends Thread {

    Thread hilo1;
    Thread hilo2;


    public HilosVarios2(Thread hilo1, Thread hilo2) {

        this.hilo1 = hilo1;
        this.hilo2 = hilo2;
    }

    @Override
    public void run() {

        try {
            //bloquea el hilo asta k termine el otro, se puede indicar tambien el tiempo
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo 3 Hilosvarios2");
        }
    }
}
