package com.tp.proyecto1.model.pasajes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

import javax.persistence.Entity;

@Entity
public class PasajeVenta extends Pasaje {


    public PasajeVenta() {
    }
    
    public PasajeVenta(Viaje viaje, Cliente cliente) {
    	super(viaje, cliente);
    }

}
