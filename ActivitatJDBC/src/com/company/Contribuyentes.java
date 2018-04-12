package com.company;

public class Contribuyentes {

    private int Id;
    private  String DNI;
    private  String Cognom_Nom;
    private  String Adreca;

    public Contribuyentes() {
    }

    public Contribuyentes(int id, String DNI, String cognom_Nom, String adreca) {
        Id = id;
        this.DNI = DNI;
        Cognom_Nom = cognom_Nom;
        Adreca = adreca;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCognom_Nom() {
        return Cognom_Nom;
    }

    public void setCognom_Nom(String cognom_Nom) {
        Cognom_Nom = cognom_Nom;
    }

    public String getAdreca() {
        return Adreca;
    }

    public void setAdreca(String adreca) {
        Adreca = adreca;
    }
}
