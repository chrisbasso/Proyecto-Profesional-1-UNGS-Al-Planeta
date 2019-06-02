package com.tp.proyecto1.views.contabilidad;

import java.time.LocalDate;
import java.util.List;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.contabilidad.TipoPosicion;
import com.tp.proyecto1.model.sucursales.Sucursal;
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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class AsientoForm extends Dialog{

	private DatePicker fechaContabilizacion;	
	private TextField textoCabecera;
	private ComboBox<Sucursal> sucursal;
	private ComboBox<TipoPosicion> tipoPosicion;
	private ComboBox<Cuenta> cuenta;
    private NumberField importe;
    private Button btnAgregar;
    private Grid posiciones;     
	private TextField sumaDebe;
	private TextField sumaHaber;
	private TextField saldo;	
    private Button btnGuardar;
    private Button btnCancelar;
    private FormLayout formCabecera;
    private FormLayout formPosicion;
    private VerticalLayout mainLayout;
    private HorizontalLayout actions; 
        
    /*
     * Constructor para modo crear asiento
     */
    public AsientoForm() {
		inicializarCabecera();    	
		inicializarPosicion();
		inicializarGridPosiciones(); 
    	inicializarActions();
    	inicializarMain();	
    }
    /*
     * Constructor para modo visualizar asiento
     */
    public AsientoForm(Asiento asiento) {
		inicializarCabecera();
    	fechaContabilizacion.setValue(asiento.getFechaContabilizacion());
    	fechaContabilizacion.setEnabled(false);
    	sucursal.setItems(asiento.getSucursal());
    	sucursal.setEnabled(false);
    	textoCabecera.setValue(asiento.getTextoCabecera());
    	textoCabecera.setEnabled(false); 
    	inicializarGridPosiciones();
    	posiciones.setItems(asiento.getPosiciones());
    	inicializarMainVisualizar(asiento.getSumaDebe().toString(), asiento.getSumaHaber().toString(), asiento.getSaldo().toString());
    }
    
    private void inicializarCabecera() {
    	fechaContabilizacion = new DatePicker();
    	fechaContabilizacion.setRequired(true);
    	sucursal = new ComboBox<Sucursal>();
    	textoCabecera = new TextField();    	
    	formCabecera = new FormLayout();
    	formCabecera.addFormItem(fechaContabilizacion, "Fecha de contabilización");
    	formCabecera.addFormItem(sucursal, "Sucursal");
    	formCabecera.addFormItem(textoCabecera, "Texto del asiento");
    }
    
    private void inicializarPosicion() {
    	tipoPosicion = new ComboBox<TipoPosicion>();
    	tipoPosicion.setRequired(true);
    	cuenta = new ComboBox<Cuenta>();
    	cuenta.setRequired(true);    	
    	importe = new NumberField();
    	importe.setPrefixComponent(new Span("$"));
    	btnAgregar = new Button("Agregar esta posicion", VaadinIcon.PLUS.create());
    	btnAgregar.setEnabled(false);    	
    	formPosicion = new FormLayout();    	
    	formPosicion.addFormItem(tipoPosicion, "D/H");
    	formPosicion.addFormItem(cuenta, "Cuenta");
    	formPosicion.addFormItem(importe, "Importe");
    	formPosicion.addFormItem(btnAgregar,"");
    }
    
    private void inicializarGridPosiciones() {		
		posiciones = new Grid<>(Posicion.class);
		posiciones.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
		posiciones.setSelectionMode(Grid.SelectionMode.NONE);
		posiciones.setHeightByRows(true);
		posiciones.setColumns("tipoPosicion", "cuenta", "importe");
		posiciones.getColumnByKey("tipoPosicion").setHeader("D/H");
	}
    
    private void inicializarActions() {
    	btnGuardar = new Button("Guardar",VaadinIcon.CHECK_CIRCLE.create());
    	btnCancelar = new Button("Cancelar",VaadinIcon.CLOSE_CIRCLE.create());
    	actions = new HorizontalLayout();
    	actions.add(btnGuardar,btnCancelar);
    }
    
    private void inicializarMain() {
		HorizontalLayout etiquetas = new HorizontalLayout();
		sumaDebe = new TextField();
		sumaDebe.setEnabled(false);		
		sumaHaber = new TextField();
		sumaHaber.setEnabled(false);
		saldo = new TextField();
		saldo.setEnabled(false);
		etiquetas.add(sumaDebe);
		etiquetas.add(sumaHaber);
		etiquetas.add(saldo);
				
    	mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(formCabecera);
        mainLayout.add(formPosicion);
        mainLayout.add(etiquetas);
		mainLayout.add(posiciones);
        mainLayout.add(actions);
        mainLayout.setSizeFull();
    	this.add(mainLayout);
    	this.setWidth("800px");
        this.setHeight("100%");
    }
    
    private void inicializarMainVisualizar(String debe, String haber, String saldo) {
		HorizontalLayout etiquetas = new HorizontalLayout();
		sumaDebe = new TextField(debe);
		sumaDebe.setEnabled(false);		
		sumaHaber = new TextField(haber);
		sumaHaber.setEnabled(false);
		this.saldo = new TextField(saldo);
		this.saldo.setEnabled(false);
		etiquetas.add(sumaDebe);
		etiquetas.add(sumaHaber);
		etiquetas.add(this.saldo);
				
    	mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(formCabecera);
        mainLayout.add(etiquetas);
		mainLayout.add(posiciones);
        mainLayout.setSizeFull();
    	this.add(mainLayout);
    	this.setWidth("800px");
        this.setHeight("100%");
    }

    public void cargarComboSucursal(List<Sucursal>sucursales) {
    	sucursal.setItems(sucursales);
    }
    
    public void cargarComboTipoPosicion(List<TipoPosicion>tiposPosicion) {
    	tipoPosicion.setItems(tiposPosicion);
    }
    
    public void cargarComboCuentas(List<Cuenta>cuentas) {
    	cuenta.setItems(cuentas);
    }
    
    public LocalDate getFechaSeleccionada() {
    	if(fechaContabilizacion.getValue() != null) {
    		return fechaContabilizacion.getEmptyValue();
    	}
    	return fechaContabilizacion.getValue();
    }
    
    public String getTextoCabecera() {
    	return textoCabecera.getValue();
    }
    
    public Sucursal getSucursal() {
    	return sucursal.getValue();
    }
    
    public TipoPosicion getTipoPosicion() {
    	return tipoPosicion.getValue();
    }
    
    public Cuenta getCuenta() {
    	return cuenta.getValue();
    }
    
    public Double getImporte() {
    	return importe.getValue();
    }
    
    public void habilitarPosiciones() {
    	tipoPosicion.setEnabled(true);
    	cuenta.setEnabled(true);
        importe.setEnabled(true);    	
    }
    
    public void bloquearPosiciones() {
    	tipoPosicion.setEnabled(false);
    	cuenta.setEnabled(false);
        importe.setEnabled(false);    	
    }
    
	public void bloquearCabecera(){
		fechaContabilizacion.setEnabled(false);	
    	sucursal.setEnabled(false);  	
	}
    
	public void actualizarEtiquetaDebe(Double importe){
		actualizarEtiqueta(1, importe);
	}
	
	public void actualizarEtiquetaHaber(Double importe){
		actualizarEtiqueta(2, importe);
	}
	
	public void actualizarEtiquetaSaldo(Double importe){
		actualizarEtiqueta(3, importe);
	}
	
	private void actualizarEtiqueta(int nroEtiqueta, Double importe){ 
		switch (nroEtiqueta) {
			case 1:
				sumaDebe.setEnabled(true);
				sumaDebe.setValue(importe.toString());
				sumaDebe.setEnabled(false);
				break;
			case 2:
				sumaHaber.setEnabled(true);
				sumaHaber.setValue(importe.toString());
				sumaHaber.setEnabled(false);
				break;
			case 3:
				saldo.setEnabled(true);
				saldo.setValue(importe.toString());	
				saldo.setEnabled(false);
				break;
			default:
				break;
		}
	}		
	
	public void actualizarGridPosiciones(List <Posicion> posiciones){
		this.posiciones.setItems(posiciones);
	}
	
    public void habilitarBtnAgregar() {
    	btnAgregar.setEnabled(true);
    }
    
    public void deshabilitarBtnAgregar() {
    	btnAgregar.setEnabled(false);
    }
    
    public void habilitarBtnGuardar() {
    	btnGuardar.setEnabled(true);
    }
    
    public void deshabilitarBtnGuardar() {
    	btnGuardar.setEnabled(false);
    }
    
    public void setFechaListener(ValueChangeListener<? super ComponentValueChangeEvent<DatePicker, LocalDate>> e) {
    	fechaContabilizacion.addValueChangeListener(e);
    }
    
    public void setTextoListener(ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> e) {
    	textoCabecera.addValueChangeListener(e);
    }
    
    public void setSucursalListener(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<Sucursal>, Sucursal>> e) {
        sucursal.addValueChangeListener(e);
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

    public void setBtnAgregarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnAgregar.addClickListener(e);
    }
    
    public void setBtnGuardarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnGuardar.addClickListener(e);
    }
    
    public void setBtnCancelarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnCancelar.addClickListener(e);
    }
}