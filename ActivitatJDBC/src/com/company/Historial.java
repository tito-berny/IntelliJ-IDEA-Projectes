package com.company;

public class Historial {

    private int Cont_Id;
    private int Vehi_Id;
    private String Data_Alta;
    private int Data_Baixa;

    public Historial() {
    }

    public Historial(int cont_Id, int vehi_Id, String data_Alta, int data_Baixa) {
        Cont_Id = cont_Id;
        Vehi_Id = vehi_Id;
        Data_Alta = data_Alta;
        Data_Baixa = data_Baixa;

    }

    public int getCont_Id() {
        return Cont_Id;
    }

    public void setCont_Id(int cont_Id) {
        Cont_Id = cont_Id;
    }

    public int getVehi_Id() {
        return Vehi_Id;
    }

    public void setVehi_Id(int vehi_Id) {
        Vehi_Id = vehi_Id;
    }

    public String getData_Alta() {
        return Data_Alta;
    }

    public void setData_Alta(String data_Alta) {
        Data_Alta = data_Alta;
    }

    public int getData_Baixa() {
        return Data_Baixa;
    }

    public void setData_Baixa(int data_Baixa) {
        Data_Baixa = data_Baixa;
    }
}
