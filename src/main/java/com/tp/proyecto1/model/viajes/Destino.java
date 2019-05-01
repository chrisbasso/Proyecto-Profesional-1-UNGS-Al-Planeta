package com.tp.proyecto1.model.viajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Destino {

    @Id
    @GeneratedValue
    private Long id;

    private String ciudad;
    private String pais;

    private String recomendacion;

    @OneToOne
    private TagDestino tagDestino;

    public Destino() {
    }

    public Destino(String ciudad, String pais, String recomendacion, TagDestino tagDestino) {
        this.ciudad = ciudad;
        this.pais = pais;
        this.recomendacion = recomendacion;
        this.tagDestino = tagDestino;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public TagDestino getTagDestino() {
        return tagDestino;
    }

    public void setTagDestino(TagDestino tagDestino) {
        this.tagDestino = tagDestino;
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
