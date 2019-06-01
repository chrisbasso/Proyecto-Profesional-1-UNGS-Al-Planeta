package com.tp.proyecto1.model.configuraciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class Configuracion {

	@Id
	@GeneratedValue
	private Long id;

	@NaturalId
	private String clave;
	private String value;

	public Configuracion() {

	}

	public Configuracion(String clave, String value) {
		this.clave = clave;
		this.value = value;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}
}
