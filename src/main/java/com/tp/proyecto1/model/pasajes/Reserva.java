package com.tp.proyecto1.model.pasajes;

import com.tp.proyecto1.model.clientes.Cliente;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Reserva extends Transaccion{
	
	public Reserva() {		
	}

	public Reserva(List<Pasaje> pasajes, List<Pago> pagos, Double importeTotal, Cliente cliente) {
		super(pasajes,pagos,importeTotal,cliente);
	}
	
	public double getTotalPagado() {
		double total = 0.0;
		if(pagos.size()>0) {
			for(Pago pago : pagos) {
				total += pago.getImporte();
			}
		}
		return total;
	}
	
}
