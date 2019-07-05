package com.tp.proyecto1.model.viajes;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class PromocionDescuento extends Promocion
{

	public PromocionDescuento()
	{
		
	}
	
	public PromocionDescuento(String nombrePromocion, String descripcion, LocalDate fechaVencimiento, String codigoPromocion, Integer nroFloat, Integer cantidadPasajes,boolean activo)
	{
		super(nombrePromocion, descripcion, fechaVencimiento, codigoPromocion,nroFloat,cantidadPasajes,activo);
		setTipoPromocion("Descuento");

	}
	
	
}
