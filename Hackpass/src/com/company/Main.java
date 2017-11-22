package com.company;

import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static String algoritmo = "AES";
    private static int keysize=128;

    private static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
	// write your code here

        // Variables

        boolean num;
        int clau = 0;

        // Programa

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

        SecretKey key = passwordKeyGeneration(clau, keysize);

        System.out.println();

        System.out.println("Dona'm el misatge a encriptar :");
        String misatge = scn.nextLine();

        String textencriptat = encriptar(misatge, key);

        System.out.println();
        System.out.println("Contrasenya encriptada: "+textencriptat);
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
}