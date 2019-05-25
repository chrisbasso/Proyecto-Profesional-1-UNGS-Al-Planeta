package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.controllers.reserva.ReservaFormController;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.viajes.ViajesView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;


@Controller
@UIScope
public class ViajesController {

    private ViajesView viajesView;

    @Autowired
    private ViajeService viajeService;

    private ViajeFormController viajeFormController;

    private VentaFormController ventaFormController;
    
	private ReservaFormController reservaFormController; 

    public ChangeHandler changeHandler;

    public ViajesController() {
        Inject.Inject(this);
        this.viajesView = new ViajesView();
        setListeners();
        setComponents();
        listViajes();
    }

    private void setComponents() {
        viajesView.getPaisFilter().setItems(viajeService.findAllPaises());
        viajesView.getTransporteFilter().setItems(viajeService.findAllTipoTransportes());
        this.viajesView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }

    private void setListeners() {
        viajesView.getPaisFilter().addValueChangeListener(e->setComboCiudades());
        setChangeHandler(this::listViajes);
        viajesView.getNewViajeButton().addClickListener(e-> openNewViajeForm());
        viajesView.getSearchButton().addClickListener(e-> listViajes());
        viajesView.getBtnReservar().addClickListener(e-> openNewReservaForm());
        viajesView.getBtnComprar().addClickListener(e-> openNewVentaForm());

    }

    private void setComboCiudades() {

        Pais pais = viajesView.getPaisFilter().getValue();
        viajesView.getCiudadFilter().setItems(pais.getCiudades());

    }

    private void openNewViajeForm() {
        viajeFormController = new ViajeFormController();
        viajeFormController.getViajeForm().open();
        viajeFormController.setChangeHandler(this::listViajes);
    }
    
    private void openNewReservaForm() {
    	Viaje seleccionado = viajesView.getGrid().asSingleSelect().getValue();
    	if(seleccionado!=null) {    		
    		if(ReservaFormController.esReservablePorFecha(seleccionado)) {
    			reservaFormController = new ReservaFormController(seleccionado);
                reservaFormController.getReservaForm().open();    			
    		}else {
    			Notification.show("Por la política de fechas el viaje selecionado solo se puede comprar.");
    		}
    	}else {
    		Notification.show("Seleccione un viaje a reservar.");
    	}    	
    }
    
    private void openNewVentaForm() {
    	Viaje viajeSeleccionado = this.viajesView.getGrid().asSingleSelect().getValue();
    	if (viajeSeleccionado != null) {
	    	ventaFormController = new VentaFormController(viajeSeleccionado);
			ventaFormController.getVentaFormCompra().open();
			ventaFormController.setChangeHandler(this::listViajes);
    	}
    	else {
    			Notification.show("Seleccione un Viaje");
    	}
	}
    
    private Button createEditButton(Viaje viaje) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
            viajeFormController = new ViajeFormController();
            viajeFormController.setComponentsValues(viaje);
            viajeFormController.getViajeForm().open();
            viajeFormController.setChangeHandler(this::listViajes);
        });
    }

    private void listViajes() {
        Viaje viajeBusqueda = new Viaje();
        if(checkFiltros()){
            setParametrosBusqueda(viajeBusqueda);
            viajesView.getGrid().setItems(viajeService.findViajes(viajeBusqueda, viajesView.getFechaDesdeFilter().getValue(), viajesView.getFechaHastaFilter().getValue()));
        }else{
            viajesView.getGrid().setItems(viajeService.findAll());
        }
    }

    private void setParametrosBusqueda(Viaje viajeBusqueda) {
        Destino destino = new Destino();
        destino.setCiudad(new Ciudad());
        viajeBusqueda.setDestino(destino);
        viajeBusqueda.setTransporte(new Transporte());
        if(!viajesView.getIdFilter().isEmpty()){
            viajeBusqueda.setId(viajesView.getIdFilter().getValue().longValue());
        }
        if(!viajesView.getCiudadFilter().isEmpty()){
            viajeBusqueda.getDestino().setCiudad(viajesView.getCiudadFilter().getValue());
        }
        if(!viajesView.getPaisFilter().isEmpty()){
            viajeBusqueda.getDestino().getCiudad().setPais(viajesView.getPaisFilter().getValue());
        }
        if(!viajesView.getCodTransporteFilter().isEmpty()){
            viajeBusqueda.getTransporte().setCodTransporte(viajesView.getCodTransporteFilter().getValue());
        }
        if(!viajesView.getTransporteFilter().isEmpty()){
            viajeBusqueda.getTransporte().setTipo(viajesView.getTransporteFilter().getValue());
        }

        viajeBusqueda.setActivo(viajesView.getActivosCheck().getValue());
    }

    private boolean checkFiltros() {
        return !viajesView.getIdFilter().isEmpty() || !viajesView.getCodTransporteFilter().isEmpty() ||
                !viajesView.getPaisFilter().isEmpty() || !viajesView.getCiudadFilter().isEmpty() ||
                viajesView.getActivosCheck().getValue() || viajesView.getFechaDesdeFilter()!=null ||
                viajesView.getFechaHastaFilter() != null;
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public ViajesView getView(){
        return viajesView;
    }


}