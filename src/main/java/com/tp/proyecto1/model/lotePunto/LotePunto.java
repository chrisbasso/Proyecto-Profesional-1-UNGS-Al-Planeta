package com.tp.proyecto1.model.lotePunto;

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
		setActivo(isActivo);
	}

	public Long getId() {
		return id;
	}

	public Boolean getActivo() {
		return isActivo;
	}

	public void setActivo(Boolean activo) {
		isActivo = activo;
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


}
