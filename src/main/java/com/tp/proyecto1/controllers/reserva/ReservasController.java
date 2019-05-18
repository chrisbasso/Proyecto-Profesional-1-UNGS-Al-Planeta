package com.tp.proyecto1.controllers.reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.controllers.VentaFormController;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.ReservaView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservasController {

    @Autowired
    private ReservaService reservaService;
    private ReservaFormController reservaFormController;
    private ReservaView reservaView;
    private ChangeHandler changeHandler;

    private VentaFormController ventaFormController;
    
    public ReservasController() {
        Inject.Inject(this);
        this.reservaView = new ReservaView();        
        agregarBotonesEdicion();
        setListeners();
        listReservas();
    }

    private void agregarBotonesEdicion() {
        reservaView.agregarColumnaEdicion(this::createEditButton);
    }
    
    private Button createEditButton(Reserva reserva) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
        	Viaje viaje = reserva.getViaje();
            if(ReservaFormController.esReservablePorFecha(viaje)) {
            	reservaFormController = new ReservaFormController(viaje);
                reservaFormController.getFormModificacionReserva(reserva).open();    			
    		}else {
    			Notification.show("Por la política de fechas el viaje selecionado solo se puede comprar.");
    		};
            reservaFormController.setChangeHandler(this::listReservas);
        });
    }
    
    private void setListeners() {
    	setChangeHandler(this::listReservas);
    	reservaView.setBtnBuscarListener(e->listReservas());
    	reservaView.getBtnVender().addClickListener(e-> openPasaVentaForm());
    }

    private void listReservas() {
    	if(!reservaView.getNumeroClienteFilter().equals(0L)){    		
    		Optional reserva = reservaService.findById(reservaView.getNumeroClienteFilter());
    		if(reserva.isPresent()) {
    			List<Reserva> reservas = new ArrayList<Reserva>();
    			reservas.add((Reserva)reserva.get());
        		reservaView.cargarReservas(reservas);	
    		}    		
    	}else {
	        Reserva reservaBusqueda = new Reserva();
	        if(checkFiltros()){
	            setParametrosBusqueda(reservaBusqueda);
	            reservaView.cargarReservas(reservaService.findReservas(reservaBusqueda));
	        }else {
	            reservaView.cargarReservas(reservaService.findAll());
	        }
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
        if(!reservaView.getNumeroClienteFilter().equals(0L)){
            reservaBusqueda.getCliente().setId(reservaView.getNumeroClienteFilter());
        }
        if(!reservaView.getCiudadFilter().equals("")){
            reservaBusqueda.getViaje().getDestino().setCiudad(reservaView.getCiudadFilter());
        }
        if(!reservaView.getPaisFilter().equals("")){
            reservaBusqueda.getViaje().getDestino().setPais(reservaView.getPaisFilter());
        }
        if(!reservaView.getCodTransporteFilter().equals("")){
            reservaBusqueda.getViaje().getTransporte().setCodTransporte(reservaView.getCodTransporteFilter());
        }
        if(reservaView.getFechaFilter() != null){
            reservaBusqueda.getViaje().setFechaSalida(reservaView.getFechaFilter());
        }
    }

    private boolean checkFiltros() {
        return !reservaView.getPaisFilter().equals("") || !reservaView.getCiudadFilter().equals("")  ||
                !reservaView.getCodTransporteFilter().equals("") || !reservaView.getNumeroClienteFilter().equals("")  ||
                 reservaView.getFechaFilter() != null;
    }

    private void openPasaVentaForm() {
    	Reserva reservaSeleccionada = this.reservaView.getGrid().asSingleSelect().getValue();
    	if (reservaSeleccionada != null) {
	    	ventaFormController = new VentaFormController(reservaSeleccionada);
			ventaFormController.getVentaFormCompra().open();
			ventaFormController.setChangeHandler(this::listReservas);
    	}
    	else {
    			Notification.show("Seleccione un Viaje");
    	}
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
