package com.tp.proyecto1.model.pasajes;

import java.util.List;

import javax.persistence.Entity;

import com.tp.proyecto1.model.clientes.Cliente;

@Entity
public class Venta extends Transaccion{

	public Venta() {
		
	}
	
	public Venta(List<PasajeVenta> pasajes, List<Pago> pagos, Double importeTotal, Cliente cliente) {
		super(pasajes,pagos,importeTotal,cliente);
	}

}