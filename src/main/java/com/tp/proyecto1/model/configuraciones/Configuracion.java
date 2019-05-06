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
	private String valor;

	public Configuracion() {
		
	}
	
	public Configuracion(String clave, String valor) {
		this.clave = clave;
		this.valor = valor;
	}
	
	public Long getId() {
		return id;
	}

	public String getClave() {
		return clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
