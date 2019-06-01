package com.tp.proyecto1.model.pasajes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Promocion;

@Entity
public class Venta extends Transaccion{
	
	@OneToOne
	private Promocion promocion;
	
	public Venta() {
		
	}
	
	public Venta(List<Pasaje> pasajes, List<Pago> pagos, Double importeTotal, Cliente cliente) {
		super(pasajes,pagos,importeTotal,cliente);
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

}