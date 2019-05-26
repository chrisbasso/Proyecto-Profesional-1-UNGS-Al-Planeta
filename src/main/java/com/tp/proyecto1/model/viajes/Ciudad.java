package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ciudad {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @ManyToOne
    private Pais pais;

    public Ciudad() {
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
