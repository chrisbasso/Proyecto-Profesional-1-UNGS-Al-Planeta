package com.tp.proyecto1.model.viajes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.vaadin.flow.component.dialog.Dialog;

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
	
	@OneToMany
	protected List<Viaje> viajesAfectados;
	
	@OneToMany
	protected List<Destino> destinosAfectados;
	
	@OneToMany
	protected List<TagDestino> tagsDestinoAfectados;
	
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
		viajesAfectados = new ArrayList<Viaje>();
		destinosAfectados = new ArrayList<Destino>();
		tagsDestinoAfectados = new ArrayList<TagDestino>();
		
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

	public List<Destino> getDestinosAfectados()
	{
		return destinosAfectados;
	}
	
	public void setDestinosAfectados(List<Destino> destinosAfectados)
	{
		this.destinosAfectados = destinosAfectados;
	}
	
	public List<Viaje> getViajesAfectados()
	{
		return viajesAfectados;
	}
	
	public void setViajesAfectados(List<Viaje> viajesAfectados)
	{
		this.viajesAfectados = viajesAfectados;
	}
	
	public List<TagDestino> getTagsDestinoAfectados()
	{
		return tagsDestinoAfectados;
	}
	
	public void setTagsDestinoAfectados(List<TagDestino> tagsDestinoAfectados)
	{
		this.tagsDestinoAfectados = tagsDestinoAfectados;
	}
	    
}
