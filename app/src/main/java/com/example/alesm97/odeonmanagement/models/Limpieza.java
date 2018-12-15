package com.example.alesm97.odeonmanagement.models;

import java.util.Date;

public class Limpieza {

    private String nombrePelicula, codigo;
    private int anho, dia, mes, hora, minutos, numSesion, sala;
    private boolean limpia;

    public Limpieza() {
    }

    public Limpieza(String nombrePelicula, int anho, int dia, int mes, int hora, int minutos, int numSesion, int sala, boolean limpia) {
        this.nombrePelicula = nombrePelicula;
        this.anho = anho;
        this.dia = dia;
        this.mes = mes;
        this.hora = hora;
        this.minutos = minutos;
        this.numSesion = numSesion;
        this.sala = sala;
        this.limpia = limpia;
        codigo = String.format("%d-%d-%d-%d-%d", dia, mes, anho, sala, numSesion);
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(int numSesion) {
        this.numSesion = numSesion;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public boolean isLimpia() {
        return limpia;
    }

    public void setLimpia(boolean limpia) {
        this.limpia = limpia;
    }

}
