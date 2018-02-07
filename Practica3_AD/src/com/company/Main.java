package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //-------------------Variables Glovales-------------------------

    private static File file = new File("./prueba.txt");

    private static Scanner scn = new Scanner(System.in);

    private static RandomAccessFile randomAccessFile;


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

    private static int Toal_linea = 159;

    public static void main(String[] args) throws FileNotFoundException, Exception {


        ArrayList<String> fichero = null;

        //------------VARIABLES--------------

        //boolean para indicar que queremos salir del bucle while
        boolean salir = false;
        //Guardaremos la opcion del usuario
        int opcion;


        //----------------MENU---------------

        while (!salir) {

            System.out.println("");
            System.out.println("1. Transformar Fichero");
            System.out.println("2. Modificar Registro");
            System.out.println("3. Salir ");

            try {

                System.out.println("Escribe una de las opciones");
                opcion = scn.nextInt();

                switch (opcion) {

                    case 1:
                        System.out.println("Has seleccionat Transformar Fichero");

                        //Guardamos en el array los datos del fichero leido
                        fichero = leerFichero();

                        //Estructuramos el fichero para que guarde los datos seguidos en formato RandomAccessFile
                        int num = estructurarFicheroRandom(fichero);

                        System.out.println("El archivo tiene " + fichero.size() + " Registros.");
                        break;

                    case 2:

                        Registro reg = new Registro();

                        System.out.println("Has seleccionat Modificar Registro");
                        System.out.println("");
                        //Preguntamos al usuari que registro quiere modificar
                        System.out.println("Que registro deseas Modificar ? ");
                        int numReg = scn.nextInt();

                        //Controlamos que el usuario introduzca un numero de registro existente
                        if (numReg >= fichero.size()) {
                            System.out.println("EL NUMERO DE REGISTRO NO EXISTE  !!!");
                        } else {

                            String linea = modificarRegistro(numReg);
                            reg = pedirCampoaModificar(linea);

                            
                        }

                        break;

                    case 3:
                        salir = true;

                        break;

                    //En el caso de no introducir un numero del 1 al 3
                    //informamos al usuario
                    default:
                        System.out.println();
                        System.out.println("Solo números entre 1 y 3");
                        System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Debes insertar un número");
                scn.next();
                System.out.println();
            }
        }




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

    private static Registro pedirCampoaModificar(String a) {

        Registro reg = new Registro();
        reg = rellenarRegistroConLinea(a);

        System.out.println("");
        System.out.println("Que campo desea modificar?" +
                "/n 1)ABM  2)Tipus  3)Data  4)Matricula  5)Bastidor  6)N_Motor  7)DNI  8)Cognoms_Nom  9)Adreça");
        int opcion = scn.nextInt();


        switch (opcion) {
            case (1):
                boolean salir1 = false;

                a.substring(0, 1);//1
                System.out.println("Valor: " + a);

                while (!salir1)

                    System.out.println("Introduce nuevo valor (tamaño 1)");
                    String b1 = scn.next();
                    if (b1.length() == 1) {
                        reg.setABM(b1);
                        salir1 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }

            case (2):
                boolean salir2 = false;

                a.substring(1, 3);//2
                System.out.println("Valor: " + a);

                while (!salir2)

                    System.out.println("Introduce nuevo valor tamaño 2");
                    String b2 = scn.next();
                    if (b2.length() == 2) {
                        reg.setTipus(b2);
                        salir2 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (3):
                boolean salir3 = false;

                a.substring(3, 13);//10

                System.out.println("Valor: " + a);

                while (!salir3)

                    System.out.println("Introduce nuevo valor tamaño 10");
                    String b3 = scn.next();
                    if (b3.length() == 10) {
                        reg.setData(b3);
                        salir3 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (4):
                boolean salir4 = false;

                a.substring(13, 22);//9
                System.out.println("Valor: " + a);

                while (!salir4)

                    System.out.println("Introduce nuevo valor tamaño 9");
                    String b4 = scn.next();
                    if (b4.length() == 9) {
                        reg.setMatricula(b4);
                        salir4 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (5):
                boolean salir5 = false;

                a.substring(22, 39);//17
                System.out.println("Valor: " + a);

                while (!salir5)

                    System.out.println("Introduce nuevo valor tamaño 17");
                    String b5 = scn.next();
                    if (b5.length() == 17) {
                        reg.setBASTIDOR(b5);
                        salir5 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (6):
                boolean salir6 = false;

                a.substring(39, 59);//20
                System.out.println("Valor: " + a);

                while (!salir6)

                    System.out.println("Introduce nuevo valor tamaño 20");
                    String b6 = scn.next();
                    if (b6.length() == 20) {
                        reg.setN_MOTOR(b6);
                        salir6 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (7):
                boolean salir7 = false;

                a.substring(59, 68);//9
                System.out.println("Valor: " + a);

                while (!salir7)

                    System.out.println("Introduce nuevo valor tamaño 9");
                    String b7 = scn.next();
                    if (b7.length() == 9) {
                        reg.setDNI(b7);
                        salir7 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (8):
                boolean salir8 = false;

                a.substring(68, 108);//40
                System.out.println("Valor: " + a);

                while (!salir8)

                    System.out.println("Introduce nuevo valor tamaño 40");
                    String b8 = scn.next();
                    if (b8.length() == 40) {
                        reg.setGOGNOMS_NOM(b8);
                        salir8 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
            case (9):
                boolean salir9 = false;

                a.substring(108, 158);//50
                System.out.println("Valor: " + a.substring(108, 158));


                while (!salir9) {

                    System.out.println("Introduce nuevo valor tamaño 50");
                    String b9 = scn.nextLine();
                    if (b9.length() == 50) {
                        reg.setADREÇA(b9);
                        salir9 = true;
                    } else {
                        System.out.println("Valor de tamaño incorrecto introduce 50 caracteres, si es mas corto " +
                                "rellena con '***'");
                    }
                }

        }

        return reg;


    }

    /**
     * Obtiene todos los valores de la linea leida y los encapsula
     * en un objeto Registro
     * @param a Objeto registro completo
     * @return
     */
    private static Registro rellenarRegistroConLinea(String a) {

        Registro r = new Registro();
        r.setABM(a.substring(0,1));
        r.setTipus(a.substring(1,3));
        r.setData(a.substring(3, 13));
        r.setMatricula(a.substring(13, 22));
        r.setBASTIDOR(a.substring(22, 39));
        r.setN_MOTOR(a.substring(39, 59));
        r.setDNI(a.substring(59, 68));
        r.setGOGNOMS_NOM(a.substring(68, 108));
        r.setADREÇA(a.substring(108, 158));

        return r;
    }

    /**
     * Obtine la linea del registro que el usuario quiere modificar
     * @param a String con la linea leida
     * @return
     */
    private static String modificarRegistro(int a) {

        String linea = null;

        //Creamos el RandomAccesFile con el fichero creado anteriormente
        try {
            randomAccessFile = new RandomAccessFile("DGTcsv.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //obtenemos el registro a modificar multiplicandolo por el total de cada registro
        int reg = a * Toal_linea;

        //Obtiene la linea calculada apartir del numero calculado
        try {
            randomAccessFile.seek(reg);
            System.out.println(randomAccessFile.readLine());
            linea = randomAccessFile.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return linea;
    }


    /**
     * Leemos el fichero linea a linea
     *
     * @return Un ArrayList con una linea del fichero en cada posicion.
     */
    private static ArrayList<String> leerFichero() {

        ArrayList<String> lineas = new ArrayList<>();

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
            while ((linea = br.readLine()) != null)
                lineas.add(linea);

        }
        //Capturamos la exepcion e informamos al usuario
        catch (Exception e) {
            System.out.println("");
            System.out.println("L'arxiu no s'ha trobat. " +
                    " Introdueix nous parametres.");
            System.out.println("");
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return lineas;
    }

    /**
     * Controla que todos los campos de los registros leidos del fichero origen
     * sean de la longitud exacta expecificada
     * @param fichero
     * @return
     */
    private static int estructurarFicheroRandom(ArrayList<String> fichero) {

        ArrayList<String[]> array = new ArrayList<>();
        String[] finalstring = new String[9];
        int contador = 0;

        //Por cada registro en el fichero creara una linea con los tamaños especificados
        for (String aLista : fichero) {

            String[] string;

            //Separamos los datos por "|" necesitamos // delante por ser un caracter especial
            string = aLista.split("\\|");

            //--------------------ABM---------------------

            //Si el tamaño del campo coincide lo asignamos
            if (string[0].length() == ABM) finalstring[0] = string[0];

                //Si el tamaño es mas grande
            else if (string[0].length() > ABM) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo ABM del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro ABM.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[0] = string[0].substring(0, ABM);

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
            else if (string[1].length() > Tipus) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo Tipus del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro Tipus.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[1] = string[1].substring(0, Tipus);

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
            else if (string[2].length() > Data) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo Data del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro Data.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[2] = string[2].substring(0, Data);

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
            else if (string[3].length() > Matricula) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo Matricula del Registro " + contador + " demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro Matricula.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[3] = string[3].substring(0, Matricula);

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
            else if (string[4].length() > BASTIDOR) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo BASTIDOR del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro BASTIDOR.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[4] = string[4].substring(0, BASTIDOR);

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
            else if (string[5].length() > N_MOTOR) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo N_MOTOR del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro N_MOTOR.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[5] = string[5].substring(0, N_MOTOR);

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
            else if (string[6].length() > DNI) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo DNI del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro DNI.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[6] = string[6].substring(0, DNI);

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
            else if (string[7].length() > GOGNOMS_NOM) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo GOGNOMS_NOM del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro GOGNOMS_NOM.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[7] = string[7].substring(0, GOGNOMS_NOM);

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
            else if (string[8].length() > ADREÇA) {

                //Informamos al usuario que se perderan datos por ser demasiado largo el String
                System.out.println("Tamaño del campo ADREÇA del Registro " + contador + " es demasiado grande, se perderan datos !" +
                        "Le recomendamos modificar el reguistro ADREÇA.");

                //Con subString recortamos el String demasialo largo al tamaño indicado
                finalstring[8] = string[8].substring(0, ADREÇA);

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

            //Guardamos la nueva linea en el fichero csv
            try {
                guardarFichero(finalstring);
                contador++;
            } catch (Exception e) {
                System.out.println("Error al dreçar l'arxui");
            }
        }
        return contador;
    }

    /**
     * Guarda en el fichero el Array de String
     * @param a Recive el Array de Strig con los Registros sin separar
     */
    public static void guardarFichero(String[] a) {

        FileWriter fichero = null;
        PrintWriter pw;
        try {
            //Indicamos el fichero donde se escribiran los datos
            //Si indicamos true como segundo parametro y el fichero ya existe
            //copiara la linea seguidamente de los datos ya existentes,
            //si no SOBREESCRIBE el archivo con los datos nuevos
            fichero = new FileWriter("./DGTcsv.txt", true);
            pw = new PrintWriter(fichero);

            //Por cada string de la lista lo guarda en una linea en el fichero
            for (String aLista : a) {
                pw.print(aLista);
                System.out.print(aLista);
            }

            //Agregamos el salto de linea al final de la linea almacenada
            pw.println("");

            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
            //En caso de error informamos al usuario
            System.out.println("Problema al emmagatzenar una linea");
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
