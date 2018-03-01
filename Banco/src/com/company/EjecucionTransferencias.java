package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EjecucionTransferencias implements Runnable {

    private Banco banco;
    private int deLaCuenta;
    private double cantidadMax;

    EjecucionTransferencias(Banco b, int d, double max){

        banco = b;
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

                int paraLaCuenta = (int)(100 * Math.random());

                double cantidad = cantidadMax * Math.random();

                banco.transferencia(deLaCuenta, paraLaCuenta, cantidad);

                Thread.sleep((int)(Math.random() * 700));

            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Variable ReentrantLock nos permitira entre otras bloquear y desbloquear lineas de codigo
    public Lock cierreBanco=new ReentrantLock();

}
