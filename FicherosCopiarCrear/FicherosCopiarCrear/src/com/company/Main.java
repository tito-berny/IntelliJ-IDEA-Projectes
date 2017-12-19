package com.company;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        // write your code here

        File file1 = new File("/home/infot/proyectos/");
        File file2 = new File("/home/infot/proyectos1/");

        try {
            inforcioFitxers(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //llistaDirectoris(file2);

        //CopiarArxius(file1, file2);
        //String[] s = new String[0];
        try {
            copiarFitxers(file1,file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CopiarArxius(File file1, File file2) {

        if (file1.exists()) {

        }
        if (file2.exists()) {

        } else {

        }
    }

    public static void inforcioFitxers(File f) throws IOException {

        System.out.println(f.getAbsolutePath());
        System.out.println("Nom: " + f.getName());
        System.out.println("Camí: " + f.getPath());

        if (f.exists()) {
            System.out.println("Fitxer existeix ");
            System.out.println("Ficher de lectura = " + (f.canRead()));
            System.out.println("Ficher de escritura = " + (f.canWrite()));
            System.out.println("Longitud del fitxer = " + f.length() + " bytes");
            Date data = new Date(f.lastModified());
            System.out.println("Última modificació = " + data);

        } else {
            System.out.println("El fitxer no existeix");
        }

    }

    public static void llistaDirectoris(File file) {


        if (file.isDirectory()) {
            File[] fitxers = file.listFiles();
            for (int x = 0; x < fitxers.length; x++) {
                System.out.println(fitxers[x].getName());
            }
        }

    }


    public static void copiarFitxers( File From, File To ) throws IOException {

        if (From.exists()){

            File [] asd = From.listFiles();

            if (To.exists()){

                //overwrite existing file, if exists
                CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES

                };

                Files.copy(From.toPath(), To.toPath(), options);
                System.out.println("Arxiu copiat");

                }else {
                    System.out.println("La ruta de desti no existeix, sera creada");

                    //Creamos la carpeta con mkdir
                    To.mkdir();

                    System.out.println("");
                    System.out.println("Ruta creada");

                    //overwrite existing file, if exists
                    CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES

                    };


                    for (int i = 0; i < asd.length; i++) {

                        Files.copy(asd[i].toPath(), new File(To.getPath()+"/"+asd[i].getName()).toPath(), options);
                        copiarFitxers(asd[i].toPath(),);
                    }
                    System.out.println("Arxiu copiat");

                }

        }else {
            System.out.println("La ruta no existeix");

        }

    }


}




