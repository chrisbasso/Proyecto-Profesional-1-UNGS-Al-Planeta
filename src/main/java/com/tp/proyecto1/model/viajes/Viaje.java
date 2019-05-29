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

	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	private Destino destino;

	@OneToOne(cascade = CascadeType.ALL)
	private Transporte transporte;

	private LocalDate fechaSalida;
	private LocalTime horaSalida;

	private Integer duracionDias;
	private Integer duracionHoras;

	private Double precio;
	private String descripcion;
	private boolean activo;

	public Viaje() {
	}

	public Viaje(Destino destino, Transporte transporte, LocalDate fechaSalida, LocalTime horaSalida, Double precio, String descripcion, boolean activo) {
		this.destino = destino;
		this.transporte = transporte;
		this.fechaSalida = fechaSalida;
		this.horaSalida = horaSalida;
		this.precio = precio;
		this.descripcion = descripcion;
		this.activo = activo;
	}
	
	public Integer getPasajesRestantes() {
		return transporte.getCapacidadRestante();
	}
	
	public boolean restarPasajes(Integer cantPasajes) {
		return transporte.restarPasajes(cantPasajes);
	}

	public boolean agregarPasajes(Integer cantPasajes) {
		return transporte.agregarPasajes(cantPasajes);		
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

	public Integer getDuracionDias() {
		return duracionDias;
	}

	public void setDuracionDias(Integer duracionDias) {
		this.duracionDias = duracionDias;
	}

	public Integer getDuracionHoras() {
		return duracionHoras;
	}

	public void setDuracionHoras(Integer duracionHoras) {
		this.duracionHoras = duracionHoras;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public Transporte getTransporte() {
		return transporte;
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

				Objects.equals(precio, viaje.precio) &&
				Objects.equals(descripcion, viaje.descripcion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, destino, transporte, fechaSalida, horaSalida, precio, descripcion, activo);
	}
	
	@Override
	public String toString()
	{
		return getDestino().toString() + ", " + getFechaSalida().toString() + " "  +  getHoraSalida().toString() + ", " + getTransporte().getTipo().getDescripcion();
	}
}
