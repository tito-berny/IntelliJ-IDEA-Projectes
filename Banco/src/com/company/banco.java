package com.company;

public class banco {

    //--------------VARIABLES-----------

    private String nom;
    private double saldo = 200000;

    public banco() {
    }

    public banco(String nom, double saldo) {
        this.nom = nom;
        this.saldo = saldo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Realiza la transferencia de saldo de una cuenta 'a' a otra cuenta 'b'.
     * @param a Cuenta de origen
     * @param b Cuenta de destino
     * @param cantidad Cantidad de saldo a traspasar.
     */
    public void transferencia (cuenta a, cuenta b, int cantidad){

        //Comprovar que la cuenta de origen tenga saldo
        if (a.getSaldo()<= 0){
            System.out.println("La cuenta de origen no tiene saldo !");
        }else {

            //Si tiene saldo y no queda en numeros negativos despues de
            //la operacion se ejecuta la transacion
            if (a.getSaldo() - cantidad < 0){
                System.out.println("No se puede realizar la operacion por que" +
                        " la cuenta quedara en numeros negativos !");

            }else {
                //restar a cuenta a
                a.setSaldo(a.getSaldo() - cantidad);
                //sumar a cuenta b
                b.setSaldo(b.getSaldo() + cantidad);
            }
        }



    }
}
