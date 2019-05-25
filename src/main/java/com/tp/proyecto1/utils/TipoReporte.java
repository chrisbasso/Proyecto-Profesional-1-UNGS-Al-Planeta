package com.tp.proyecto1.utils;

public enum TipoReporte {

	LOCAL("Local"), DESTINO("Destino"), CLIENTE("Cliente"), EGRESOS("Egresos Servicios"), VENDEDOR("Vendedor");

	String tipo;

	private TipoReporte (String tipo){
		this.tipo = tipo;
	}

}
