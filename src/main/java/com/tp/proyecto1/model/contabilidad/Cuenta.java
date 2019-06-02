package com.tp.proyecto1.model.contabilidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Cuenta {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private int numeroCuenta;
	private String descripcion;
	private TipoCuenta tipoCuenta;

	public Cuenta() {		
	}
	
	public Cuenta(int numeroCuenta, String descripcion, TipoCuenta tipoCuenta) {
		this.numeroCuenta = numeroCuenta;
		this.descripcion = descripcion;
		this.tipoCuenta = tipoCuenta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	@Override
	public String toString() {
		return numeroCuenta + " " + descripcion;
	}
}
