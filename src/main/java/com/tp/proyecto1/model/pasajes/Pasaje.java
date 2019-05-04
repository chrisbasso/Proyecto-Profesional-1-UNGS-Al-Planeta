package com.tp.proyecto1.model.pasajes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

@Entity
public abstract class Pasaje {

	@Id
	@GeneratedValue
	protected Long id;

	@OneToOne
	protected Viaje viaje;
	@OneToOne
	protected Cliente cliente;
	@OneToMany(mappedBy = "pasaje", cascade = CascadeType.ALL)
	protected List<Pago> pagos = new ArrayList<>();
	
	public Pasaje() {
	}

	public Pasaje(Viaje viaje, Cliente cliente) {
		this.viaje = viaje;
		this.cliente = cliente;
	}

	public void agregarPago(Pago pago){
		this.pagos.add(pago);
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

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List <Pago> pagos) {
		this.pagos = pagos;
	}

	public Long getId() {
		return id;
	}
}