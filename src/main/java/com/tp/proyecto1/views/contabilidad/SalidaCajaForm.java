package com.tp.proyecto1.views.contabilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class SalidaCajaForm extends Dialog{

	private DatePicker fechaContabilizacion;	
	private TextField textoCabecera;
	private ComboBox<Sucursal> sucursal;
	private ComboBox<Cuenta> cuenta;
    private NumberField importe;
    private Button btnAgregar;
    
    /*
     * 	Crear salida de caja
     */
    public SalidaCajaForm() {
		inicializarComponentes();
    }
    
	private void inicializarComponentes() {
    	fechaContabilizacion = new DatePicker();
    	fechaContabilizacion.setRequired(true);
    	sucursal = new ComboBox<Sucursal>();
    	textoCabecera = new TextField();
    	cuenta = new ComboBox<Cuenta>();
    	cuenta.setRequired(true);    	
    	importe = new NumberField();
    	importe.setPrefixComponent(new Span("$"));
    	btnAgregar = new Button("Agregar esta posicion", VaadinIcon.PLUS.create());
    	btnAgregar.setEnabled(false);    	    	
    }
    
    public void cargarComboCuentas(List <Cuenta> items) {
    	cuenta.setItems(items);
    }
    
    public void cargarComboSucursales(List <Sucursal> items) {
    	sucursal.setItems(items);
    }
    
    public void btnAgregarListener(ComponentEventListener<ClickEvent<Button>> e) {
    	btnAgregar.addClickListener(e);
    }
    
    public LocalDate getFechaContabilizacion() {
    	LocalDate ret = null;
    	if(fechaContabilizacion.getValue() != null) {
    		ret = fechaContabilizacion.getValue();
    	}
    	return ret;
    }
    
    public String getTexto() {
    	String ret = null;
    	if(textoCabecera.getValue() != null) {
    		ret = textoCabecera.getValue();
    	}
    	return ret;
    }
    
    public Sucursal getSucursal() {
    	Sucursal ret = null;
    	if(sucursal.getValue() != null) {
    		ret = sucursal.getValue();
    	}
    	return ret;
    }
    
	public Cuenta getCuenta() {
    	Cuenta ret = null;
    	if(cuenta.getValue() != null) {
    		ret = cuenta.getValue();
    	}
    	return ret;		
	}
    
	public Double getimporte() {
    	Double ret = null;
    	if(importe.getValue() != null) {
    		ret = importe.getValue();
    	}
    	return ret;		
	}

    /*
     * Visualizar salida de caja
     */
    public SalidaCajaForm(LocalDate fecha, String txt, Sucursal suc, Cuenta cta, Double impte) {
    	inicializarComponentes();
    	cargarDatos(fecha, txt, suc, cta, impte);
    	fijarSoloLectura();
    }
    
    private void cargarDatos(LocalDate fecha, String txt, Sucursal suc, Cuenta cta, Double impte) {
    	fechaContabilizacion.setValue(fecha);
    	textoCabecera.setValue(txt);
    	/* 
    	 * Es necesario agregar una lista primero para evitar el error 
    	 * de Vaadin en el setValue.
    	 */
    	List <Sucursal> sucursales = new ArrayList<>();
    	sucursales.add(suc);
    	sucursal.setItems(sucursales);
    	sucursal.setValue(suc);    	
    	/* 
    	 * Es necesario agregar una lista primero para evitar el error 
    	 * de Vaadin en el setValue.
    	 */
    	List <Cuenta> cuentas = new ArrayList<>();
    	cuentas.add(cta);
    	cuenta.setItems(cuentas);
    	cuenta.setValue(cta);
    	
    	importe.setValue(impte);
    }
    
    private void fijarSoloLectura() {
    	fechaContabilizacion.setReadOnly(true);
    	textoCabecera.setReadOnly(true);
    	sucursal.setReadOnly(true);
    	cuenta.setReadOnly(true);
    	importe.setReadOnly(true);
    }
}