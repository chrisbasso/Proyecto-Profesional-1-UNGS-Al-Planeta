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

	private double capacidad;
	private double capacidadRestante;
	private String clase;

	public Transporte() {
	}

	public Transporte(String codTransporte, TipoTransporte tipo, double capacidad, String clase) {
		this.codTransporte = codTransporte;
		this.tipo = tipo;
		this.capacidad = capacidad;
		capacidadRestante = capacidad;
		this.clase = clase;
	}
	
	public boolean restarPasajes(double cantPasajes) {
		if(capacidadRestante == 0 || capacidadRestante < cantPasajes) {
			return false;
		}else{			
			capacidadRestante -= cantPasajes;
			return true;
		}
	}
	
	public double pasajesRestantes() {
		return capacidadRestante;
	}

	public Long getId() {
		return id;
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

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
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
