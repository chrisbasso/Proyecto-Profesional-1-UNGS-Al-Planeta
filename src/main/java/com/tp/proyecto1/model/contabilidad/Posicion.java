package com.tp.proyecto1.model.contabilidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Posicion {
	@Id
	@GeneratedValue
	private Long id;	
	@OneToOne
	private Cuenta cuenta;
	private Double importe;
	
	public Posicion() {		
	}
	public Posicion(Cuenta cuenta, Double importe) {
		this.cuenta = cuenta;
		this.importe = importe;
	}
	public Long getId() {
		return id;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}	
}