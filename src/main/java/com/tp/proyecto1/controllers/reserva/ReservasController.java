package com.tp.proyecto1.controllers.reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tp.proyecto1.model.viajes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.controllers.VentaFormController;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
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
    @Autowired
    private ViajeService viajeService;
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
        setComponents();
    }

    private void agregarBotonesEdicion() {
        reservaView.agregarColumnaEdicion(this::createEditButton);
        reservaView.agregarColumnaBorrado(this::createDeleteButton);
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
    
    private Button createDeleteButton(Reserva reserva) {
        Button btnBorrar = new Button(VaadinIcon.TRASH.create(), clickEvent -> borrarReserva(reserva));
    		if(!reserva.isActivo()){
    			btnBorrar.setEnabled(false);
    		}
    		return btnBorrar;
    }
    
    private void borrarReserva(Reserva reserva) {
    	String mensaje = "¿Realmente desea dar de baja la reserva del cliente " 
    						+ reserva.getCliente()
    						+ ", por "
    						+ reserva.getCantidadPasajes()
    						+ " pasajes?";
		ConfirmationDialog confirmationDialog = new ConfirmationDialog(mensaje);
		confirmationDialog.getConfirmButton().addClickListener(event -> {
			reserva.inactivar();
			Viaje viaje = reserva.getViaje();
			viaje.agregarPasajes(reserva.getCantidadPasajes());			
			reservaService.save(reserva);
			viajeService.save(viaje);
			Notification.show("Reserva dada de baja");
			changeHandler.onChange();
		});
		confirmationDialog.open();
	}

    private void setComponents() {
        reservaView.getPaisFilter().setItems(viajeService.findAllPaises());
    }

    private void setListeners() {
        reservaView.getPaisFilter().addValueChangeListener(e->setComboCiudades());
    	setChangeHandler(this::listReservas);
    	reservaView.setBtnBuscarListener(e->listReservas());
    	reservaView.setBtnVenderListener(e-> venderReserva());
    }


    private void setComboCiudades() {

        Pais pais = reservaView.getPaisFilter().getValue();
        reservaView.getCiudadFilter().setItems(pais.getCiudades());

    }


    private void listReservas() {
    	if(!reservaView.getValueNumeroCliente().equals(0L)){
    		Optional reserva = reservaService.findById(reservaView.getValueNumeroCliente());
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
        Ciudad ciudad = new Ciudad();
        viaje.setCiudad(ciudad);
        viaje.setTransporte(transporte);
        viaje.setActivo(true);
        reservaBusqueda.setViaje(viaje);
        if(!reservaView.getValueNumeroCliente().equals(0L)){
            reservaBusqueda.getCliente().setId(reservaView.getValueNumeroCliente());
        }
        if(!reservaView.getCiudadFilter().isEmpty()){
            reservaBusqueda.getViaje().setCiudad(reservaView.getCiudadFilter().getValue());
        }
        if(!reservaView.getPaisFilter().isEmpty()){
            reservaBusqueda.getViaje().getCiudad().setPais(reservaView.getPaisFilter().getValue());
        }
        if(!reservaView.getValueCodTransporte().equals("")){
            reservaBusqueda.getViaje().getTransporte().setCodTransporte(reservaView.getValueCodTransporte());
        }
        if(reservaView.getValueFecha() != null){
            reservaBusqueda.getViaje().setFechaSalida(reservaView.getValueFecha());
        }
    }

    private boolean checkFiltros() {
        return !reservaView.getValuePais().equals("") || !reservaView.getValueCiudad().equals("")  ||
                !reservaView.getValueCodTransporte().equals("") || !reservaView.getValueNumeroCliente().equals("")  ||
                 reservaView.getValueFecha() != null;
    }

    private void venderReserva() {
    	Reserva reservaSeleccionada = reservaView.getReservaSeleccionada();
    	if (reservaSeleccionada != null) {
	    	ventaFormController = new VentaFormController(reservaSeleccionada);
			ventaFormController.getVentaReservaFormCompra().open();
			ventaFormController.setChangeHandler(this::listReservas);
    	}
    	else {
    			Notification.show("Seleccione un Venta");
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