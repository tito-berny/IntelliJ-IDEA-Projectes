package com.company;

public class Empleado {

    private String dni;
    private String nom;
    private String cognom;
    private String seccio;
    private double sou;

    public Empleado() {
    }

    public Empleado(String nom, double sou) {

        this.nom = nom;

        this.sou = sou;

        this.seccio = "administracio";
    }

    public Empleado(String dni, String nom, String cognom, String seccio, double sou){
        this.dni = dni;
        this.nom = nom;
        this.cognom = cognom;
        this.seccio = seccio;
        this.sou = sou;
    }


    public double getSou() {
        return sou;
    }

    public void setSou(double sou) {
        this.sou = sou;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getSeccio() {
        return seccio;
    }

    public void setSeccio(String seccio) {
        this.seccio = seccio;
    }

    public void subir_sueldo(double percentage){
        double incremento;

        incremento = percentage/100 * sou;
        sou = sou + incremento;
    }

}
