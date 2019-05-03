package com.tp.proyecto1.model.pasajes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Venta extends Pasaje {


    public Venta() {
    }

    public Venta(Viaje viaje, Cliente cliente, FormaDePago formaDePago, LocalDate fechaPago) {
        super(viaje, cliente, formaDePago, fechaPago);

    }


}
