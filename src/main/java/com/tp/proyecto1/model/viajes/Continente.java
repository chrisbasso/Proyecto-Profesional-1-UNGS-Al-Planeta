package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Continente {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Pais> paises = new HashSet<>();

	public Continente() {
	}

	public Continente(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public Set<Pais> getPaises() {
		return paises;
	}

	public void setPaises(Set<Pais> paises) {
		this.paises = paises;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Continente that = (Continente) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(nombre, that.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}
}
