package com.tp.proyecto1.views.configuracion;

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

public class ConfiguracionForm extends Dialog{

	private TextField descripcion;
	private TextField valor;
	private Button btnGuardar;
	private FormLayout form;
	private HorizontalLayout actions; 
    private VerticalLayout mainLayout;
        
    public ConfiguracionForm() {
		inicializarCampos();
		inicializarActions();
		inicializarMain();
    }

    private void inicializarCampos() {
    	descripcion = new TextField ("Descripcion");
		valor = new TextField ("Valor");
		btnGuardar = new Button("Agregar esta configuracion", VaadinIcon.PLUS.create());
		btnGuardar.setEnabled(false);
		form = new FormLayout();    	
    	form.addFormItem(descripcion, "Nombre Config.");
    	form.addFormItem(valor, "Valor Asignado");
    	form.addFormItem(btnGuardar,"");
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
   
    public String getDescripcion() {
    	return descripcion.getValue();
    }
    
    public String getValor() {
    	return valor.getValue();
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