package com.tp.proyecto1.model.viajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Destino {

    @Id
    @GeneratedValue
    private Long id;

    private String ciudad;
    private String pais;

    public Destino() {
    }

    public Destino(String ciudad, String pais) {
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destino destino = (Destino) o;
        return Objects.equals(id, destino.id) &&
                Objects.equals(ciudad, destino.ciudad) &&
                Objects.equals(pais, destino.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ciudad, pais);
    }
}
