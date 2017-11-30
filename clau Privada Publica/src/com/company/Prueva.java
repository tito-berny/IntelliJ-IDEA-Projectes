package com.company;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Prueva {

    public static String asHex(byte buf[]) {

        StringBuffer strbuf=new StringBuffer(buf.length*2);

        int i;

        for (i=0; i<buf.length;i++) {

            if(((int)buf[i]&0xff) < 0x10){

                strbuf.append("0");
            }

            strbuf.append(Long.toString((int) buf[i]&0xff,16));
        }

        return strbuf.toString();
    }



    public static void main(String[] args) throws Exception {

        //Comencem instanciant un Generador de claus, utilitzant un algoritme de tipus "AES"

        KeyGenerator kgen=KeyGenerator.getInstance("AES");

        //Inicialitzem el generador especificant-li el tamany de la clau

        kgen.init(128);

        //Instanciem una clau secreta

        SecretKey clau = kgen.generateKey();

        //Codifiquem la clau en bytes

        byte[] codificada= clau.getEncoded();

        //constru�m una clau secreta de tipus "AES"

        SecretKeySpec skeySpect = new SecretKeySpec(codificada,"AES");

        //Generem un objecte de xifrat de tipus "AES"

        Cipher cipher=Cipher.getInstance("AES");

        //Guardem un missatge en text pla en un String

        String missatgeOriginal="HOLALALALAAATTTE";

        System.out.println("Aquest es el missatge: \n"+missatgeOriginal);

        // Utilitzem el mode d'encriptaci�

        cipher.init(Cipher.ENCRYPT_MODE, skeySpect);

        // Encriptem el missatge

        byte[] encriptat = cipher.doFinal(missatgeOriginal.getBytes());

        // Mostrem el missatge xifrat

        System.out.println("Missatge encriptat:  \n"+asHex(encriptat));

        // Utilitzem el mode de desencriptaci�, amb la mateixa clau

        cipher.init(Cipher.DECRYPT_MODE, skeySpect);

        // Obtenim l'array de bytes del missatge desencriptat

        byte[] desencriptat=cipher.doFinal(encriptat);

        // Imprimim el missatge desencriptat

        System.out.println("Missatge Desencriptat:  \n"+ new String(desencriptat));


    }

}
