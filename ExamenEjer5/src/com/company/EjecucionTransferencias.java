package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EjecucionTransferencias implements Runnable {

    private DosBancos DosBancos;
    private int deLaCuenta;
    private double cantidadMax;

    EjecucionTransferencias(DosBancos b, int d, double max){

        DosBancos = b;
        deLaCuenta = d;
        cantidadMax = max;
    }

    //Sobreescribimos el metodo run, es obligatorioal implementar Runnable
    @Override
    public void run() {

        try {

            //bucle donde definimos la cuentra de origen la de destino y la cantidad a trasferir aleatoriamente
            //Tambien dormimos el hilo 0.7s
            while (true){

                int paraLaCuenta1 = (int)(10 * Math.random());
                int paraLaCuenta2 = (int)(10 * Math.random());


                double cantidad = cantidadMax * Math.random();

                DosBancos.transferencia(deLaCuenta, paraLaCuenta1, deLaCuenta, paraLaCuenta2,  cantidad);

                Thread.sleep((int)(Math.random() * 700));

            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Variable ReentrantLock nos permitira entre otras bloquear y desbloquear lineas de codigo
    public Lock cierreBanco=new ReentrantLock();

}
