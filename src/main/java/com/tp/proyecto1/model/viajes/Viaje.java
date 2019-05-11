package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Viaje {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Destino destino;

	@OneToOne(cascade = CascadeType.ALL)
	private Transporte transporte;

	private LocalDate fechaSalida;
	private LocalTime horaSalida;
	private LocalDate fechaLlegada;
	private LocalTime horaLlegada;
	private Double precio;
	private String descripcion;
	private boolean activo;

	public Viaje() {
	}

	public Viaje(Destino destino, Transporte transporte, LocalDate fechaSalida, LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada, Double precio, String descripcion, boolean activo) {
		this.destino = destino;
		this.transporte = transporte;
		this.fechaSalida = fechaSalida;
		this.horaSalida = horaSalida;
		this.fechaLlegada = fechaLlegada;
		this.horaLlegada = horaLlegada;
		this.precio = precio;
		this.descripcion = descripcion;
		this.activo = activo;
	}
	
	public double getPasajesRestantes() {
		return transporte.pasajesRestantes();
	}
	
	public void restarPasajes(double cantPasajes) {
		transporte.restarPasajes(cantPasajes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public void setTransporte(Transporte transporte) {
		this.transporte = transporte;
	}

	public LocalDate getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public LocalTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	public LocalDate getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(LocalDate fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public LocalTime getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(LocalTime horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Viaje viaje = (Viaje) o;
		return activo == viaje.activo &&
				Objects.equals(id, viaje.id) &&
				Objects.equals(destino, viaje.destino) &&
				Objects.equals(transporte, viaje.transporte) &&
				Objects.equals(fechaSalida, viaje.fechaSalida) &&
				Objects.equals(horaSalida, viaje.horaSalida) &&
				Objects.equals(fechaLlegada, viaje.fechaLlegada) &&
				Objects.equals(horaLlegada, viaje.horaLlegada) &&
				Objects.equals(precio, viaje.precio) &&
				Objects.equals(descripcion, viaje.descripcion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, destino, transporte, fechaSalida, horaSalida, fechaLlegada, horaLlegada, precio, descripcion, activo);
	}

	public Transporte getTransporte() {
		return transporte;
	}
}
