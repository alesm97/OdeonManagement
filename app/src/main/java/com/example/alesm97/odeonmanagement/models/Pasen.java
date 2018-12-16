package com.example.alesm97.odeonmanagement.models;

import java.util.Objects;

public class Pasen {

    private int sala;
    private boolean pasen;

    public Pasen() {
    }

    public Pasen(int sala, boolean pasen) {
        this.sala = sala;
        this.pasen = pasen;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public boolean isPasen() {
        return pasen;
    }

    public void setPasen(boolean pasen) {
        this.pasen = pasen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasen pasen1 = (Pasen) o;
        return sala == pasen1.sala &&
                pasen == pasen1.pasen;
    }

    @Override
    public int hashCode() {

        return Objects.hash(sala, pasen);
    }
}
