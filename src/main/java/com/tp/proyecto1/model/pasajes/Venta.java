package com.tp.proyecto1.model.pasajes;

import javax.persistence.Entity;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

@Entity
public class Venta extends Pasaje {


    public Venta() {
    }
    
    public Venta(Viaje viaje, Cliente cliente) {
    	super(viaje, cliente);
    }

}
