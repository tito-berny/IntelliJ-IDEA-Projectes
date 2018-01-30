package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    //-------------------Variables Glovales-------------------------

    private static File file = new File("./prueba.txt");

    //-------------------Variables de Configuracion tamaños campos-----------

    private static int ABM = 1;

    private static int Tipus = 2;

    private static int Data = 10;

    private static int Matricula = 9;

    private static int BASTIDOR = 17;

    private static int N_MOTOR = 20;

    private static int DNI = 9;

    private static int GOGNOMS_NOM = 40;

    private static int ADREÇA = 50;

    private static String relleno = "*";


    public static void main(String[] args) throws FileNotFoundException, Exception {
	// write your code here


        RandomAccessFile randomAccessFile = new RandomAccessFile("DGTestruct.csv","rw");

        ArrayList<String> fichero;
        String[] ficheroEstructurado;

        //Guardamos en el fichero los datos del fichero leido
        fichero = leerFichero();

        ficheroEstructurado = estructurarFicheroRandom(fichero);

        /*
        try {

            // create a new RandomAccessFile with filename test
            RandomAccessFile raf = new RandomAccessFile("./test.txt", "rw");

            // write something in the file
            raf.writeUTF("Hello World");

            // set the file pointer at 0 position
            raf.seek(0);

            // print the line
            System.out.println("" + raf.readLine());

            // set the file pointer at 0 position
            raf.seek(0);

            // write something in the file
            raf.writeUTF("This is an example \n Hello World");

            raf.seek(10);
            // print the line
            System.out.println("" + raf.readLine());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
*/
    }


    /**
     * Leemos el fichero linea a linea
     * @return Un ArrayList con una linea del fichero en cada posicion.
     */
    private static ArrayList<String> leerFichero(){

        ArrayList<String> lineas = new ArrayList<String>();

        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // Lectura del fichero
            // Cada linea leida en el fichero se guarda en una posicion del array
            String linea;
            while((linea=br.readLine())!=null)
                lineas.add(linea);

        }
        //Capturamos la exepcion e informamos al usuario
        catch(Exception e){
            System.out.println("");
            System.out.println("L'arxiu no s'ha trobat. " +
                    " Introdueix nous parametres.");
            System.out.println("");
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

        return lineas;
    }

    private static String[] estructurarFicheroRandom(ArrayList<String> fichero) {

        ArrayList<String> array = new ArrayList<String>();
        String[] finalstring = new String[8];


        for (String aLista: fichero) {

            String[] string;
            string = aLista.split("\\|");

            if (string[0].length() == ABM) finalstring[0] = string[0];
            else System.out.println("Tamaño del campo ABM incorrecto, se perderan datos " + fichero);

            if (string[1].length() == Tipus) finalstring[1] = string[1];
            else System.out.println("Tamaño del campo Tipus incorrecto, se perderan datos !");

            if (string[2].length() == Data) finalstring[2] = string[2];
            else System.out.println("Tamaño del campo Data incorrecto, se perderan datos !");

            if (string[3].length() == Matricula) finalstring[3] = string[3];
            else if (string[3].length() > Matricula){
                System.out.println("Tamaño del campo Matricula demasiado grande, se perderan datos !");
                for (int i = 0; i < Matricula; i++) {
                    //string[3] = string[3].getChars(i,i,null,i);
                }
                finalstring[3] = string[3];
            }
            else {
                int a = Matricula - string[3].length();
                for (int i = 0; i < a; i++) {
                    string[3] = string[3] + relleno;
                }
                finalstring[3] = string[3];
            }

            if (string[4].length() == BASTIDOR) finalstring[4] = string[4];
            else System.out.println("Tamaño del campo BASTIDOR incorrecto, se perderan datos !");

            if (string[5].length() == N_MOTOR) finalstring[5] = string[5];
            else System.out.println("Tamaño del campo N_MOTOR incorrecto, se perderan datos !");

            if (string[6].length() == DNI) finalstring[6] = string[6];
            else System.out.println("Tamaño del campo DNI incorrecto, se perderan datos !");

            if (string[7].length() == GOGNOMS_NOM) finalstring[7] = string[7];
            else System.out.println("Tamaño del campo GOGNOMS_NOM incorrecto, se perderan datos !");

            if (string[8].length() == ADREÇA) finalstring[8] = string[8];
            else System.out.println("Tamaño del campo ADREÇA incorrecto, se perderan datos !");
        }


        return finalstring;
    }

}
