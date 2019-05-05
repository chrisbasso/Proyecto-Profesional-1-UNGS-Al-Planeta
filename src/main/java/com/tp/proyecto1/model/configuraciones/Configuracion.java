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
	private String key;
	private String value;

	public Configuracion() {
		
	}
	
	public Configuracion(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
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
