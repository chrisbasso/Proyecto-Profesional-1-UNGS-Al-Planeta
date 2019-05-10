package com.tp.proyecto1.controllers.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.ReservaView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservasController {

    private ReservaView reservaView;
    @Autowired
    private ReservaService reservaService;
    private ReservaFormController reservaFormController;
    
    private ChangeHandler changeHandler;

    public ReservasController() {
        Inject.Inject(this);
        this.reservaView = new ReservaView();
        setListeners();
        setComponents();
        listReservas();

    }

    private void setComponents() {
        reservaView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }
    
    private Button createEditButton(Reserva reserva) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
            reservaFormController = new ReservaFormController(reserva.getViaje());
            if(reservaFormController.esReservablePorFecha()) {    			    	
                reservaFormController.getReservaForm().open();    			
    		}else {
    			Notification.show("Por la política de fechas el viaje selecionado solo se puede comprar.");
    		};
            reservaFormController.setChangeHandler(this::listReservas);
        });
    }
    
    private void setListeners() {
    	setChangeHandler(this::listReservas);
    	reservaView.getSearchButton().addClickListener(e->listReservas());
    }

    private void listReservas() {
        Reserva reservaBusqueda = new Reserva();
        if(checkFiltros()){
            setParametrosBusqueda(reservaBusqueda);
            reservaView.getGrid().setItems(reservaService.findReservas(reservaBusqueda));
        }else {
            reservaView.getGrid().setItems(reservaService.findAll());
        }
    }

    private void setParametrosBusqueda(Reserva reservaBusqueda) {
    	Cliente cliente = new Cliente();
    	cliente.setActivo(true);
        reservaBusqueda.setCliente(cliente);
        Transporte transporte = new Transporte();
        Viaje viaje = new Viaje();
        viaje.setDestino(new Destino());
        viaje.setTransporte(transporte);
        viaje.setActivo(true);
        reservaBusqueda.setViaje(viaje);
        if(!reservaView.getNumeroClienteFilter().isEmpty()){
            reservaBusqueda.getCliente().setId(reservaView.getNumeroClienteFilter().getValue().longValue());
        }
        if(!reservaView.getCiudadFilter().isEmpty()){
            reservaBusqueda.getViaje().getDestino().setCiudad(reservaView.getCiudadFilter().getValue());
        }
        if(!reservaView.getPaisFilter().isEmpty()){
            reservaBusqueda.getViaje().getDestino().setPais(reservaView.getPaisFilter().getValue());
        }
        if(!reservaView.getCodTransporteFilter().isEmpty()){
            reservaBusqueda.getViaje().getTransporte().setCodTransporte(reservaView.getCodTransporteFilter().getValue());
        }
        if(!reservaView.getFechaFilter().isEmpty()){
            reservaBusqueda.getViaje().setFechaSalida(reservaView.getFechaFilter().getValue());
        }
    }

    private boolean checkFiltros() {
        return !reservaView.getPaisFilter().isEmpty() || !reservaView.getCiudadFilter().isEmpty() ||
                !reservaView.getCodTransporteFilter().isEmpty() || !reservaView.getNumeroClienteFilter().isEmpty() ||
                 !reservaView.getFechaFilter().isEmpty();
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public ReservaView getView(){
        return reservaView;
    }
}
