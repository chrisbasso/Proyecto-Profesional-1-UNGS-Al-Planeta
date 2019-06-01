package com.tp.proyecto1.model.contabilidad;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;

@Entity
public class Asiento {
	@Id
	@GeneratedValue
	private Long id;	
	@OneToOne
	private Cabecera cabecera;
	@OneToMany
	private List<Posicion> posiciones;
	
	public Asiento() {		
	}
	public Asiento(Cabecera cabecera, List<Posicion> posiciones) {
		this.cabecera = cabecera;
		this.posiciones = posiciones;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cabecera getCabecera() {
		return cabecera;
	}
	public void setCabecera(Cabecera cabecera) {
		this.cabecera = cabecera;
	}
	public List<Posicion> getPosiciones() {
		return posiciones;
	}
	public void setPosiciones(List<Posicion> posiciones) {
		this.posiciones = posiciones;
	}
	public LocalDate getFechaRegistro() {
		return cabecera.getFechaRegistro();
	}
	public void setFechaRegistro(LocalDate fecha) {
		cabecera.setFechaRegistro(fecha);
	}
	public User getUsuario() {
		return cabecera.getUsuario();
	}
	public void setUsuario(User usuario) {
		cabecera.setUsuario(usuario);
	}
	public boolean getAnulado() {
		return cabecera.isAnulado();
	}
	public void setAnulado(User usuarioAnulacion) {
		cabecera.anular(LocalDate.now(), usuarioAnulacion);
	}
	public void setFechaContabilizacion(LocalDate fecha) {
		cabecera.setFechaContabilizacion(fecha);
	}
	public LocalDate getFechaContabilizacion() {
		return cabecera.getFechaContabilizacion();
	}
	public Sucursal getSucursal() {
		return cabecera.getSucursal();
	}
	public String getTextoCabecera() {
		return cabecera.getTextoCabecera();
	}
	public Double getSumaDebe() {
		Double debe = 0.0;
    	for(Posicion posicion : posiciones) {
    		if(posicion.getDebeHaber() == TipoPosicion.DEBE) {
    			debe += posicion.getImporte();
    		}
    	}
		return debe;
	}
	public Double getSumaHaber() {
		Double haber = 0.0;
    	for(Posicion posicion : posiciones) {
    		if(posicion.getDebeHaber() == TipoPosicion.HABER) {
    			haber += posicion.getImporte();
    		}
    	}
		return haber;
	}
	public Double getSaldo() {
		return getSumaDebe() - getSumaHaber();  
	}
}