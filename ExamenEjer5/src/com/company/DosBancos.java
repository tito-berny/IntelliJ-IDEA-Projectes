package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DosBancos {

        //--------------VARIABLES-----------

        //numero de cuentas en el banco
        private double[] cuentas1 = new double[10];
        private double[] cuentas2 = new double[10];

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
        DosBancos() {
            for (int i = 0; i < 10; i++) {
                cuentas1[i] = 2000;
                cuentas2[i] = 2000;
            }
            //TODO IMPORTANTE
            //Establecemos un bloque con una condicion y la guardamos en la varible saldosuficiente
            //EL BLOQUEO SE ESTABLECE EN BASE A UNA CONDICION la llamamos saldosuficiente
            //Lo hacemos en el constructor para poder usarla en cualquier parte de banco
            saldosuficiente = cierreBanco.newCondition();

        }


    /**
     *
     * @param cuentaOrigen1
     * @param cuentaDestino1
     * @param cuentaOrigen2
     * @param cuentaDestino2
     * @param cantidad
     * @throws InterruptedException
     */
        public void transferencia (int cuentaOrigen1, int cuentaDestino1, int cuentaOrigen2, int cuentaDestino2, double cantidad) throws InterruptedException {


            /*
            //-------------------------------PUNTO 2 ----------------------------------------------

            if(cuentas1[cuentaOrigen1] < cantidad) {

                return ;
            }else{
                cuentas1[cuentaOrigen1] -= cantidad;
                cuentas1[cuentaDestino1] += cantidad;

            System.out.print(String.format("%10.2f",cantidad)) ;
            System.out.println(" Del banco 1 de la cuenta " + cuentaOrigen1 + " a la cuenta" + cuentaDestino1);
            System.out.println(Thread.currentThread());

            }
            if(cuentas2[cuentaOrigen2] < cantidad ) {

                return ;
            }else {
                cuentas2[cuentaOrigen2] -= cantidad;
                cuentas2[cuentaDestino2] += cantidad;

                System.out.print(String.format("%10.2f",cantidad)) ;
                System.out.println( " Del banco 2 de la cuenta " + cuentaOrigen2 + " a la cuenta" + cuentaDestino2);
                System.out.println(Thread.currentThread());
            }


            System.out.printf(" Saldo total: %10.2f%n", getSaldoBanco());
*/


            //------------------------PUNTO 3 y PUNTO 4 con saldosuficiente.await(); y saldosuficiente.signalAll(); ---------------------------------------------------


            //TODO IMPORTANTE
            //VIDEO 175
            //bloquea las lineas cuando un hilo entra para que solo un hilo a la vez pueda entrar a estasa lineas
            cierreBanco.lock();

            try {

                //Comprovar que la cuenta de origen tenga saldo
                //SI NO TIENE SALDO SUFICIENTE PARA HACER LA TRANSFERENCIA PONE EL HILO A LA ESPERA
                while (cuentas1[cuentaOrigen1] < cantidad ||cuentas2[cuentaOrigen2] < cantidad ) {

                    //PONE EL HILO A LA ESPERA Y LIBERA EL BLOQUEO DEL CODIGO
                    //Video 176 - 177
                    //TODO IMPORTANTE
                    saldosuficiente.await();
                    System.out.println("El hilo se Ha bloqueado a la espera de que la cuenta tenga saledo");
                    System.out.println(Thread.currentThread());
                }

                System.out.println("El hilo se a desbloqueado ");


                //Imprime el hilo en ejecucion
                System.out.println(Thread.currentThread());

                //DESCONTAMOS DE LA CUENTA ORIGEN
                //SUMAMOS A LA CUENTA DESTINO
                cuentas1[cuentaOrigen1] -= cantidad;
                cuentas1[cuentaDestino1] += cantidad;

                System.out.print(String.format("%10.2f",cantidad)) ;
                System.out.println(" Del banco 1 de la cuenta " + cuentaOrigen1 + " a la cuenta" + cuentaDestino1);
                System.out.println(Thread.currentThread());

                //DESCONTAMOS DE LA CUENTA ORIGEN
                //SUMAMOS A LA CUENTA DESTINO
                cuentas2[cuentaOrigen2] -= cantidad;
                cuentas2[cuentaDestino2] += cantidad;

                System.out.print(String.format("%10.2f",cantidad)) ;
                System.out.println( " Del banco 2 de la cuenta " + cuentaOrigen2 + " a la cuenta" + cuentaDestino2);
                System.out.println(Thread.currentThread());

                //IMPRIMIMOS SALDO TOTAL DEL BANCO
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
            return ;
        }

        /**
         * Comprueva el saldo total sumando el saldo de todas las cuentas
         * @return Dooble con el saldo total
         */
        public double getSaldoBanco(){

            double total = 0;

            //Obtenemos con un for el saldo de todas las cuentas
            for (double a: cuentas1) {
                total += a;
            }
            for (double a: cuentas2) {
                total += a;
            }
            return total;
        }


}
