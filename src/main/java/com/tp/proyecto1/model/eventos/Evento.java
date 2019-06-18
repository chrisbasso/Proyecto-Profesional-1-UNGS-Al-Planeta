package com.tp.proyecto1.model.eventos;


import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.users.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Evento {

	@Id
	@GeneratedValue
	private Long id;

	private String mensaje;
	private LocalDate fecha;
	private LocalTime hora;
	@ManyToOne
	private Persona persona;

	@ManyToOne
	private User creadorEvento;

	@ManyToOne
	private User cerradorEvento;

	@ManyToOne
	private User usuarioAsignado;

	private LocalDate fechaVencimiento;
	private LocalTime horaVencimiento;
	
	private Boolean isAbierto;

	private String prioridad;

	public Evento() {
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public User getCreadorEvento() {
		return creadorEvento;
	}

	public void setCreadorEvento(User creadorEvento) {
		this.creadorEvento = creadorEvento;
	}

	public User getCerradorEvento() {
		return cerradorEvento;
	}

	public void setCerradorEvento(User cerradorEvento) {
		this.cerradorEvento = cerradorEvento;
	}

	public User getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(User usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Boolean isAbierto() {
		return isAbierto;
	}

	public void setAbierto(Boolean abierto) {
		isAbierto = abierto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Evento evento = (Evento) o;
		return Objects.equals(id, evento.id) &&
				Objects.equals(mensaje, evento.mensaje) &&
				Objects.equals(fecha, evento.fecha) &&
				Objects.equals(hora, evento.hora) &&
				Objects.equals(persona, evento.persona) &&
				Objects.equals(prioridad, evento.prioridad);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, mensaje, fecha, hora, persona, prioridad);
	}

	public LocalDate getFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento)
	{
		this.fechaVencimiento = fechaVencimiento;
	}

	public LocalTime getHoraVencimiento()
	{
		return horaVencimiento;
	}

	public void setHoraVencimiento(LocalTime horaVencimiento)
	{
		this.horaVencimiento = horaVencimiento;
	}
}
