package com.tp.proyecto1.model.viajes;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Viaje {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Ciudad destino;

	@OneToOne
	private Ciudad origen;

	private String recomendacion;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<TagDestino> tagsDestino = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL)
	private Transporte transporte;

	private LocalDate fechaSalida;
	private LocalTime horaSalida;

	private Integer duracionDias;
	private Integer duracionHoras;

	private Double precio;
	private String descripcion;
	private Boolean activo;

	public Viaje() {
	}

	public Viaje(Ciudad destino, Ciudad origen, Transporte transporte, LocalDate fechaSalida, LocalTime horaSalida, Double precio, String descripcion, Boolean activo) {
		this.destino = destino;
		this.origen = origen;
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

	public Ciudad getOrigen() {
		return origen;
	}

	public void setOrigen(Ciudad origen) {
		this.origen = origen;
	}

	public Boolean getActivo() {
		return activo;
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

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Viaje viaje = (Viaje) o;
		return activo == viaje.activo &&
				Objects.equals(id, viaje.id) &&
				Objects.equals(transporte, viaje.transporte) &&
				Objects.equals(fechaSalida, viaje.fechaSalida) &&
				Objects.equals(horaSalida, viaje.horaSalida) &&
				Objects.equals(precio, viaje.precio) &&
				Objects.equals(descripcion, viaje.descripcion);
	}

	public Ciudad getDestino() {
		return destino;
	}

	public void setDestino(Ciudad destino) {
		this.destino = destino;
	}

	public String getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	public Set<TagDestino> getTagsDestino() {
		return tagsDestino;
	}

	public void setTagsDestino(Set<TagDestino> tagsDestino) {
		this.tagsDestino = tagsDestino;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, destino, transporte, fechaSalida, horaSalida, precio, descripcion, activo);
	}
	
	@Override
	public String toString()
	{
		return	"Origen: " + getOrigen().getNombre() + ", Destino: " + getDestino().toString() + ", " + getFechaSalida().toString() + " "  +  getHoraSalida().toString() + ", " + getTransporte().getTipo().getDescripcion();
	}
}
