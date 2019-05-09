package com.tp.proyecto1.model.pasajes;

import java.util.Objects;

import javax.persistence.*;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Pasaje {

	@Id
	@GeneratedValue
	protected Long id;

	@OneToOne
	protected Viaje viaje;
	@OneToOne
	protected Cliente cliente;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	protected Pasajero pasajero;
	
	public Pasaje() {
	}

	public Pasaje(Viaje viaje, Cliente cliente) {
		this.viaje = viaje;
		this.cliente = cliente;
	}


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

	public void setId(Long id) {
		this.id = id;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pasaje pasaje = (Pasaje) o;
		return Objects.equals(id, pasaje.id) &&
				Objects.equals(viaje, pasaje.viaje) &&
				Objects.equals(cliente, pasaje.cliente) &&
				Objects.equals(pasajero, pasaje.pasajero);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, viaje, cliente, pasajero);
	}
}