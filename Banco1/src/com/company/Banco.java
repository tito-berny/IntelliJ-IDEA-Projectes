package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class Banco {

    //--------------VARIABLES-----------

    //numero de cuentas en el banco
    private double[] cuentas = new double[100];

    //TODO IMPORTANTE
    //Variable para bloquear lineas de cofdigo
    //Bloquea un trozo de codigo para que solo pueda entrar un solo hilo a la vez
    private Lock cierreBanco=new ReentrantLock();

    //TODO IMPORTANTE
    //Variable de condicion para establecer que hilo queda esperando
    //PERMITE CREAR UN BLOQUE PERO CON UNA CONDICION
    private Condition saldosuficiente;

    //--------------PROGRAMA-------------

    //Cnstructor, inicia 100 cuentas con un saldo de 2000 cada una
    Banco() {
        for (int i = 0; i < 100; i++) {
            cuentas[i] = 2000;
        }
        //TODO IMPORTANTE
        //Establecemos un bloque con una condicion y la guardamos en la varible saldosuficiente
        //EL BLOQUEO SE ESTABLECE EN BASE A UNA CONDICION la llamamos saldosuficiente
        //Lo hacemos en el constructor para poder usarla en cualquier parte de banco
        saldosuficiente = cierreBanco.newCondition();

    }


    /**
     * Realiza la transferencia de saldo de una cuenta 'cuentaOrigen' a otra cuenta 'cuentaDestino'.
     * @param cuentaOrigen Cuenta de origen
     * @param cuentaDestino Cuenta de destino
     * @param cantidad Cantidad de saldo a traspasar.
     */
    public void transferencia (int cuentaOrigen, int cuentaDestino, double cantidad) throws InterruptedException {

        //TODO IMPORTANTE
        //VIDEO 175
        //bloquea las lineas cuando un hilo entra para que solo un hilo a la vez pueda entrar a estasa lineas
        cierreBanco.lock();

        try {

            //Comprovar que la cuenta de origen tenga saldo
            //SI NO TIENE SALDO SUFICIENTE PARA HACER LA TRANSFERENCIA PONE EL HILO A LA ESPERA
            while (cuentas[cuentaOrigen] < cantidad) {

                //PONE EL HILO A LA ESPERA Y LIBERA EL BLOQUEO DEL CODIGO
                //Video 176 - 177
                //TODO IMPORTANTE
                saldosuficiente.await();
            }

            //Imprime el hilo en ejecucion
            System.out.println(Thread.currentThread());

            //dinero que sale de la cuenta origen
            cuentas[cuentaOrigen] -= cantidad;

                System.out.printf("%10.2f de %d para %d ", cantidad, cuentaOrigen, cuentaDestino);

            cuentas[cuentaDestino] += cantidad;

            System.out.printf(" Saldo total: %10.2f%n", getSaldoBanco());

            //Despertamos el hilo dormido para comprovar en laproxima vuelta si cumple con las condiciones
            //DESPIERTA A TODOS LOS HILOS EN WAIT PARA QUE COMPRUEVEN SI CUMPLEN LA CONDICION DEL WHILE
            //Video 176 - 177
            //TODO IMPORTANTE
            saldosuficiente.signalAll();

        } finally {
            //Siempre se desbloquea el codigo al salir el hilo para que otro hilo pueda entrar en el hilo
            //cuando entre de nuevo lo bloqueara con lock nuevamente
            //TODO IMPORTANTE
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

