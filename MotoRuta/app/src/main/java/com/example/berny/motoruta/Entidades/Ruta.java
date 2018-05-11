package com.example.berny.motoruta.Entidades;

import java.io.Serializable;

public class Ruta implements Serializable {

    private int id;
    private String nombre;
    private String fecha;
    private int tiempo;
    private int meteorología;
    private int valoracion;
    private String path;


    public Ruta(int id) {
    }

    public Ruta(int id, String nombre, String fecha, int tiempo, int meteorología, int valoracion, String path) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.meteorología = meteorología;
        this.valoracion = valoracion;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getMeteorología() {
        return meteorología;
    }

    public void setMeteorología(int meteorología) {
        this.meteorología = meteorología;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", tiempo=" + tiempo +
                ", meteorología=" + meteorología +
                ", valoracion=" + valoracion +
                ", path='" + path + '\'' +
                '}';
    }

}
