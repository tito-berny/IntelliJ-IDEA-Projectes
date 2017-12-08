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
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Scanner sn = new Scanner(System.in);
    private static String arxiuOriginal = "./Arxui/arxiuOriginal.txt";   // archivo original
    private static String arxiuHash = "./Arxui/arxiuhashEncriptat.txt";          // archivo amb el hash del original
    private static String arxiuClauPublica = "./Claus/clauPublica.txt";// archivo amb la Clau Publica

    private static String arxiuClauPublicaReceptor = "./Claus/clauPublicaReceptor.txt";// archivo amb la Clau Publica
    private static String arxiuHashReceptor = "./Arxui/arxiuhashReceptor.txt";        // archivo creado con el hash del Receptor

    private static KeyPair clau = null;     //inicializamos el par de claves

    //Genera las llaves Public y Privada
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
    //Hace el hash del String, en este caso el texto original
    public static byte[] ferHash(String path) throws NoSuchAlgorithmException, FileNotFoundException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        File f = new File(path);
        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[(int) f.length()];
        int read = 0;
        while ((read = is.read(buffer)) > 0) {
            digest.update(buffer, 0, read);
        }
        byte[] md5sum = digest.digest();

        return md5sum;
    }
    // Encripta el hash con la llave Privada
    private static byte[] encriptarHash(byte[] hash, PrivateKey clau) throws NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {

        //Encriptem el hash amb la clau privada

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, clau);
        byte[] encriptat = cipher.doFinal(hash);

        return encriptat;
    }
    //Desemcripta
    private static byte[] desencriptarAmbClauPublica(byte[] hash, PublicKey clau) throws NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {

        //Desencriptem el hash amb la clau publica

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, clau);
        byte[] desencriptat = cipher.doFinal(hash);

        return desencriptat;
    }
    // Guarda en un archivo el hash pasado en un array
    private static void guardarHash(String fileName, byte[] hash) throws Exception {

        FileOutputStream fis = new FileOutputStream(fileName);
        fis.write(hash);
        fis.close();
        System.out.println("Hash emmagatzemat !!");
    }
    //Guarda el contenido de un fichero en una array de bytes
    private static byte[] muestraContenido(String archivo) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(archivo);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        return bytes;
    }
    //Obtiene la clave publica de un fichero
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
    //Guarda la clave en un fichero
    private static void guardarClave(Key key, String fileName) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(publicKeyBytes);
        fos.close();
        System.out.println("Clau guardada al arxiu !!");
    }
    //Verifica que todos los ficheros son creados
    private static void verificaficheros() {

        Path paths1 = Paths.get(arxiuClauPublica);
        Path paths2 = Paths.get(arxiuHash);
        Path paths3 = Paths.get(arxiuOriginal);

        if (Files.exists(paths1)) {

            if (Files.exists(paths2)) {

                if (Files.exists(paths3)) {
                    System.out.println("Molt be s'ha dreçat tots els arxius !!!");

                } else System.out.println("L' arxiu original no trobat !!");

            } else System.out.println("L' arxiu del Hash no s'ha generat !!");

        } else System.out.println("L' arxiu de clau publica no esta generat !!");

    }
    //Muestra el menu principal de la app
    private static void menuPrincipal() throws Exception {

        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        KeyPair claves = generarClave();
        //System.out.println(claves.getPrivate() + " Clave Publica : " + claves.getPublic().toString());


        while (!salir) {

            System.out.println("1. Emisor");
            System.out.println("2. Receptor");

            System.out.println("Escriu una de les opcions");
            opcion = sn.nextInt();
            do {
                try {
                    num = false;
                    opcion = +Integer.parseInt(sn.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("nomes numeros de 1 a 2 !!");
                    num = true;
                }
            } while (num);

            switch (opcion) {
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
    //Muestra el menu de emisor de la app
    public static void emisor() throws Exception {


        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {

            System.out.println("1. Generar Claus");
            System.out.println("2. Fer hash del arxiu");
            System.out.println("3. Guardar Clau Publica");
            System.out.println("4. Enviar-ho tot");
            System.out.println("5. Tornar ");

            System.out.println("Escriu una de les opcions");
            opcion = sn.nextInt();

            do {
                try {
                    num = false;
                    opcion = +Integer.parseInt(sn.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("nomes numeros de 1 a 2 !!");
                    num = true;
                }

            } while (num);

            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionado Generar Claus");
                    clau = generarClave();
                    System.out.println("");

                    break;
                case 2:
                    System.out.println("Has seleccionat Fer hash del arxiu");

                    byte[] hash = ferHash(arxiuOriginal);

                    byte[] hashencrip = encriptarHash(hash, clau.getPrivate());
                    System.out.println(hashencrip);
                    System.out.println("S'ha cifrat el hash amb la clau Privada.");

                    guardarHash(arxiuHash, hashencrip);
                    break;
                case 3:
                    System.out.println("Has seleccionat Guardar Clau Publica");

                    //Guarda en el fichero la llave Pubila
                    guardarClave(clau.getPublic(), arxiuClauPublica);


                    //obtenerClavePublica(arxiuClauPublica);

                    break;
                case 4:
                    System.out.println("Has seleccionat Enviar-ho tot");

                    //Verificamos que todos los ficheros estan creadosº
                    verificaficheros();
                    //volvemos al menu primcipal
                    menuPrincipal();

                    break;
                case 5:
                    menuPrincipal();
                    break;
            }

        }
    }
    //Muestra el menu del receptor de la app
    private static void receptor() throws Exception {

        boolean num;
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {

            System.out.println("1. Crear hash del arxiu original");
            System.out.println("2. Estreure clau publica ");
            System.out.println("3. Comparar hash rebut amb hash desemcriptat amb clau Publica ");
            System.out.println("4. Tornar ");


            System.out.println("Escriu una de les opcions");
            opcion = sn.nextInt();

            do {
                try {
                    num = false;
                    opcion = +Integer.parseInt(sn.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("nomes numeros de 1 a 2 !!");
                    num = true;
                }

            } while (num);

            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionat Crear hash del arxiu original ");
                    System.out.println("");

                    byte[] hashReceptor = ferHash(arxiuOriginal);
                    guardarHash(arxiuHashReceptor, hashReceptor);

                    break;

                case 2:
                    System.out.println("Has seleccionat Estreure clau publica ");
                    System.out.println("");

                    PublicKey clau1 = obtenerClavePublica(arxiuClauPublica);

                    System.out.println("La clau obtinguda es :" + clau.getPublic());

                    break;

                case 3:
                    System.out.println("Comparar hash rebut amb hash desemcriptat amb clau Publica");
                    System.out.println("");

                    // Cojemos el contenido del archivo con el hash encriptado recibido del emisor y lo desencriptamos con
                    // la llave Publica
                    byte[] hashEncriptado = muestraContenido(arxiuHash);
                    //PublicKey clau2 = obtenerClavePublica(arxiuClauPublica);
                    byte[] hashDesencriptat = desencriptarAmbClauPublica(hashEncriptado, clau.getPublic());
                    boolean comparacion = Arrays.equals(hashDesencriptat, hashEncriptado);

                    if (hashDesencriptat == hashEncriptado){
                        System.out.println(" Son iguals Proçes terminat !!!");
                    }else  System.out.println(" Esta correpte el document");

                    //Comparamos el hash desencriptado con el hash obtenido del documento
                    if (comparacion) System.out.println(" Es una firma digital en tota regla");
                    else System.out.println(" Esta corrupte el document");

                case 4:
                    menuPrincipal();
                    break;
            }
        }
    }
    // Main
    public static void main(String[] args) throws Exception {

        menuPrincipal();
    }

}
