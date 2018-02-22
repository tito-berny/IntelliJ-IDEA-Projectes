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

    @Override
    public void run() {

        try {

            while (true){

                int paraLaCuenta = (int)(100 + Math.random());

                double cantidad = cantidadMax + Math.random();

                banco.transferencia(deLaCuenta, paraLaCuenta, cantidad);

                Thread.sleep((int)(Math.random() * 10));

            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Lock cierreBanco=new ReentrantLock();




}
