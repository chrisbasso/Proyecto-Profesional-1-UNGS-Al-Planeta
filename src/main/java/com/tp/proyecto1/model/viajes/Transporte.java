package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Transporte {

	@Id
	@GeneratedValue
	private Long id;

	private String codTransporte;

	@OneToOne
	private TipoTransporte tipo;

	private String capacidad;
	private String clase;

	public Transporte() {
	}

	public Transporte(String codTransporte, TipoTransporte tipo, String capacidad, String clase) {
		this.codTransporte = codTransporte;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.clase = clase;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodTransporte() {
		return codTransporte;
	}

	public void setCodTransporte(String codTransporte) {
		this.codTransporte = codTransporte;
	}

	public TipoTransporte getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransporte tipo) {
		this.tipo = tipo;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Transporte that = (Transporte) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(codTransporte, that.codTransporte) &&
				Objects.equals(tipo, that.tipo) &&
				Objects.equals(capacidad, that.capacidad) &&
				Objects.equals(clase, that.clase);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codTransporte, tipo, capacidad, clase);
	}
}
