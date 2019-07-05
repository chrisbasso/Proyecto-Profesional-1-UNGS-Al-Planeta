package com.tp.proyecto1.model.pasajes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

import javax.persistence.Entity;

@Entity
public class PasajeReserva extends Pasaje {


    public PasajeReserva() {
    }
    
    public PasajeReserva(Viaje viaje, Cliente cliente) {
    	super(viaje, cliente);
    }

}
