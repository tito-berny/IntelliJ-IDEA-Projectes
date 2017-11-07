package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here

        String encriptado = Utilidades.Encriptar("Esto es una prueba");
        System.out.println(encriptado);
        String desencriptado = Utilidades.Desencriptar(encriptado);
        System.out.println(desencriptado);
    }
}
