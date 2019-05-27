package com.tp.proyecto1.views.contabilidad;

import java.time.LocalDate;
import java.util.List;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.KeyUpEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class AsientoForm extends Dialog{

	private DatePicker fecha;
    private ComboBox<Cuenta> cuenta;
    private NumberField importe;
    private TextField textoCabecera;
    private Button btnGuardar;
    private Button btnCancelar;
    private VerticalLayout mainLayout;
    private HorizontalLayout actions; 
    private FormLayout form;
    
    public AsientoForm(List<Cuenta> cuentas) {
    	if(cuentas != null && cuentas.size()>0) {
    		inicializarForm();
        	cargarCombo(cuentas);
        	inicializarActions();
        	inicializarMain() ;	
    	}else {
    		Notification.show("Debe crear cuentas para poder ingresar asientos.");	
    	}    	
    }
    
    private void inicializarForm() {
    	fecha = new DatePicker();
    	fecha.setRequired(true);
    	cuenta = new ComboBox<Cuenta>();
    	cuenta.setRequired(true);
    	importe = new NumberField();
    	importe.setRequiredIndicatorVisible(true);
    	textoCabecera = new TextField();
    	form = new FormLayout();
    	form.addFormItem(fecha, "Fecha del asiento");
    	form.addFormItem(cuenta, "Cuenta");
    	form.addFormItem(importe, "Importe");
    	form.addFormItem(textoCabecera, "Texto");
    }
    
    private void cargarCombo(List<Cuenta>cuentas) {
    	cuenta.setItems(cuentas);
    }
    
    private void inicializarActions() {
    	btnGuardar = new Button("Guardar",VaadinIcon.CHECK_CIRCLE.create());
    	btnGuardar.setEnabled(false);
    	btnCancelar = new Button("Cancelar",VaadinIcon.CLOSE_CIRCLE.create());
    	actions = new HorizontalLayout();
    	actions.add(btnGuardar,btnCancelar);
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
    
    public LocalDate getFechaSeleccionada() {
    	return fecha.getValue(); 
    }
    
    public Cuenta getCuentaSeleccionada() {
    	return cuenta.getValue();
    }
    
    public Double getImporte() {
    	return importe.getValue();
    }
    
    public String getTextoCabecera() {
    	return textoCabecera.getValue();
    }
    
    public void habilitarBtnGuardar() {
    	btnGuardar.setEnabled(true);
    }
    
    public void deshabilitarBtnGuardar() {
    	btnGuardar.setEnabled(false);
    }
    
    public void setFechaListener(ValueChangeListener<? super ComponentValueChangeEvent<DatePicker, LocalDate>> e) {
    	fecha.addValueChangeListener(e);
    }
    
    public void setCuentaListener(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<Cuenta>, Cuenta>> e) {
        cuenta.addValueChangeListener(e);
    }
    
    public void setImporteListener(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
        importe.addValueChangeListener(e);
    }
    
    public void setImporteKUListener(ComponentEventListener<KeyUpEvent> e) {
        importe.addKeyUpListener(e);
    }
    
    public void setBtnGuardarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnGuardar.addClickListener(e);
    }
    
    public void setBtnCancelarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnCancelar.addClickListener(e);
    }
}