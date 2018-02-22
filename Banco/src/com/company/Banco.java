package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class Banco {

    //--------------VARIABLES-----------


    private double[] cuentas = new double[100];
    private Lock cierreBanco=new ReentrantLock();

    Banco() {
        for (int i = 0; i < 100; i++) {
            cuentas[i] = 2000;
        }
    }


    /**
     * Realiza la transferencia de saldo de una cuenta 'cuentaOrigen' a otra cuenta 'cuentaDestino'.
     * @param cuentaOrigen Cuenta de origen
     * @param cuentaDestino Cuenta de destino
     * @param cantidad Cantidad de saldo a traspasar.
     */
    public void transferencia (int cuentaOrigen, int cuentaDestino, double cantidad){

        cierreBanco.lock();

        try {


            //Comprovar que la cuenta de origen tenga saldo
            if (cuentas[cuentaOrigen] < cantidad) {
                return;
            }
            System.out.println(Thread.currentThread());

            cuentas[cuentaOrigen] -= cantidad; //dinero que sale de la cuenta origen

            System.out.printf("%10.2f de %d para %d ", cantidad, cuentaOrigen, cuentaDestino);

            cuentas[cuentaDestino] += cantidad;

            System.out.printf(" Saldo total: %10.2f%n", getSaldoBanco());
            
        } finally {
            cierreBanco.unlock();
        }
    }

    /**
     * Comprueva el saldo total sumando el saldo de todas las cuentas
     * @return Dooble con el saldo total
     */
    private double getSaldoBanco(){

        double total = 0;

        for (double a: cuentas) {
            total += a;
        }
        return total;
    }
}

