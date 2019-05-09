package com.tp.proyecto1.model.viajes;

import java.time.LocalDate;

public class PromocionPuntos extends Promocion
{

	Float multiplicadorPuntos;
	
	public PromocionPuntos()
	{
		
	}
	
	public PromocionPuntos(String nombrePromocion, String descripcion, LocalDate fechaVencimiento, String codigoPromocion, Float multiplicadorPuntos)
	{
		super(nombrePromocion, descripcion, fechaVencimiento, codigoPromocion);
		setMultiplicadorPuntos(multiplicadorPuntos);
	}
	
	public Float getMultiplicadorPuntos()
	{
		return multiplicadorPuntos;
	}

	private void setMultiplicadorPuntos(Float multiplicadorPuntos)
	{
		this.multiplicadorPuntos = multiplicadorPuntos;
	}
	
}
