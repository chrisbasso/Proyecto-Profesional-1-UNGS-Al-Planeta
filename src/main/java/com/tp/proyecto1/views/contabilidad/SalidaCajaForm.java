package com.tp.proyecto1.views.contabilidad;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.utils.ConfigDatePicker;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.NumberField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalidaCajaForm extends Dialog{

	private DatePicker fechaContabilizacion;	
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
		ConfigDatePicker configDatePicker = new ConfigDatePicker();
		configDatePicker.setearLenguajeEspañol(fechaContabilizacion);
    	fechaContabilizacion.setRequired(true);
    	fechaContabilizacion.setLabel("Fecha");
    	fechaContabilizacion.setValue(LocalDate.now());
    	sucursal = new ComboBox<Sucursal>();
    	sucursal.setLabel("Sucursal");
    	cuenta = new ComboBox<Cuenta>();
    	cuenta.setLabel("Gasto");
    	cuenta.setRequired(true);
    	importe = new NumberField();
    	importe.setLabel("Importe");
    	importe.setPrefixComponent(new Span("$"));
    	btnAgregar = new Button("Agregar esta posicion", VaadinIcon.PLUS.create());
    	btnAgregar.setEnabled(false);
    	
    	this.add(fechaContabilizacion);
    	this.add(sucursal);
    	this.add(cuenta);
    	this.add(importe);
    	this.add(btnAgregar);
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
    
    public void habilitarBtnGuardado(Boolean activar) {
    	btnAgregar.setEnabled(activar);
    }
    
	public void fechaContabilizacionListener(ValueChangeListener<? super ComponentValueChangeEvent<DatePicker, LocalDate>> e) {
		fechaContabilizacion.addValueChangeListener(e);
	}
	
	public void sucursalListener(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<Sucursal>, Sucursal>> e) {
		sucursal.addValueChangeListener(e);
	}
	
	public void cuentaListener(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<Cuenta>, Cuenta>> e) {
		cuenta.addValueChangeListener(e);
	}
	
	public void importeListener(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
		importe.addValueChangeListener(e);
	}
    
    public LocalDate getFechaContabilizacion() {
    	LocalDate ret = null;
    	if(fechaContabilizacion.getValue() != null) {
    		ret = fechaContabilizacion.getValue();
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
    	sucursal.setReadOnly(true);
    	cuenta.setReadOnly(true);
    	importe.setReadOnly(true);
    }
}