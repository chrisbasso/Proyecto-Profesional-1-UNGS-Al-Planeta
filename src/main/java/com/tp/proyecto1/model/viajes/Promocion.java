package com.tp.proyecto1.model.viajes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Promocion
{
	@Id
    @GeneratedValue
    private Long id;
	
	private String nombrePromocion;
	
	private String descripcion;
	
	private LocalDate fechaVencimiento;
	
	private Float multiplicadorPuntos;
	
	public Promocion()
	{
		
	}
	
	public Promocion(String nombrePromocion, String descripcion, LocalDate fechaVencimiento, Float multiplicadorPuntos)
	{
		setNombrePromocion(nombrePromocion);
		setDescripcion(descripcion);
		setFechaVencimiento(fechaVencimiento);
		setMultiplicadorPuntos(multiplicadorPuntos);
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

	public Float getMultiplicadorPuntos()
	{
		return multiplicadorPuntos;
	}

	public void setMultiplicadorPuntos(Float multiplicadorPuntos)
	{
		this.multiplicadorPuntos = multiplicadorPuntos;
	}
		    
	    
}
