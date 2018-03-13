package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

            Thread hilo1 = new HilosVarios("1");
            Thread hilo2 = new HilosVarios("2");
            Thread hilo3 = new HilosVarios2( hilo1, hilo2);

            hilo1.start();
            hilo2.start();
            hilo3.start();

        }

}
