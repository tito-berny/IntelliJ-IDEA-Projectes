package com.company;

import java.util.Random;

public class Main {

    Random rnd = new Random();

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        DosBancos b = new DosBancos();

        //------------------Aprartdo 1 ----------------------------
       /* for (int i=0; i<10; i++){

            int deparaLaCuenta1 = (int)(10 * Math.random());
            int deparaLaCuenta2 = (int)(10 * Math.random());

            int paraLaCuenta1 = (int)(10 * Math.random());
            int paraLaCuenta2 = (int)(10 * Math.random());

            int cantidad = (int)(100 * Math.random());

            b.transferencia(deparaLaCuenta1, paraLaCuenta1, deparaLaCuenta2, paraLaCuenta2,  cantidad);

            b.getSaldoBanco();
        }
*/

       //-------------------------APARTADO 2 -----------------------------------
        //Ejecuta 10 transferencias
        for (int i=0; i<10; i++){

            //Instancia la clase con el constructor, le pasamos os parametros Banco,i y cantidad maxima, es runable
            EjecucionTransferencias r = new EjecucionTransferencias(b, i,2000);

            //creamos un hilo con la clase
            Thread t = new Thread(r);

            //Ejecutamos el hillo
            t.start();

        }


        //-------------------------APARTADO 3 -----------------------------------
        //Ejecuta 100 transferencias
        for (int i=0; i<100; i++){

            //Instancia la clase con el constructor, le pasamos os parametros Banco,i y cantidad maxima, es runable
            EjecucionTransferencias r = new EjecucionTransferencias(b, i,2000);

            //creamos un hilo con la clase
            Thread t = new Thread(r);

            //Ejecutamos el hillo
            t.start();

        }

    }

}
