package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class TagDestino {

    @Id
    @GeneratedValue
    private Long id;

    private String descripcion;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Viaje> viajes;


    public TagDestino() {
    }

    public TagDestino(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(Set<Viaje> viajes) {
        this.viajes = viajes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDestino that = (TagDestino) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion);
    }
}
