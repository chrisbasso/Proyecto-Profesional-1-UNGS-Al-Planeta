package com.tp.proyecto1.controllers;

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
    }



    private void listVentas() {
//        Viaje viajeBusqueda = new Viaje();
//        if(checkFiltros()){
//            setParametrosBusqueda(viajeBusqueda);
//            ventaView.getGrid().setItems(viajeService.findViajes(viajeBusqueda, ventaView.getFechaDesdeFilter().getValue(), ventaView.getFechaHastaFilter().getValue()));
//        }else{
//            ventaView.getGrid().setItems(viajeService.findAll());

    }
//
//    private void setParametrosBusqueda(Viaje viajeBusqueda) {
//        viajeBusqueda.setDestino(new Destino());
//        viajeBusqueda.setTransporte(new Transporte());
//        if(!ventaView.getIdFilter().isEmpty()){
//            viajeBusqueda.setId(ventaView.getIdFilter().getValue().longValue());
//        }
//        if(!ventaView.getCiudadFilter().isEmpty()){
//            viajeBusqueda.getDestino().setCiudad(ventaView.getCiudadFilter().getValue());
//        }
//        if(!ventaView.getPaisFilter().isEmpty()){
//            viajeBusqueda.getDestino().setPais(ventaView.getPaisFilter().getValue());
//        }
//        if(!ventaView.getCodTransporteFilter().isEmpty()){
//            viajeBusqueda.getTransporte().setCodTransporte(ventaView.getCodTransporteFilter().getValue());
//        }
//        if(!ventaView.getTransporteFilter().isEmpty()){
//            viajeBusqueda.getTransporte().setTipo(ventaView.getTransporteFilter().getValue());
//        }
//
//        viajeBusqueda.setActivo(ventaView.getActivosCheck().getValue());
//    }
//
//    private boolean checkFiltros() {
//        return !ventaView.getIdFilter().isEmpty() || !ventaView.getCodTransporteFilter().isEmpty() ||
//                !ventaView.getPaisFilter().isEmpty() || !ventaView.getCiudadFilter().isEmpty() ||
//                ventaView.getActivosCheck().getValue() || ventaView.getFechaDesdeFilter()!=null ||
//                ventaView.getFechaHastaFilter() != null;
//    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public VentaView getView(){
        return ventaView;
    }
}
