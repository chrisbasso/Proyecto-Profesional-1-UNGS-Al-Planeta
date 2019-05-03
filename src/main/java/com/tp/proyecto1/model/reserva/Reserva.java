package com.tp.proyecto1.model.reserva;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Pasaje;
import com.tp.proyecto1.model.viajes.Viaje;

@Entity
public class Reserva extends Pasaje{

	
	public Reserva(Viaje viaje, Cliente cliente) {
		setCliente(cliente);
		setViaje(viaje);
	}

	public Reserva() {
	}
}
