package com.tp.proyecto1.model.pasajes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Venta {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	private List<PasajeVenta> pasajes =  new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected List<Pago> pagos = new ArrayList<>();

	private Double importeTotal;

	@OneToOne
	private Viaje viaje;

	@OneToOne
	protected Cliente cliente;

	public Venta() {
	}

	public Venta(List<PasajeVenta> pasajes, List<Pago> pagos, Double importeTotal, Cliente cliente) {
		this.pasajes = pasajes;
		this.pagos = pagos;
		this.importeTotal = importeTotal;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PasajeVenta> getPasajes() {
		return pasajes;
	}

	public void setPasajes(List<PasajeVenta> pasajes) {
		this.pasajes = pasajes;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Venta venta = (Venta) o;
		return Objects.equals(id, venta.id) &&
				Objects.equals(pasajes, venta.pasajes) &&
				Objects.equals(pagos, venta.pagos) &&
				Objects.equals(importeTotal, venta.importeTotal) &&
				Objects.equals(cliente, venta.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, pasajes, pagos, importeTotal, cliente);
	}
}
