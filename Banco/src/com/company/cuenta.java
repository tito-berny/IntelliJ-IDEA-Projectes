package com.company;

public class cuenta {

    private int Id;
    private int Saldo;
    private boolean activada;

    public cuenta() {
    }

    public cuenta(int id, int saldo, boolean activada) {
        Id = id;
        Saldo = saldo;
        this.activada = activada;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSaldo() {
        return Saldo;
    }

    public void setSaldo(int saldo) {
        Saldo = saldo;
    }

    public boolean isActivada() {
        return activada;
    }

    public void setActivada(boolean activada) {
        this.activada = activada;
    }
}
