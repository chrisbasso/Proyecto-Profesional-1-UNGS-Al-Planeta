package com.tp.proyecto1.model.viajes;

import java.time.LocalDate;

public class PromocionDescuento extends Promocion
{

	Float porcentajeDescuento;
	
	public PromocionDescuento()
	{
		
	}
	
	public PromocionDescuento(String nombrePromocion, String descripcion, LocalDate fechaVencimiento, String codigoPromocion, Float porcentajeDescuento)
	{
		super(nombrePromocion, descripcion, fechaVencimiento, codigoPromocion);
		setPorcentajeDescuento(porcentajeDescuento);
	}
	
	public Float getPorcentajeDescuento()
	{
		return porcentajeDescuento;
	}

	private void setPorcentajeDescuento(Float multiplicadorPuntos)
	{
		this.porcentajeDescuento = multiplicadorPuntos;
	}
	
}
