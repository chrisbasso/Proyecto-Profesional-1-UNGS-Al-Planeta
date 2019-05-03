package com.tp.proyecto1.model.pasajes;

import javax.persistence.*;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
public abstract class Pasaje {

	@Id
	@GeneratedValue
	protected Long id;

	@OneToOne
	protected Viaje viaje;
	@OneToOne
	protected Cliente cliente;
	@OneToOne
	protected FormaDePago formaDePago;

	protected LocalDate fechaPago;


	public Pasaje(Viaje viaje, Cliente cliente, FormaDePago formaDePago, LocalDate fechaPago) {
		this.viaje = viaje;
		this.cliente = cliente;
		this.formaDePago = formaDePago;
		this.fechaPago = fechaPago;
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

	public void setId(Long id) {
		this.id = id;
	}

	public FormaDePago getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
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
