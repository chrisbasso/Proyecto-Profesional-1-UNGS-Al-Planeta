package com.tp.proyecto1.controllers;

import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.PromocionService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.promociones.PromocionView;
import com.tp.proyecto1.views.viajes.ViajesView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;


@Controller
@UIScope
public class PromocionesController {

    private PromocionView promocionView;

    @Autowired
    private PromocionService promocionService;

    private PromocionFormController promocionFormController;
    
    private ChangeHandler changeHandler;

    public PromocionesController() {
        Inject.Inject(this);
        this.promocionView = new PromocionView();
        setListeners();
        setComponents();
        listPromociones();
    }

    private void setComponents() {
        //viajesView.getTransporteFilter().setItems(viajeService.findAllTipoTransportes());
       this.promocionView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }

    private void setListeners() {
        setChangeHandler(this::listPromociones);
        promocionView.getNewPromocionButton().addClickListener(e-> openNewPromocionForm());
        promocionView.getSearchButton().addClickListener(e-> listPromociones());
        /*promocionView.getBtnReservar().addClickListener(e-> openNewReservaForm());
        promocionView.getBtnComprar().addClickListener(e-> openNewVentaForm());*/
    }

	private void openNewPromocionForm() {
        promocionFormController = new PromocionFormController();
        promocionFormController.getPromocionForm().open();
        promocionFormController.setChangeHandler(this::listPromociones);
    }
    
    
    private Button createEditButton(Promocion promocion) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
            promocionFormController = new PromocionFormController();
            promocionFormController.setComponentsValues(promocion);
            promocionFormController.getPromocionForm().open();
            promocionFormController.setChangeHandler(this::listPromociones);
        });
    }

    private void listPromociones() {
        /*Promocion viajeBusqueda = new Promocion();
        if(checkFiltros()){
            setParametrosBusqueda(viajeBusqueda);
         //   promocionView.getGrid().setItems(viajeService.findViajes(viajeBusqueda, viajesView.getFechaDesdeFilter().getValue(), viajesView.getFechaHastaFilter().getValue()));
        }else{*/
         promocionView.getGrid().setItems(promocionService.findAll());
       // }
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
       /* if(!promocionView.getCodTransporteFilter().isEmpty()){
            viajeBusqueda.getTransporte().setCodTransporte(promocionView.getCodTransporteFilter().getValue());
        }
       /* if(!promocionView.getTransporteFilter().isEmpty()){
            viajeBusqueda.getTransporte().setTipo(promocionView.getTransporteFilter().getValue());
        }
*/
        viajeBusqueda.setActivo(promocionView.getActivosCheck().getValue());
    }

    private boolean checkFiltros() {
        return !promocionView.getIdFilter().isEmpty() || /*!promocionView.getCodTransporteFilter().isEmpty()||*/ 
                !promocionView.getPaisFilter().isEmpty() || !promocionView.getCiudadFilter().isEmpty() ||
                promocionView.getActivosCheck().getValue() || promocionView.getFechaDesdeFilter()!=null ||
                promocionView.getFechaHastaFilter() != null;
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public PromocionView getView(){
        return promocionView;
    }


}

