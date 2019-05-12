package com.tp.proyecto1.controllers;

import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.ConsultaForm;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class ConsultaFormController {

    private ConsultaForm consultaForm;

    @Autowired
    private EventoService eventoService;

    private ChangeHandler changeHandler;

    public ConsultaFormController() {
        Inject.Inject(this);
        this.consultaForm = new ConsultaForm();
        setListeners();
        setComponents();
    }

    private void setComponents() {
        consultaForm.getApellido().setEnabled(false);
        consultaForm.getNombre().setEnabled(false);
        consultaForm.getEmail().setEnabled(false);
        consultaForm.getTelefono().setEnabled(false);
    }

    private void setListeners() {

        consultaForm.getCheckInteresado().addValueChangeListener(e-> cambiarAinteresado());


    }

    private void cambiarAinteresado() {

        if(consultaForm.getCheckInteresado().getValue()){
            consultaForm.getApellido().setEnabled(true);
            consultaForm.getNombre().setEnabled(true);
            consultaForm.getEmail().setEnabled(true);
            consultaForm.getTelefono().setEnabled(true);
            consultaForm.getBuscadorClientes().setEnabled(false);
        }else{
            consultaForm.getApellido().setEnabled(false);
            consultaForm.getNombre().setEnabled(false);
            consultaForm.getEmail().setEnabled(false);
            consultaForm.getTelefono().setEnabled(false);
            consultaForm.getBuscadorClientes().setEnabled(true);
        }

    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public ConsultaForm getView() {
        return consultaForm;
    }

}