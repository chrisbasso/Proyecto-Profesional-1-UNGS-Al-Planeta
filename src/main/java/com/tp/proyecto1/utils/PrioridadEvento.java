package com.tp.proyecto1.utils;

public enum PrioridadEvento {

    URGENTE("Urgente"), ALTA("Alta"), MEDIA("Media"), BAJA("Baja");

    String prioridad;

    private PrioridadEvento (String prioridad){
        this.prioridad = prioridad;
    }

}
