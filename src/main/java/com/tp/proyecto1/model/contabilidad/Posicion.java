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
	private TipoPosicion debeHaber;
	
	public Posicion() {		
	}
	public Posicion(TipoPosicion debeHaber, Cuenta cuenta, Double importe) {
		this.debeHaber = debeHaber;
		this.cuenta = cuenta;
		this.importe = importe;
	}
	public Long getId() {
		return id;
	}	
	public TipoPosicion getDebeHaber() {
		return debeHaber;
	}
	public void setDebeHaber(TipoPosicion debeHaber) {
		this.debeHaber = debeHaber;
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
	public String getTipoPosicion() {
		if(debeHaber.equals(TipoPosicion.DEBE)) {
			return "D";
		}else {
			return "H";
		}
	}
}