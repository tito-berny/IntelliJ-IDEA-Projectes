package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class Main {

    private static Scanner sn = new Scanner(System.in);
    private static String arxiuOriginal = "./Arxui/arxiuOriginal.txt";   // archivo original
    private static String arxiuHash = "./Arxui/arxiuhash.txt";          // archivo amb el hash del original
    private static String arxiuClauPublica = "./Claus/clauPublica.txt";// archivo amb la Clau Publica

    private static String arxiuClauPublicaReceptor = "./Claus/clauPublicaReceptor.txt";// archivo amb la Clau Publica
    private static String arxiuHashReceptor = "./Arxui/arxiuhashReceptor.txt";// archivo amb la Clau Publica

    public static KeyPair generarClave() {
        KeyPair clave = null;//La clase KeyPair soporta una clave privada y una pública.
        try {
            //Usamos el algoritmo RSA (RSA es un sistema criptográfico de clave pública desarrollado en 1977).
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
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

    public static byte[] ferHash(String path) throws NoSuchAlgorithmException, FileNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        File f = new File(path);
        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[(int) f.length()];
        int read = 0;
        while ((read = is.read(buffer)) > 0)
        {
            digest.update(buffer, 0, read);
        }
        byte[] md5sum = digest.digest();

        return md5sum;
    }

    private static byte[] encriptarHash(byte[] hash, PrivateKey clau) throws NoSuchAlgorithmException,IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException{

        //Encriptem el hash amb la clau privada

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE,clau );
        byte[] encriptat = cipher.doFinal(hash);

        return encriptat;
    }


    /*public static boolean Comparar(String file, String hashCode)
            throws NoSuchAlgorithmException, FileNotFoundException, IOException
    {
        return hashCode.equals(ferHash(file, clau));
    }*/

    private static void guardarHash(String fileName, byte[] hash ) throws Exception {

        FileOutputStream fis = new FileOutputStream(fileName);
        fis.write(hash);
        fis.close();
        System.out.println("Hash emmagatcemat !!");
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

    private static void guardarClave(Key key, String fileName) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(publicKeyBytes);
        fos.close();
        System.out.println("Clau guardada al arxiu !!");
    }

        // write your code here
    public static void main(String[] args) throws Exception {

        menuPrincipal();
    }

    private static void menuPrincipal() {

        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        KeyPair claves = generarClave();
        //System.out.println(claves.getPrivate() + " Clave Publica : " + claves.getPublic().toString());


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
                    try {
                        emisor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    System.out.println("Has seleccionat Receptor");
                    System.out.println("");
                    receptor();

                    break;
            }

        }
    }

    public static void emisor() throws Exception {

        KeyPair clau = null; //inicializamos el par de claves
        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

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

                    byte [] hash = ferHash(arxiuOriginal);

                    byte [] hashencrip = encriptarHash(hash, clau.getPrivate());
                    System.out.println(hashencrip);

                    guardarHash(arxiuHash, hash);
                    break;
                case 3:
                    System.out.println("Has seleccionat Guardar Clau Publica");

                    guardarClave(clau.getPublic(), arxiuClauPublica );

                    obtenerClavePublica(arxiuClauPublica);

                    break;
                case 4:
                    System.out.println("Has seleccionat Enviar-ho tot");

                    //Verificamos que todos los ficheros estan creadosº
                    verificaficheros();
                    //volvemos al menu primcipal
                    menuPrincipal();

                    break;
            }

        }
    }

    private static void receptor() {

        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while(!salir){

            System.out.println("1. Crear hash del arxiu original");
            System.out.println("2. Estreure clau publica ");
            System.out.println("2. Comparar hash ");

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
                    System.out.println("Has seleccionat Crear hash del arxiu original ");
                    System.out.println("");

                   // ferHash(arxiuHashReceptor);

                    break;

                case 2:
                    System.out.println("Has seleccionat Estreure clau publica ");
                    System.out.println("");


                    break;

                case 3:
                    System.out.println("Has seleccionat Comparar hash");
                    System.out.println("");

                    menuPrincipal();

                    break;
            }
        }
    }

    private static void verificaficheros() {

        Path paths1 = Paths.get(arxiuClauPublica);
        Path paths2 = Paths.get(arxiuHash);
        Path paths3 = Paths.get(arxiuOriginal);

        if (Files.exists(paths1)){

            if (Files.exists(paths2)){

                if (Files.exists(paths3)){
                    System.out.println("Molt be sa dreçat tots els arxius !!!");

                }else System.out.println("L' arxiu original no trobat !!");

            }else System.out.println("L' arxiu del Hash no s'ha generat !!");

        }else System.out.println("L' arxiu de clau publica no esta generat !!");

    }


}