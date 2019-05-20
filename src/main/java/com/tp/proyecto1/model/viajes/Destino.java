package com.tp.proyecto1.model.viajes;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
public class Destino {

    @Id
    @GeneratedValue
    private Long id;

    private String ciudad;
    private String pais;

    private String recomendacion;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<TagDestino> tagsDestino = new HashSet<>();

    public Destino() {
    }

    public Destino(String ciudad, String pais, String recomendacion) {
        this.ciudad = ciudad;
        this.pais = pais;
        this.recomendacion = recomendacion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public Set<TagDestino> getTagsDestino() {
        return tagsDestino;
    }

    public void setTagsDestino(Set<TagDestino> tagsDestino) {
        this.tagsDestino = tagsDestino;
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
    
    @Override
    public String toString()
    {
    	return ciudad+", "+pais;
    }
}
