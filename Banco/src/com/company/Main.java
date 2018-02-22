package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    Random rnd = new Random();

    public static void main(String[] args) {
        // write your code here

        Banco b=new Banco();

        for (int i=0; i<100; i++){

            EjecucionTransferencias r= new EjecucionTransferencias(b,i,2000);

            Thread t = new Thread(r);

            t.start();

        }

    }


}
