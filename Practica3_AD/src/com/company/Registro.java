package com.company;

public class Registro {

    private String ABM;
    private String Tipus;
    private String Data;
    private String Matricula;
    private String BASTIDOR ;
    private String N_MOTOR;
    private String DNI;
    private String GOGNOMS_NOM;
    private String ADREÇA;

    public Registro() {
    }

    public Registro(String ABM, String tipus, String data, String matricula, String BASTIDOR, String n_MOTOR, String DNI, String GOGNOMS_NOM, String ADREÇA) {
        this.ABM = ABM;
        Tipus = tipus;
        Data = data;
        Matricula = matricula;
        this.BASTIDOR = BASTIDOR;
        N_MOTOR = n_MOTOR;
        this.DNI = DNI;
        this.GOGNOMS_NOM = GOGNOMS_NOM;
        this.ADREÇA = ADREÇA;
    }

    public String getABM() {
        return ABM;
    }

    public void setABM(String ABM) {
        this.ABM = ABM;
    }

    public String getTipus() {
        return Tipus;
    }

    public void setTipus(String tipus) {
        Tipus = tipus;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getBASTIDOR() {
        return BASTIDOR;
    }

    public void setBASTIDOR(String BASTIDOR) {
        this.BASTIDOR = BASTIDOR;
    }

    public String getN_MOTOR() {
        return N_MOTOR;
    }

    public void setN_MOTOR(String n_MOTOR) {
        N_MOTOR = n_MOTOR;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getGOGNOMS_NOM() {
        return GOGNOMS_NOM;
    }

    public void setGOGNOMS_NOM(String GOGNOMS_NOM) {
        this.GOGNOMS_NOM = GOGNOMS_NOM;
    }

    public String getADREÇA() {
        return ADREÇA;
    }

    public void setADREÇA(String ADREÇA) {
        this.ADREÇA = ADREÇA;
    }

}


