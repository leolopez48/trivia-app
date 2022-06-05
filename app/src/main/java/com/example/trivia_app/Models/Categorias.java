package com.example.trivia_app.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Categorias {
    @SerializedName("id")
    private int id;
    @SerializedName("url_img")
    private String icono;
    @SerializedName("category_name")
    private String titulo;


    public Categorias(int id, String icono, String titulo) {
        this.id = id;
        this.icono = icono;
        this.titulo = titulo;
    }

    public Categorias() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
