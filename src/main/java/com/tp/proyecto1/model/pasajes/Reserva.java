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

	public Reserva() {
	}
	
	public Reserva(Viaje viaje, Cliente cliente) {
		this.viaje = viaje;
		this.cliente = cliente;
	}
}
