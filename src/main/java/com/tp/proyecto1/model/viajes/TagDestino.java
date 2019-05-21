package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class TagDestino {

    @Id
    @GeneratedValue
    private Long id;

    private String descripcion;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Destino> destinos;


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

	public Set<Destino> getDestinos() {
		return destinos;
	}

	public void setDestinos(Set<Destino> destinos) {
		this.destinos = destinos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TagDestino that = (TagDestino) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(destinos, that.destinos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, descripcion, destinos);
	}
}
