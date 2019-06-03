package com.tp.proyecto1.views.contabilidad;

import java.util.List;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.KeyUpEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class CuentaForm extends Dialog{

	private NumberField numero;
	private TextField descripcion;
	private ComboBox <TipoCuenta> cmbTipoCuenta;	
	private FormLayout form;
	private Button btnGuardar;
	private HorizontalLayout actions; 
    private VerticalLayout mainLayout;
        
    /*
     * Constructor para crear cuenta
     */
    public CuentaForm() {
		inicializarCampos();
		inicializarActions();
		inicializarMain();
    }
	/*
	 *	Constructor para visualizar cuenta 
	 */
    public CuentaForm(Cuenta cuenta) {
		inicializarCampos();
		numero.setValue(cuenta.getNumeroCuenta() + 0.0);
		numero.setEnabled(false);
		descripcion.setValue(cuenta.getDescripcion());
		descripcion.setEnabled(false);
		cmbTipoCuenta.setValue(cuenta.getTipoCuenta());
		cmbTipoCuenta.setEnabled(false);
		inicializarMainVisualizar();
    }

    private void inicializarCampos() {
    	numero = new NumberField ();
    	descripcion = new TextField ();
    	cmbTipoCuenta = new ComboBox<TipoCuenta>();
		form = new FormLayout();
		form.addFormItem(numero, "Numero cuenta");
    	form.addFormItem(descripcion, "Nombre cuenta");
    	form.addFormItem(cmbTipoCuenta, "Tipo de cuenta");
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

    public int getNumero() {
    	int ret = 0;
    	if(numero.getValue() != null) {
    		ret = numero.getValue().intValue();
    	}
    	return ret;
    }
    
    public String getDescripcion() {
    	String ret = "";
    	if(descripcion.getValue() != null) {
    		ret = descripcion.getValue();
    	}
    	return ret;
    }
    
    public TipoCuenta getTipoCuenta() {
    	return cmbTipoCuenta.getValue();
    }    
    
    public void cargarComboTipoCuenta(List <TipoCuenta> tipos) {
    	cmbTipoCuenta.setItems(tipos);
    }
    
    public void habilitarBtnGuardar() {
    	btnGuardar.setEnabled(true);
    }
    
    public void deshabilitarBtnGuardar() {
    	btnGuardar.setEnabled(false);
    }

    public void setNumeroListener(ComponentEventListener<KeyUpEvent> e) {
        numero.addKeyUpListener(e);
    }

    public void setDescripcionListener(ComponentEventListener<KeyUpEvent> e) {
        descripcion.addKeyUpListener(e);
    }
    
    public void setTipoCuentaListener(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<TipoCuenta>, TipoCuenta>> e) {
        cmbTipoCuenta.addValueChangeListener(e);
    }
    
    public void setBtnGuardarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnGuardar.addClickListener(e);
    }
}