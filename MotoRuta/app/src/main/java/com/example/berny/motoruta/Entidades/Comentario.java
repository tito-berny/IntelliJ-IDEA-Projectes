package com.example.berny.motoruta.Entidades;

import java.io.Serializable;

public class Comentario implements Serializable{

    private int id;
    private Ruta ruta_id;
    private String comentario;

    public Comentario(int id) {
    }

    public Comentario(int id, Ruta ruta_id, String comentario) {
        this.id = id;
        this.ruta_id = ruta_id;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ruta getRuta_id() {
        return ruta_id;
    }

    public void setRuta_id(Ruta ruta_id) {
        this.ruta_id = ruta_id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", ruta_id=" + ruta_id +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
