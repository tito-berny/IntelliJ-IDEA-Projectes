package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    Random rnd = new Random();

    public static void main(String[] args) {
        // write your code here

        Banco b=new Banco();

        //Ejecuta 100 transferencias
        for (int i=0; i<100; i++){

            //Instancia la clase con el constructor, le pasamos os parametros Banco,i y cantidad maxima, es runable
            EjecucionTransferencias r = new EjecucionTransferencias(b,i,2000);

            //creamos un hilo con la clase
            Thread t = new Thread(r);

            //Ejecutamos el hillo
            t.start();

        }

    }


}
