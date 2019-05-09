package com.tp.proyecto1.model.pasajes;

import java.util.List;

import javax.persistence.Entity;

import com.tp.proyecto1.model.clientes.Cliente;

@Entity
public class Reserva extends Transaccion{
	
	public Reserva() {		
	}

	public Reserva(List<PasajeVenta> pasajes, List<Pago> pagos, Double importeTotal, Cliente cliente) {
		super(pasajes,pagos,importeTotal,cliente);
	}
	
}
