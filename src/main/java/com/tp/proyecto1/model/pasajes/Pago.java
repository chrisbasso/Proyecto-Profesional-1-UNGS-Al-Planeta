package com.tp.proyecto1.model.pasajes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.clientes.Cliente;

@Entity
public class Pago {

	@Id
	@GeneratedValue
	private Long id;	
	@OneToOne
	private Cliente cliente;
	@ManyToOne
	private Venta venta;
	@OneToOne
	private FormaDePago formaDePago;

	private Double importe;
	private LocalDate fechaDePago;

	public Pago() {		
	}
	
	public Pago(Cliente cliente, Venta venta, FormaDePago formaDePago, Double importe, LocalDate fechaDePago) {
		this.cliente = cliente;
		this.venta = venta;
		this.formaDePago = formaDePago;
		this.importe = importe;
		this.fechaDePago = fechaDePago;
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

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public FormaDePago getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getFechaDePago() {
		return fechaDePago;
	}

	public void setFechaDePago(LocalDate fechaDePago) {
		this.fechaDePago = fechaDePago;
	}	
}
