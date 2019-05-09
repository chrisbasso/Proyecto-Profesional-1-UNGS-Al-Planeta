package com.tp.proyecto1.controllers;

import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.PromocionService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.viajes.ViajesView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;


@Controller
@UIScope
public class PromocionController {

    private ViajesView promocionView;

    @Autowired
    private PromocionService promocionService;

    private PromocionFormController PromocionFormController;
    
    private ChangeHandler changeHandler;

    public PromocionController() {
        Inject.Inject(this);
        this.promocionView = new ViajesView();
        setListeners();
        setComponents();
        listViajes();
    }

    private void setComponents() {
        //viajesView.getTransporteFilter().setItems(viajeService.findAllTipoTransportes());
        this.promocionView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }

    private void setListeners() {
        setChangeHandler(this::listViajes);
        promocionView.getNewViajeButton().addClickListener(e-> openNewViajeForm());
        promocionView.getSearchButton().addClickListener(e-> listViajes());
        /*promocionView.getBtnReservar().addClickListener(e-> openNewReservaForm());
        promocionView.getBtnComprar().addClickListener(e-> openNewVentaForm());*/
    }

	private void openNewViajeForm() {
        PromocionFormController = new PromocionFormController();
        PromocionFormController.getViajeForm().open();
        PromocionFormController.setChangeHandler(this::listViajes);
    }
    
    
    private Button createEditButton(Viaje viaje) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
            PromocionFormController = new PromocionFormController();
            PromocionFormController.setComponentsValues(viaje);
            PromocionFormController.getViajeForm().open();
            PromocionFormController.setChangeHandler(this::listViajes);
        });
    }

    private void listViajes() {
        Viaje viajeBusqueda = new Viaje();
        if(checkFiltros()){
            setParametrosBusqueda(viajeBusqueda);
         //   viajesView.getGrid().setItems(viajeService.findViajes(viajeBusqueda, viajesView.getFechaDesdeFilter().getValue(), viajesView.getFechaHastaFilter().getValue()));
        }else{
          //  viajesView.getGrid().setItems(viajeService.findAll());
        }
    }

    private void setParametrosBusqueda(Viaje viajeBusqueda) {
        viajeBusqueda.setDestino(new Destino());
        viajeBusqueda.setTransporte(new Transporte());
        if(!promocionView.getIdFilter().isEmpty()){
            viajeBusqueda.setId(promocionView.getIdFilter().getValue().longValue());
        }
        if(!promocionView.getCiudadFilter().isEmpty()){
            viajeBusqueda.getDestino().setCiudad(promocionView.getCiudadFilter().getValue());
        }
        if(!promocionView.getPaisFilter().isEmpty()){
            viajeBusqueda.getDestino().setPais(promocionView.getPaisFilter().getValue());
        }
        if(!promocionView.getCodTransporteFilter().isEmpty()){
            viajeBusqueda.getTransporte().setCodTransporte(promocionView.getCodTransporteFilter().getValue());
        }
        if(!promocionView.getTransporteFilter().isEmpty()){
            viajeBusqueda.getTransporte().setTipo(promocionView.getTransporteFilter().getValue());
        }

        viajeBusqueda.setActivo(promocionView.getActivosCheck().getValue());
    }

    private boolean checkFiltros() {
        return !promocionView.getIdFilter().isEmpty() || !promocionView.getCodTransporteFilter().isEmpty() ||
                !promocionView.getPaisFilter().isEmpty() || !promocionView.getCiudadFilter().isEmpty() ||
                promocionView.getActivosCheck().getValue() || promocionView.getFechaDesdeFilter()!=null ||
                promocionView.getFechaHastaFilter() != null;
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public ViajesView getView(){
        return promocionView;
    }


}

