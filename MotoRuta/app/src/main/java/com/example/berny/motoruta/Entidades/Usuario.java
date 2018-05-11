package com.example.berny.motoruta.Entidades;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int id;
    private String alias;
    private String email;
    private String pass;
    private String moto;
    private int nivel;


    public Usuario(int id) {
    }

    public Usuario(int id, String alias, String email, String pass, String moto, int nivel) {
        this.id = id;
        this.alias = alias;
        this.email = email;
        this.pass = pass;
        this.moto = moto;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", moto='" + moto + '\'' +
                ", nivel=" + nivel +
                '}';
    }

}
