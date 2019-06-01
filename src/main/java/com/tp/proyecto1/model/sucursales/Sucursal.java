package com.tp.proyecto1.model.sucursales;

import com.tp.proyecto1.model.pasajes.Transaccion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Sucursal {

	@Id
	@GeneratedValue
	protected Long id;

	private String descripcion;

	@OneToMany
	private List<Transaccion> transacciones;

	public Sucursal() {
	}

	public Sucursal(String descripcion) {
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

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}

	@Override
	public String toString() {
		return descripcion;
	}
	
	
}
