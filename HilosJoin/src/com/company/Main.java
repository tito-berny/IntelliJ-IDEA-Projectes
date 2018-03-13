package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Thread hilo1 = new hilos("1");
        Thread hilo2 = new hilos("2");

        hilo1.start();
        try {
            //bloquea el hilo asta k termine el otro, se puede indicar tambien el tiempo
            hilo2.join(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hilo2.start();

    }
}
