package com.company;

public class Vehicles {

    private int Id;
    private  String Matricula;
    private String Bastidor;
    private String N_Motor;
    private String Data_Alata;
    private String Data_Baixa;
    private String Tipus_Baixa;

    public Vehicles() {
    }

    public Vehicles(int id, String matricula, String bastidor, String n_Motor, String data_Alata, String data_Baixa, String tipus_Baixa) {
        Id = id;
        Matricula = matricula;
        Bastidor = bastidor;
        N_Motor = n_Motor;
        Data_Alata = data_Alata;
        Data_Baixa = data_Baixa;
        Tipus_Baixa = tipus_Baixa;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getBastidor() {
        return Bastidor;
    }

    public void setBastidor(String bastidor) {
        Bastidor = bastidor;
    }

    public String getN_Motor() {
        return N_Motor;
    }

    public void setN_Motor(String n_Motor) {
        N_Motor = n_Motor;
    }

    public String getData_Alata() {
        return Data_Alata;
    }

    public void setData_Alata(String data_Alata) {
        Data_Alata = data_Alata;
    }

    public String getData_Baixa() {
        return Data_Baixa;
    }

    public void setData_Baixa(String data_Baixa) {
        Data_Baixa = data_Baixa;
    }

    public String getTipus_Baixa() {
        return Tipus_Baixa;
    }

    public void setTipus_Baixa(String tipus_Baixa) {
        Tipus_Baixa = tipus_Baixa;
    }
}
