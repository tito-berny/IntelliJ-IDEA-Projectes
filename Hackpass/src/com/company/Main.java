package com.company;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

     //Variables

    private static SecretKey key;
    private static String algoritmo = "AES";
    private static int keysize = 128;

    private static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        boolean num;
        int clau = 0;

        // Programa

        //Pedimos al usuario que introduzca el numero de la llave
        System.out.println("Dona'm el numero clau (0 - 99) :");
        do {
            try {
                num = false;
                clau =+ Integer.parseInt(scn.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("nomes numeros de 0 a 99 !!");
                num = true;
            }
            if (clau < 0 || clau > 99) {
                num = true;
                System.out.println("nomes numeros de 0 a 99 !!!!");
            }

        } while (num);

        //Generamos la llave
        SecretKey skey = passwordKeyGeneration(clau, keysize);
        key = skey ;

        System.out.println();

        //Pedimos al usuario que introduzca el mensage a encriptar
        System.out.println("Dona'm el misatge a encriptar :");
        String misatge = scn.nextLine();

        //Encriptamos el mensage con la llave
        String textencriptat = encriptar(misatge, skey);

        System.out.println();
        //Mostramos el texto encriptado
        System.out.println("Contrasenya encriptada: "+textencriptat);

        System.out.println();

        //Pasamos el texto encriptado a un array de bytes
        byte [] textEncriptatEnBytes = new byte[0];
        try {
            textEncriptatEnBytes = new BASE64Decoder().decodeBuffer(textencriptat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Pasamos el array de bytes a la funcion que busca la llave y desencripta el mensage
        hackPassMisage(textEncriptatEnBytes);

    }

    private static SecretKey passwordKeyGeneration(int text, int keySize) {
        SecretKey sKey = null;
        String clau;

        clau =String.valueOf(text);

        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                if (clau.length() < 2) {
                    clau = "0" + clau;
                }
                byte[] data = clau.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize/8);
                sKey = new SecretKeySpec(key, algoritmo);
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }

    private static String encriptar(String texto, SecretKey skey){
        String value="";
        try {
            Cipher cipher = Cipher.getInstance(algoritmo);
            cipher.init( Cipher.ENCRYPT_MODE, skey );
            byte[] textobytes = texto.getBytes();
            byte[] cipherbytes = cipher.doFinal( textobytes );
            value = new BASE64Encoder().encode( cipherbytes );
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println( ex.getMessage() );
        }
        return value;
    }

    private static void hackPassMisage(byte[] misatgeencriptat) {

        System.out.println("Cercant clau...");

        //Se generan todas las contaseñas
        for (int num = 0; num < 100; num++) {
            String pass = Integer.toString(num);
            while (pass.length() < 2) {
                pass = "0" + pass;
            }

            //Se genera la llave a partir de la posible contraseña
            SecretKey sKey = null;
            try {
                byte[] data = pass.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keysize / 8);
                sKey = new SecretKeySpec(key, algoritmo);
            } catch (Exception ex) {
                System.err.println("No s'ha pogut generar la clau:" + ex);
            }

            //Si la llave no es null entra e intenta descifrar el mensage con esa llave
            if (sKey != null) {
                try {
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, sKey);
                    byte[] data = cipher.doFinal(misatgeencriptat);
                    //S'ha desxifrat alguna cosa, però serà un text llegible?
                    System.out.print("Possible clau trobada: " + pass + " => ");
                    System.out.println(new String(data));
                } catch (Exception ex) {
                   //Si encuentra un error significa que la llave no es correcta
                }
            }
        }
    }

}