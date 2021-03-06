package com.tp.proyecto1.model.viajes;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Promocion
{
	@Id
    @GeneratedValue
    protected Long id;
	
	protected String nombrePromocion;
	
	protected String descripcion;
	
	protected LocalDate fechaVencimiento;
	
	protected String codigoPromocion;
	
	protected Integer doubleValue;
	
	protected String tipoPromocion;
	
	protected Integer cantidadPasajes;
	
	protected Integer cantidadPasajesRestantes;
	
	@ManyToMany(fetch = FetchType.EAGER)
	protected Set<Viaje> viajesAfectados;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	protected Set<Ciudad> ciudadesAfectadas;
	
	protected Boolean activo;
	
	public Promocion()
	{
		
	}
	
	public Promocion(String nombrePromocion, String descripcion, LocalDate fechaVencimiento, String codigoPromocion,Integer nroFloat, Integer cantidadPasajes2,Boolean activo)
	{
		setNombrePromocion(nombrePromocion);
		setDescripcion(descripcion);
		setFechaVencimiento(fechaVencimiento);
		setCodigoPromocion(codigoPromocion);
		setDoubleValue(nroFloat);
		setCantidadPasajes(cantidadPasajes2);
		setCantidadPasajesRestantes(cantidadPasajes2);
		viajesAfectados = new TreeSet<Viaje>();
		ciudadesAfectadas = new TreeSet<Ciudad>();
		setActivo(activo);
	}
	
	public Long getId()
	{
	        return id;
	}
	
	public void setId(Long id)
	{
	    this.id = id;
	}

	public String getNombrePromocion()
	{
		return nombrePromocion;
	}

	public void setNombrePromocion(String nombrePromocion)
	{
		this.nombrePromocion = nombrePromocion;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public LocalDate getFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento)
	{
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getCodigoPromocion()
	{
		return codigoPromocion;
	}

	public void setCodigoPromocion(String codigoPromocion)
	{
		this.codigoPromocion = codigoPromocion;
	}
	
	public Integer getDoubleValue()
	{
		return doubleValue;
	}

	public void setDoubleValue(Integer nroFloat)
	{
		this.doubleValue = nroFloat;
	}
	
	public String getTipoPromocion()
	{
		return tipoPromocion;
	}
	
	public void setTipoPromocion(String tipoPromocion)
	{
		this.tipoPromocion = tipoPromocion;
	}
	
	public Integer getCantidadPasajes()
	{
		return cantidadPasajes;
	}
	
	public void setCantidadPasajes(Integer cantidadPasajes2)
	{
		this.cantidadPasajes = cantidadPasajes2;
	}

	public Set<Ciudad> getCiudadesAfectadas()
	{
		return ciudadesAfectadas;
	}
	
	public void setCiudadesAfectadas(Set<Ciudad> destinosAfectados)
	{
		this.ciudadesAfectadas = destinosAfectados;
	}
	
	public Set<Viaje> getViajesAfectados()
	{
		return viajesAfectados;
	}
	
	public void setViajesAfectados(Set<Viaje> viajesAfectados)
	{
		this.viajesAfectados = viajesAfectados;
	}
	
	public Boolean isActivo()
	{
		return activo;
	}

	public void setActivo(Boolean activo)
	{
		this.activo = activo;
	}

	public Integer getCantidadPasajesRestantes() {
		return cantidadPasajesRestantes;
	}

	public void setCantidadPasajesRestantes(Integer cantidadPasajesRestantes) {
		this.cantidadPasajesRestantes = cantidadPasajesRestantes;
	}
	
	public boolean restarPasajes(Integer cantPasajes) {
		if(this.cantidadPasajesRestantes == 0 || this.cantidadPasajesRestantes < cantPasajes) {
			return false;
		}else{
			this.cantidadPasajesRestantes -= cantPasajes;
			return true;
		}
	}
	
	public boolean agregarPasajes(Integer cantPasajes) {
		if(this.cantidadPasajesRestantes + cantPasajes > this.cantidadPasajes) {
			return false;
		}else{
			this.cantidadPasajesRestantes += cantPasajes;
			return true;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Promocion promocion = (Promocion) o;
		return
				Objects.equals(id, promocion.id) &&
				Objects.equals(nombrePromocion, promocion.nombrePromocion) &&
				Objects.equals(descripcion, promocion.descripcion) &&
				Objects.equals(fechaVencimiento, promocion.fechaVencimiento) &&
				Objects.equals(codigoPromocion, promocion.codigoPromocion) &&
				Objects.equals(doubleValue, promocion.doubleValue) &&
				Objects.equals(tipoPromocion, promocion.tipoPromocion) &&
				Objects.equals(cantidadPasajes, promocion.cantidadPasajes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombrePromocion, descripcion, fechaVencimiento, codigoPromocion,doubleValue,tipoPromocion,cantidadPasajes);
	}
	
	@Override
	public String toString()
	{
		return getNombrePromocion();
	}
	    
}
