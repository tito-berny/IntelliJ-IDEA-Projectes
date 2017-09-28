package com.company;

public class jefe extends Empleado {


    private double incentivo;

    public double getIncentivo() {
        return incentivo;
    }

    public void estableceincentivo(double incentivo) {
        this.incentivo = incentivo;
    }

    public jefe(String dni, String nom, String cognom, String seccio, double sou, double incentivo) {
        super(dni, nom, cognom, seccio, sou);
        this.incentivo = incentivo;
    }

    public jefe() {
    }
}
