package com.tp.proyecto1.views.contabilidad;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CuentaForm extends Dialog{

	private TextField descripcion;
	private FormLayout form;
	private Button btnGuardar;
	private HorizontalLayout actions; 
    private VerticalLayout mainLayout;
        
    public CuentaForm() {
		inicializarCampos();
		inicializarActions();
		inicializarMain();
    }

    public CuentaForm(Cuenta cuenta) {
		inicializarCampos();
		descripcion.setValue(cuenta.getDescripcion());	
		inicializarMainVisualizar();
    }

    private void inicializarCampos() {
    	descripcion = new TextField ();
		form = new FormLayout();    	
    	form.addFormItem(descripcion, "Nombre cuenta");
    }
    
    private void inicializarActions() {
    	btnGuardar = new Button("Guardar",VaadinIcon.CHECK_CIRCLE.create());
    	btnGuardar.setEnabled(false);
    	actions = new HorizontalLayout();
    	actions.add(btnGuardar);
    }
    
    private void inicializarMain() {				
    	mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form);        
        mainLayout.add(actions);
        mainLayout.setSizeFull();
    	this.add(mainLayout);
    	this.setWidth("800px");
        this.setHeight("100%");
    }
   
    private void inicializarMainVisualizar() {				
    	mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form);        
        mainLayout.setSizeFull();
    	this.add(mainLayout);
    	this.setWidth("800px");
        this.setHeight("100%");
    }

    public String getDescripcion() {
    	return descripcion.getValue();
    }
    
    public void habilitarBtnGuardar() {
    	btnGuardar.setEnabled(true);
    }
    
    public void deshabilitarBtnGuardar() {
    	btnGuardar.setEnabled(false);
    }
	
    public void setBtnGuardarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnGuardar.addClickListener(e);
    }
}