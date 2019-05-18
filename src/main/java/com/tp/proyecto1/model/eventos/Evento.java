package com.tp.proyecto1.model.eventos;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.clientes.Interesado;
import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.users.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
	@OneToOne
	private User usuarioLogueado;

	private Boolean isAbierto = true;

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

	public User getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(User usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
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

	public boolean isAbierto() {
		return isAbierto;
	}

	public void setAbierto(boolean abierto) {
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
				Objects.equals(usuarioLogueado, evento.usuarioLogueado) &&
				Objects.equals(prioridad, evento.prioridad);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, mensaje, fecha, hora, persona, usuarioLogueado, prioridad);
	}
}
