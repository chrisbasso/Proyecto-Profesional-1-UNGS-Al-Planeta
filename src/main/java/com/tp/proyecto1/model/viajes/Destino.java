package com.tp.proyecto1.model.viajes;


import javax.persistence.*;
import java.util.*;

@Entity
public class Destino {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Ciudad ciudad;

    private String recomendacion;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<TagDestino> tagsDestino = new HashSet<>();

    public Destino() {
    }

    public Destino(Ciudad ciudad, String recomendacion) {
        this.ciudad = ciudad;
        this.recomendacion = recomendacion;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destino destino = (Destino) o;
        return Objects.equals(id, destino.id) &&
                Objects.equals(ciudad, destino.ciudad);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ciudad);
    }
    
    @Override
    public String toString()
    {
    	return getCiudad()+", "+getCiudad().getPais();
    }
}
