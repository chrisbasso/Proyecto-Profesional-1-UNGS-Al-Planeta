package com.tp.proyecto1.model.pasajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Pasajero {

	@Id
	@GeneratedValue
	private Long id;

	private String nombreCompleto;
	private String dni;

	public Pasajero() {
	}

	public Pasajero(String nombreCompleto, String dni) {
		this.nombreCompleto = nombreCompleto;
		this.dni = dni;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pasajero pasajero = (Pasajero) o;
		return Objects.equals(id, pasajero.id) &&
				Objects.equals(nombreCompleto, pasajero.nombreCompleto) &&
				Objects.equals(dni, pasajero.dni);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombreCompleto, dni);
	}
}
