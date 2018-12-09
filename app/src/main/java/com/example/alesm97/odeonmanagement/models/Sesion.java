package com.example.alesm97.odeonmanagement.models;

import java.util.Date;
import java.util.Objects;

public class Sesion {

    private String nombrePelicula, codigo;
    private Date horaPelicula;
    private int anho, dia, mes, hora, minutos, numSesion;

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

    public Date getHoraPelicula() {
        return horaPelicula;
    }

    public void setHoraPelicula(Date horaPelicula) {
        this.horaPelicula = horaPelicula;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sesion)) return false;
        Sesion sesion = (Sesion) o;
        return anho == sesion.anho &&
                dia == sesion.dia &&
                mes == sesion.mes &&
                hora == sesion.hora &&
                minutos == sesion.minutos &&
                numSesion == sesion.numSesion &&
                Objects.equals(nombrePelicula, sesion.nombrePelicula) &&
                Objects.equals(codigo, sesion.codigo) &&
                Objects.equals(horaPelicula, sesion.horaPelicula);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nombrePelicula, codigo, horaPelicula, anho, dia, mes, hora, minutos, numSesion);
    }
}
