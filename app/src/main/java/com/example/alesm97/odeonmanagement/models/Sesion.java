package com.example.alesm97.odeonmanagement.models;

import java.util.Objects;

public class Sesion {

    private String nombrePelicula, codigo;
    private int anho, dia, mes, horaE, minutosE, horaS, minutosS, numSesion, sala;

    public Sesion() {

    }

    public Sesion(String nombrePelicula, int anho, int mes, int dia, int horaE, int minutosE, int horaS, int minutosS, int numSesion, int sala) {
        this.nombrePelicula = nombrePelicula;
        this.anho = anho;
        this.dia = dia;
        this.mes = mes;
        this.horaE = horaE;
        this.minutosE = minutosE;
        this.horaS = horaS;
        this.minutosS = minutosS;
        this.numSesion = numSesion;
        this.sala = sala;
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

    public int getHoraE() {
        return horaE;
    }

    public void setHoraE(int horaE) {
        this.horaE = horaE;
    }

    public int getMinutosE() {
        return minutosE;
    }

    public void setMinutosE(int minutosE) {
        this.minutosE = minutosE;
    }

    public int getHoraS() {
        return horaS;
    }

    public void setHoraS(int horaS) {
        this.horaS = horaS;
    }

    public int getMinutosS() {
        return minutosS;
    }

    public void setMinutosS(int minutosS) {
        this.minutosS = minutosS;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sesion)) return false;
        Sesion sesion = (Sesion) o;
        return anho == sesion.anho &&
                dia == sesion.dia &&
                mes == sesion.mes &&
                horaE == sesion.horaE &&
                minutosE == sesion.minutosE &&
                horaS == sesion.horaS &&
                minutosS == sesion.minutosS &&
                sala == sesion.sala &&
                numSesion == sesion.numSesion &&
                Objects.equals(nombrePelicula, sesion.nombrePelicula) &&
                Objects.equals(codigo, sesion.codigo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nombrePelicula, codigo, anho, dia, mes, horaE, minutosE, horaS, minutosS, numSesion);
    }

    //public Date getHoraPelicula() {
    //    return horaPelicula;
    //}

    /*private Date setHoraPelicula(int anho, int mes, int dia, int hora, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,anho);
        calendar.set(Calendar.MONTH,mes);
        calendar.set(Calendar.DAY_OF_MONTH,dia);
        calendar.set(Calendar.HOUR_OF_DAY,hora);
        calendar.set(Calendar.MINUTE,minutos);
        calendar.set(Calendar.SECOND,0);

        return calendar.getTime();
    }*/
}
