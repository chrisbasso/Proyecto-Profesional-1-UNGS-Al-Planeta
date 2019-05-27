package com.tp.proyecto1.model.contabilidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Cuenta {
	@Id
	@GeneratedValue
	private Long id;	
	private String descripcion;
	
	public Cuenta() {
		
	}
	public Cuenta(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		return descripcion;
	}
}
