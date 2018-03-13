package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class Banco {

    //--------------VARIABLES-----------

    //numero de cuentas en el banco
    private double[] cuentas = new double[100];
    //Variable para bloquear lineas de cofdigo
    private Lock cierreBanco=new ReentrantLock();
    //Variable de condicion para establecer que hilo queda esperando
    private Condition saldosuficiente;

    //--------------PROGRAMA-------------

    //Cnstructor, inicia 100 cuentas con un saldo de 2000 cada una
    Banco() {
        for (int i = 0; i < 100; i++) {
            cuentas[i] = 2000;
        }
        //Establecemos un bloque con una condicion y la guardamos en la varible saldosuficiente
        saldosuficiente = cierreBanco.newCondition();

    }


    /**
     * Realiza la transferencia de saldo de una cuenta 'cuentaOrigen' a otra cuenta 'cuentaDestino'.
     * @param cuentaOrigen Cuenta de origen
     * @param cuentaDestino Cuenta de destino
     * @param cantidad Cantidad de saldo a traspasar.
     */
    public void transferencia (int cuentaOrigen, int cuentaDestino, double cantidad) throws InterruptedException {

        //bloquea las lineas cuando un hilo entra
        cierreBanco.lock();

        try {


            //Comprovar que la cuenta de origen tenga saldo
            while (cuentas[cuentaOrigen] < cantidad) {
                //System.out.println("-----------------Saldo insuficiente: " + cuentaOrigen);
                // return;
                saldosuficiente.await();
            }//else {
               // System.out.println("-----------------Saldo OK: " + cuentaOrigen);

            //Imprime el hilo en ejecucion
            System.out.println(Thread.currentThread());

            //dinero que sale de la cuenta origen
            cuentas[cuentaOrigen] -= cantidad;

            System.out.printf("%10.2f de %d para %d ", cantidad, cuentaOrigen, cuentaDestino);

            cuentas[cuentaDestino] += cantidad;

            System.out.printf(" Saldo total: %10.2f%n", getSaldoBanco());

            //Despertamos el hilo dormido para comprovar en laproxima vuelta si cumple con las condiciones
            saldosuficiente.signalAll();
            
        } finally {
            //Siempre se desbloquea el codigo al salir el hilo
            cierreBanco.unlock();
        }
    }

    /**
     * Comprueva el saldo total sumando el saldo de todas las cuentas
     * @return Dooble con el saldo total
     */
    private double getSaldoBanco(){

        double total = 0;

        //Obtenemos con un for el saldo de todas las cuentas
        for (double a: cuentas) {
            total += a;
        }
        return total;
    }
}

