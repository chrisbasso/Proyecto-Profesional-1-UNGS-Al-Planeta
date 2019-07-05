package com.tp.proyecto1.views.configuracion;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ConfiguracionForm extends Dialog{

	private TextField clave;
	private TextField valor;
	private Button btnGuardar;
	private FormLayout form;
	private HorizontalLayout actions; 
    private VerticalLayout mainLayout;
        
    public ConfiguracionForm() {
		inicializarCampos();
		inicializarActions();
		inicializarMainCrear();
    }

    public ConfiguracionForm(Configuracion configuracion) {
		inicializarCampos();
		cargarClave(configuracion.getClave());		
		deshabilitarClave();
		cargarValor(configuracion.getValue());
		deshabilitarValor();
		inicializarMainVisualizar();
    }

    private void inicializarCampos() {
    	clave = new TextField ("Clave");
		valor = new TextField ("Valor");		
		form = new FormLayout();    	
    	form.addFormItem(clave, "Nombre Config.");
    	form.addFormItem(valor, "Valor Asignado");    	
    }
    
    private void inicializarActions() {
    	btnGuardar = new Button("Agregar esta configuracion", VaadinIcon.PLUS.create());
		btnGuardar.setEnabled(false);
		actions = new HorizontalLayout();
    	actions.add(btnGuardar);
    }
    
    private void inicializarMainCrear() {				
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
    
    public String getClave() {
    	String ret = null;
    	if(clave.getValue() != null) {
    		ret = clave.getValue();
    	}
    	return ret;
    }
    
    public String getValor() {
    	String ret = null;
    	if(valor.getValue() != null) {
    		ret = valor.getValue();
    	}
    	return ret;
    }
    
    private void cargarValor(String value) {
    	valor.setValue(value);		
	}

	private void cargarClave(String clave) {
    	this.clave.setValue(clave);		
	}
    
    public void deshabilitarClave() {
    	clave.setEnabled(false);
    }
    
    public void deshabilitarValor() {
    	valor.setEnabled(false);
    }
    
    public void habilitarBtnGuardar() {
    	btnGuardar.setEnabled(true);
    }
    
    public void deshabilitarBtnGuardar() {
    	btnGuardar.setEnabled(false);
    }
	
    public void setValorListener(ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> e) {
        valor.addValueChangeListener(e);
    }
    
    public void setClaveListener(ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> e) {
        clave.addValueChangeListener(e);
    }
    
    public void setBtnGuardarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnGuardar.addClickListener(e);
    }
}