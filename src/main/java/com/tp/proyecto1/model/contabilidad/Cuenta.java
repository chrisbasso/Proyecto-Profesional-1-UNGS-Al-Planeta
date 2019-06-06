package com.tp.proyecto1.model.contabilidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
public class Cuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generador_cuenta")
	@SequenceGenerator(name="generador_cuenta", sequenceName = "secuencia_cuenta")
	private Long id;

	@Column(unique = true)
	private Integer numeroCuenta;
	private String descripcion;
	private TipoCuenta tipoCuenta;

	public Cuenta() {		
	}
	
	public Cuenta(Integer numeroCuenta, String descripcion, TipoCuenta tipoCuenta) {
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

	public Integer getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(Integer numeroCuenta) {
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
