package com.tp.proyecto1.model.viajes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Promocion
{
	@Id
    @GeneratedValue
    protected Long id;
	
	protected String nombrePromocion;
	
	protected String descripcion;
	
	protected LocalDate fechaVencimiento;
	
	protected String codigoPromocion;
	
	protected Double doubleValue;
	
	protected String tipoPromocion;
	
	protected Double cantidadPasajes;
	
	public Promocion()
	{
		
	}
	
	public Promocion(String nombrePromocion, String descripcion, LocalDate fechaVencimiento, String codigoPromocion,Double doubleValue, Double cantidadPasajes2)
	{
		setNombrePromocion(nombrePromocion);
		setDescripcion(descripcion);
		setFechaVencimiento(fechaVencimiento);
		setCodigoPromocion(codigoPromocion);
		setDoubleValue(doubleValue);
		setCantidadPasajes(cantidadPasajes2);
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
	
	public Double getDoubleValue()
	{
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue)
	{
		this.doubleValue = doubleValue;
	}
	
	public String getTipoPromocion()
	{
		return tipoPromocion;
	}
	
	public void setTipoPromocion(String tipoPromocion)
	{
		this.tipoPromocion = tipoPromocion;
	}
	
	public Double getCantidadPasajes()
	{
		return cantidadPasajes;
	}
	
	public void setCantidadPasajes(Double cantidadPasajes2)
	{
		this.cantidadPasajes = cantidadPasajes2;
	}
	    
}
