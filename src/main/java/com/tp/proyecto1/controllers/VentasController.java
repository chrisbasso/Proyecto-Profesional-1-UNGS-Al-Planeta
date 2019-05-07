package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.PasajeVenta;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.ventas.VentaView;

import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class VentasController {

    private VentaView ventaView;

    @Autowired
    private VentaService ventaService;

    private ChangeHandler changeHandler;

    public VentasController() {
        Inject.Inject(this);
        this.ventaView = new VentaView();
        setListeners();
        setComponents();
        listVentas();

    }

    private void setComponents() {

    }

    private void setListeners() {
    	setChangeHandler(this::listVentas);
    	ventaView.getSearchButton().addClickListener(e->listVentas());
    }


    private void listVentas() {
        Venta ventaBusqueda = new Venta();
        if(checkFiltros()){
            setParametrosBusqueda(ventaBusqueda);
            ventaView.getGrid().setItems(ventaService.findVentas(ventaBusqueda));
        }else {
            ventaView.getGrid().setItems(ventaService.findAllVentas());
        }
    }

    private void setParametrosBusqueda(Venta venta) {
    	Cliente cliente = new Cliente();
    	cliente.setActivo(true);
        venta.setCliente(cliente);
        Transporte transporte = new Transporte();
        Viaje viaje = new Viaje();
        viaje.setDestino(new Destino());
        viaje.setTransporte(transporte);
        viaje.setActivo(true);
        venta.setViaje(viaje);
        if(!ventaView.getNumeroClienteFilter().isEmpty()){
            venta.getCliente().setId(ventaView.getNumeroClienteFilter().getValue().longValue());
        }
        if(!ventaView.getCiudadFilter().isEmpty()){
            venta.getViaje().getDestino().setCiudad(ventaView.getCiudadFilter().getValue());
        }
        if(!ventaView.getPaisFilter().isEmpty()){
            venta.getViaje().getDestino().setPais(ventaView.getPaisFilter().getValue());
        }
        if(!ventaView.getCodTransporteFilter().isEmpty()){
            venta.getViaje().getTransporte().setCodTransporte(ventaView.getCodTransporteFilter().getValue());
        }
        if(!ventaView.getFechaFilter().isEmpty()){
            venta.getViaje().setFechaSalida(ventaView.getFechaFilter().getValue());
        }
    }

    private boolean checkFiltros() {
        return !ventaView.getPaisFilter().isEmpty() || !ventaView.getCiudadFilter().isEmpty() ||
                !ventaView.getCodTransporteFilter().isEmpty() || !ventaView.getNumeroClienteFilter().isEmpty() ||
                 !ventaView.getFechaFilter().isEmpty();
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public VentaView getView(){
        return ventaView;
    }
}
