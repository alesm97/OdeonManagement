package com.example.alesm97.odeonmanagement.models;

public class Pelicula {

    private int codigo;
    private String nombre, sinopsis, calificacion;

    public Pelicula(int codigo, String nombre, String sinopsis, String calificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.calificacion = calificacion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}
