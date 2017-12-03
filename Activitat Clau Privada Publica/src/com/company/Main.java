package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class Main {

    private static Scanner sn = new Scanner(System.in);
    private  static String arxiuOriginal = "arxiu.txt";    // archivo original
    private static String arxiuHash = "arxiu.txt";        // archivo amb el hash del original
    private static String arxiuClauPublica = "./Claus/clauPublica.txt"; // archivo amb la Clau Publica

    public static KeyPair generarClave() {
        KeyPair clave = null;//La clase KeyPair soporta una clave privada y una pública.
        try {
            //Usamos el algoritmo RSA (RSA es un sistema criptográfico de clave pública desarrollado en 1977).
            KeyPairGenerator generador = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            generador.initialize(1024);//Tamaño de la clave.

            clave = generador.genKeyPair();
            System.out.println("Claus generades !!");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Error al generar les claus !!");
        }
        return clave;
    }

    private static PublicKey obtenerClavePublica(String fileName) throws Exception {

        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        return keyFromBytes;
    }

    private static PrivateKey obtenerClavePrivada(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }

    private static void guardarClave(Key key, String fileName) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(publicKeyBytes);
        fos.close();
        System.out.println("Clau guardada al arxiu !!");
    }

    public static void main(String[] args) throws Exception {
        // write your code here


        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        KeyPair claves = generarClave();
        System.out.println(claves.getPrivate() + " Clave Publica : " + claves.getPublic().toString());


        while(!salir){

            System.out.println("1. Emisor");
            System.out.println("2. Receptor");

            System.out.println("Escriu una de les opcions");
            opcion = sn.nextInt();
                do {
                    try {
                        num = false;
                        opcion =+ Integer.parseInt(sn.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("nomes numeros de 1 a 2 !!");
                        num = true;
                    }
                } while (num);


            switch(opcion){
                case 1:
                    System.out.println("Has seleccionat Emisor");
                    System.out.println("");
                    emisor();

                    break;
                case 2:
                    System.out.println("Has seleccionat Receptor");
                    System.out.println("");

                    break;
            }

        }

    }

    private static void emisor() throws Exception {

        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        KeyPair clau = null; //inicializamos el par de claves

        while(!salir){

            System.out.println("1. Generar Claus");
            System.out.println("2. Fer hash del arxiu");
            System.out.println("3. Guardar Clau Publica");
            System.out.println("4. Enviar-ho tot");

            System.out.println("Escriu una de les opcions");
            opcion = sn.nextInt();

            do {
                try {
                    num = false;
                    opcion =+ Integer.parseInt(sn.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("nomes numeros de 1 a 2 !!");
                    num = true;
                }

            } while (num);

            switch(opcion){
                case 1:
                    System.out.println("Has seleccionado Generar Claus");
                    clau = generarClave();
                    System.out.println("");

                    break;
                case 2:
                    System.out.println("Has seleccionat Fer hash del arxiu");
                    break;
                case 3:
                    System.out.println("Has seleccionat Guardar Clau Publica");

                    guardarClave(clau.getPublic(), arxiuClauPublica );
                    //PublicKey keyFromBytes =
                    obtenerClavePublica(arxiuClauPublica);
                   // System.out.println(keyFromBytes);
                    break;
                case 4:
                    System.out.println("Has seleccionat Enviar-ho tot");
                    break;
            }

        }
    }


}