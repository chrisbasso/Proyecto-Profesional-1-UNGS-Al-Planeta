package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.views.viajes.ViajesView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@UIScope
public class ViajesController {

    private ViajesView viajesView;

    private ViajeService viajeService;

    private ViajeFormController viajeFormController;

    private ChangeHandler changeHandler;


    @Autowired
    public ViajesController(ViajeService viajeService) {
        this.viajesView = new ViajesView();
        this.viajeService = viajeService;
        setListeners();
        setComponents();
        listViajes();
    }

    private void setComponents() {
        viajesView.getTransporteFilter().setItems(viajeService.findAllTipoTransportes());
//        this.viajesView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
//        this.viajesView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }

    private void setListeners() {
        setChangeHandler(this::listViajes);
        viajesView.getNewViajeButton().addClickListener(e-> openNewViajeForm());
        viajesView.getSearchButton().addClickListener(e-> listViajes());
    }

    private void openNewViajeForm() {
        viajeFormController = new ViajeFormController(viajeService);
        viajeFormController.getViajeForm().open();
        viajeFormController.setChangeHandler(this::listViajes);
    }

    private void deleteCliente(Cliente cliente) {

//        ConfirmationDialog confirmationDialog = new ConfirmationDialog("¿Realmente desea dar de baja al Cliente?");
//        confirmationDialog.getConfirmButton().addClickListener(event -> {cliente.setActivo(false);
//            cliente.setFechaBaja(LocalDate.now());
//            viajeService.save(cliente);
//            Notification.show("Cliente fue dado de baja");
//            changeHandler.onChange();
//        });
//        confirmationDialog.open();
    }

    private Button createRemoveButton(Cliente cliente) {
        Button botonEliminar = new Button(VaadinIcon.TRASH.create(), clickEvent -> deleteCliente(cliente));
        if(!cliente.isActivo()){
            botonEliminar.setEnabled(false);
        }
        return botonEliminar;
    }
//
//    private Button createEditButton(Cliente cliente) {
////        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
////            clienteFormController = new ClienteFormController(viajeService);
////            clienteFormController.setComponentsValues(cliente);
////            clienteFormController.getClienteForm().open();
////            clienteFormController.setChangeHandler(this::listViajes);
////        });
//    }

    private void listViajes() {
        Viaje viajeBusqueda = new Viaje();
        if(checkFiltros()){
            setParametrosBusqueda(viajeBusqueda);
            viajesView.getGrid().setItems(viajeService.findViajes(viajeBusqueda));
        }else{
            viajesView.getGrid().setItems(viajeService.findAll());
        }
    }

    private void setParametrosBusqueda(Viaje viajeBusqueda) {
        if(!viajesView.getIdFilter().isEmpty()){
            viajeBusqueda.setId(viajesView.getIdFilter().getValue().longValue());
        }
        viajeBusqueda.setActivo(viajesView.getActivosCheck().getValue());
    }

    private boolean checkFiltros() {
        return !viajesView.getIdFilter().isEmpty() || !viajesView.getCodTransporteFilter().isEmpty() ||
                !viajesView.getPaisFilter().isEmpty() || !viajesView.getCiudadFilter().isEmpty() ||
                viajesView.getActivosCheck().getValue();
    }

    public interface ChangeHandler {
        void onChange();
    }
    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
    public ViajesView getView(){
        return viajesView;
    }

}

