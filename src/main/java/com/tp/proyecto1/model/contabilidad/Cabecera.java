package com.tp.proyecto1.model.contabilidad;

import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Cabecera {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generador_cabecera")
	@SequenceGenerator(name="generador_cabecera", sequenceName = "secuencia_cabecera")
	private Long id;	
	private LocalDate fechaRegistro;
	private LocalDate fechaContabilizacion;
	private LocalDate fechaAnulacion;
	@OneToOne
	private User usuario;
	@OneToOne
	private User usuarioAnulacion;
	private String textoCabecera;
	@OneToOne
	private Sucursal sucursal;
	private boolean anulado;
	private Modulo modulo;
	
	public Cabecera() {		
	}
	public Cabecera(LocalDate fechaRegistro, LocalDate fechaContabilizacion, User usuario, String textoCabecera, Sucursal sucursal, Modulo modulo) {
		this.fechaRegistro = fechaRegistro;
		this.fechaContabilizacion = fechaContabilizacion;
		this.usuario = usuario;
		this.textoCabecera = textoCabecera;
		this.sucursal = sucursal;
		this.modulo = modulo;
	}
	public void anular(LocalDate fechaAnulacion, User usuarioAnulacion) {
		if(!anulado) {
			this.fechaAnulacion = fechaAnulacion;
			this.usuarioAnulacion = usuarioAnulacion;
			anulado = true;
		}
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public LocalDate getFechaContabilizacion() {
		return fechaContabilizacion;
	}
	public void setFechaContabilizacion(LocalDate fechaContabilizacion) {
		this.fechaContabilizacion = fechaContabilizacion;
	}
	public LocalDate getFechaAnulacion() {
		return fechaAnulacion;
	}
	public void setFechaAnulacion(LocalDate fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public User getUsuarioAnulacion() {
		return usuarioAnulacion;
	}
	public void setUsuarioAnulacion(User usuarioAnulacion) {
		this.usuarioAnulacion = usuarioAnulacion;
	}
	public String getTextoCabecera() {
		return textoCabecera;
	}
	public void setTextoCabecera(String textoCabecera) {
		this.textoCabecera = textoCabecera;
	}
	public boolean isAnulado() {
		return anulado;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Modulo getModulo() {
		return modulo;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}	
}