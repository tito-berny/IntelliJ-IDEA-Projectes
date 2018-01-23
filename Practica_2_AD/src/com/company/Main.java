package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //-------------------Variables-------------------------

    static Scanner scn = new Scanner(System.in);


    public static void main(String[] args) {
	// write your code here

        menu();


    }

    private static void menu() {

        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        //----------------MENU---------------

        while(!salir){

            System.out.println("1. Insereix nous parametres");
            System.out.println("2. Opcion 2");
            System.out.println("3. Opcion 3");
            System.out.println("4. Salir");

            try {

                System.out.println("Escribe una de las opciones");
                opcion = scn.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Has seleccionat Insereix nous parametres");

                        pedirParametros();

                        break;
                    case 2:
                        System.out.println("Has seleccionado la opcion 2");
                        break;
                    case 3:
                        System.out.println("Has seleccionado la opcion 3");
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println();
                        System.out.println("Solo números entre 1 y 4");
                        System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Debes insertar un número");
                scn.next();
                System.out.println();
            }
        }

    }

    public static void pedirParametros() {

        //Variables

        String ABM, TIPUS,DATA, MATRICULA, N_BASTIDO ,N_MOTOR ,DNI, COGNOM_NOM,ADREÇA;

        String[] lista = new String[9];

        //----------------Solicitar---------------


        System.out.println(" Dona'm A(alta), B(baixa) o M(Modificacio)");
        ABM = scn.next();
        lista[0] = ABM;

        System.out.println(" Dona 'm el Tipus\n" +

                            "AL Alta nou vehicle\n" +
                "\n" +
                "            CD canvi domicili\n" +
                "\n" +
                "            CP canvi propietari\n" +
                "\n" +
                "            BD Baixa definitiva - desvallastament\n" +
                "\n" +
                "            BT  Baixa temporal");
        TIPUS = scn.next();
        lista[1] = TIPUS;

        System.out.println(" Dona'm la Data (DD/MM/AAAA)");
        DATA = scn.next();
        lista[2] = DATA;

        System.out.println(" Dona'm la Matricula");
        MATRICULA = scn.next();
        lista[3] = MATRICULA;

        System.out.println(" Dona'm el Numero de Bastidor");
        N_BASTIDO = scn.next();
        lista[4] = N_BASTIDO;

        System.out.println(" Dona'm el Numero de Motor");
        N_MOTOR = scn.next();
        lista[5] = N_MOTOR;

        System.out.println(" Dona'm el DNI");
        DNI = scn.next();
        lista[6] = DNI;

        System.out.println(" Dona'm el Cognom");
        COGNOM_NOM = scn.next();
        lista[7] = COGNOM_NOM;

        System.out.println(" Dona'm l'Adreça");
        ADREÇA = scn.next();
        lista[8] = ADREÇA;

        System.out.println("Contigut final : ");

        for (int i = 0; i < lista.length; i++) {

            System.out.print(" " + lista[i]);
        }

    }
}
