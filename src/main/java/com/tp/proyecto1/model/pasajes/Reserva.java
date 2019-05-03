package com.tp.proyecto1.model.pasajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Pasaje;
import com.tp.proyecto1.model.viajes.Viaje;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Reserva extends Pasaje{

	private Double porcentajePagado;

	public Reserva() {
	}
	
	public Reserva(Viaje viaje, Cliente cliente, FormaDePago formaDePago, LocalDate fechaPago, Double porcentajePagado) {
		super(viaje, cliente, formaDePago, fechaPago);
		this.porcentajePagado = porcentajePagado;

	}

	public Double getPorcentajePagado() {
		return porcentajePagado;
	}

	public void setPorcentajePagado(Double porcentajePagado) {
		this.porcentajePagado = porcentajePagado;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Reserva reserva = (Reserva) o;
		return Objects.equals(porcentajePagado, reserva.porcentajePagado);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), porcentajePagado);
	}
}
