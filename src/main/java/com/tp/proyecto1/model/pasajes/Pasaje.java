package com.tp.proyecto1.model.pasajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

@Entity
public abstract class Pasaje {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	Viaje viaje;
	@OneToOne
	Cliente cliente;
	@OneToOne
	FormaDePago formaDePago;
	
	public Viaje getViaje() {
		return viaje;
	}
	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Long getId() {
		return id;
	}	
}
