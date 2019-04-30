package com.tp.proyecto1.model.LotePunto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LotePunto
{

	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate fechaAlta;
	private LocalDate fechaVencimiento;
	private Integer cantidadPuntos;
	private Boolean isActivo;
	
	public LotePunto()
	{
		
	}
	
	public LotePunto(LocalDate fechaAlta, LocalDate fechaVencimiento, Integer cantidadPuntos, Boolean isActivo)
	{
		setFechaAlta(fechaAlta);
		setFechaVencimiento(fechaVencimiento);
		setCantidadPuntos(cantidadPuntos);
		setIsActivo(isActivo);
	}

	public LocalDate getFechaAlta()
	{
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta)
	{
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento)
	{
		this.fechaVencimiento = fechaVencimiento;
	}

	public Integer getCantidadPuntos()
	{
		return cantidadPuntos;
	}

	public void setCantidadPuntos(Integer cantidadPuntos)
	{
		this.cantidadPuntos = cantidadPuntos;
	}

	public Boolean getIsActivo()
	{
		return isActivo;
	}

	public void setIsActivo(Boolean isActivo)
	{
		this.isActivo = isActivo;
	}
	
}
