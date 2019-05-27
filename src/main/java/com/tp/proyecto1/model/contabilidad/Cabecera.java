package com.tp.proyecto1.model.contabilidad;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.users.User;
@Entity
public class Cabecera {
	@Id
	@GeneratedValue
	private Long id;	
	private LocalDate fecha;
	@OneToOne
	private User usuario;
	private String textoCabecera;
	private boolean anulado;
	
	public Cabecera() {		
	}
	public Cabecera(LocalDate fecha, User usuario, String textoCabecera) {
		this.fecha = fecha;
		this.usuario = usuario;
		this.textoCabecera = textoCabecera;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public Long getId() {
		return id;
	}	
	public void setAnulado() {
		anulado = true;
	}
	public boolean getAnulado() {
		return anulado;
	}
	public String getTextoCabecera() {
		return textoCabecera;
	}
	public void setTextoCabecera(String textoCabecera) {
		this.textoCabecera = textoCabecera;
	}	
}