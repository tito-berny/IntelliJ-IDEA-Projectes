package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here

        //Variables

        String fraseoriginal;
        String fraseencriptada;


        Scanner scn = new Scanner(System.in);

        //Programa

        System.out.println("Dona'm la frase a encriptar :");
        fraseoriginal = scn.nextLine();
        System.out.println("");

        Seguridad sec = new Seguridad();

        sec.addKey("Catalunya");
        System.out.println( fraseoriginal );

        System.out.println( " ------------ Encriptado ------------ " );
        String texto = sec.encriptar(fraseoriginal);
        System.out.println( texto );

        System.out.println( " ------------ Desencriptado ------------ " );
        System.out.println( sec.desencriptar( texto ) );


    }
}

