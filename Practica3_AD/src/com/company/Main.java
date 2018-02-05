package com.company;

import java.io.*;
import java.util.ArrayList;

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


        RandomAccessFile randomAccessFile = new RandomAccessFile("DGTestruct.csv","rw");

        ArrayList<String> fichero;
        ArrayList<String[]> ficheroEstructurado;

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

    private static ArrayList<String[]> estructurarFicheroRandom(ArrayList<String> fichero) {

        ArrayList<String[]> array = new ArrayList<String[]>();
        String[] finalstring = new String[9];

        //Por cada registro en el fichero creara una linea con los tamaños especificados
        for (String aLista: fichero) {

            String[] string;

            //Separamos los datos por "|" necesitamos // delante por ser un caracter especial
            string = aLista.split("\\|");

            //--------------------ABM---------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[0].length() == ABM) finalstring[0] = string[0];

                //Si el tamaño es mas grande
            else if (string[0].length() > ABM){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo ABM demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro ABM.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[0] = string[0].substring(0,ABM);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = ABM - string[0].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[0] = string[0] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[0] = string[0];
            }

            //------------------TIPUS----------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[1].length() == Tipus) finalstring[1] = string[1];

                //Si el tamaño es mas grande
            else if (string[1].length() > Tipus){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo Tipus demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro Tipus.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[1] = string[1].substring(0,Tipus);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = Tipus - string[1].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[1] = string[1] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[1] = string[1];
            }

            //-------------------DATA---------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[2].length() == Data) finalstring[2] = string[2];

                //Si el tamaño es mas grande
            else if (string[2].length() > Data){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo Data demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro Data.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[2] = string[2].substring(0,Data);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = Data - string[2].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[2] = string[2] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[2] = string[2];
            }

            //----------------Matricula------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[3].length() == Matricula) finalstring[3] = string[3];

                //Si el tamaño es mas grande
            else if (string[3].length() > Matricula){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo Matricula demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro Matricula.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[3] = string[3].substring(0,Matricula);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = Matricula - string[3].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[3] = string[3] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[3] = string[3];
            }

            //-----------------BASTIDOR--------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[4].length() == BASTIDOR) finalstring[4] = string[4];

                //Si el tamaño es mas grande
            else if (string[4].length() > BASTIDOR){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo BASTIDOR demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro BASTIDOR.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[4] = string[4].substring(0,BASTIDOR);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = BASTIDOR - string[4].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[4] = string[4] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[4] = string[4];
            }

            //---------------------N_MOTOR----------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[5].length() == N_MOTOR) finalstring[5] = string[5];

                //Si el tamaño es mas grande
            else if (string[5].length() > N_MOTOR){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo N_MOTOR demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro N_MOTOR.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[5] = string[5].substring(0,N_MOTOR);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = N_MOTOR - string[5].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[5] = string[5] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[5] = string[5];
            }

            //----------------------DNI-----------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[6].length() == DNI) finalstring[6] = string[6];

                //Si el tamaño es mas grande
            else if (string[6].length() > DNI){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo DNI demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro DNI.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[6] = string[6].substring(0,DNI);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = DNI - string[6].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[6] = string[6] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[6] = string[6];
            }

            //------------------GOGNOMS_NOM------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[7].length() == GOGNOMS_NOM) finalstring[7] = string[7];

                //Si el tamaño es mas grande
            else if (string[7].length() > GOGNOMS_NOM){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo GOGNOMS_NOM demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro GOGNOMS_NOM.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[7] = string[7].substring(0,GOGNOMS_NOM);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = GOGNOMS_NOM - string[7].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[7] = string[7] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[7] = string[7];
            }

            //------------------------ADREÇA----------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[8].length() == ADREÇA) finalstring[8] = string[8];

                //Si el tamaño es mas grande
            else if (string[8].length() > ADREÇA){

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo ADREÇA demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro ADREÇA.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[8] = string[8].substring(0,ADREÇA);

            }

            //Si el tamaño es mas pequeño
            else {
                //Rellenamos los espacios asta el tamaño indicado con *
                //Obtenemos los espacios asta el limite
                int a = ADREÇA - string[8].length();
                //Rellenamos con el calculo
                for (int i = 0; i < a; i++) {
                    string[8] = string[8] + relleno;
                }
                //Asignamos el nuevo string a la lista final
                finalstring[8] = string[8];
            }
            array.add(finalstring);
        }

        return array;
    }

}