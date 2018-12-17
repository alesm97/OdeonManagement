package com.example.alesm97.odeonmanagement.models;

import java.util.Objects;

public class Incidencia {

    private String codigo;

    private String mensaje;

    private int critico;

    public Incidencia(String codigo, String mensaje, int critico) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.critico = critico;
    }

    public Incidencia() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCritico() {
        return critico;
    }

    public void setCritico(int critico) {
        this.critico = critico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incidencia that = (Incidencia) o;
        return critico == that.critico &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(mensaje, that.mensaje);
    }

    @Override
    public int hashCode() {

        return Objects.hash(codigo, mensaje, critico);
    }
}
