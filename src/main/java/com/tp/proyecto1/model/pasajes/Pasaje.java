package com.tp.proyecto1.model.pasajes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@OneToMany(mappedBy = "pasaje")
	protected List<Pago> pagos;

	public Pasaje(Viaje viaje, Cliente cliente, FormaDePago formaDePago, LocalDate fechaPago) {
		this.viaje = viaje;
		this.cliente = cliente;
		this.pagos = pagos;		
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

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Pasaje() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pasaje pasaje = (Pasaje) o;
		return Objects.equals(id, pasaje.id) &&
				Objects.equals(viaje, pasaje.viaje) &&
				Objects.equals(cliente, pasaje.cliente) &&
				Objects.equals(formaDePago, pasaje.formaDePago);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, viaje, cliente, formaDePago);
	}
}
}