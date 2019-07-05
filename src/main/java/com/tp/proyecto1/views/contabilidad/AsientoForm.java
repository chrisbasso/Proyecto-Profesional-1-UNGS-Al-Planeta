package com.tp.proyecto1.views.contabilidad;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.contabilidad.TipoPosicion;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.utils.ConfigDatePicker;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		ConfigDatePicker configDatePicker = new ConfigDatePicker();
		configDatePicker.setearLenguajeEspañol(fechaContabilizacion);
    	List <Sucursal> sucursales = new ArrayList<Sucursal>();
    	sucursales.add(asiento.getSucursal());
    	sucursal.setItems(sucursales);
    	sucursal.setValue(asiento.getSucursal());
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
		posiciones.setColumns("tipoPosicion", "cuenta.numeroCuenta","cuenta.descripcion", "importe");
		posiciones.getColumnByKey("tipoPosicion").setHeader("D/H");
		posiciones.getColumnByKey("cuenta.numeroCuenta").setHeader("Nro Cuenta");
		posiciones.getColumnByKey("cuenta.descripcion").setHeader("Descripcion");
	}
    
    private void inicializarActions() {
    	btnGuardar = new Button("Guardar",VaadinIcon.CHECK_CIRCLE.create());
    	btnCancelar = new Button("Cancelar",VaadinIcon.CLOSE_CIRCLE.create());
    	actions = new HorizontalLayout();
    	actions.add(btnGuardar,btnCancelar);
    }
    
    private void inicializarMain() {
    	HorizontalLayout etiquetas = crearEtiquetas("0", "0", "0");				
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
		HorizontalLayout etiquetas = crearEtiquetas(debe, haber, saldo);				
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

    private HorizontalLayout crearEtiquetas(String debe, String haber, String saldo) {
		HorizontalLayout etiquetas = new HorizontalLayout();
		sumaDebe = new TextField("Suma Debe");
		sumaDebe.setValue(debe);
		sumaDebe.setEnabled(false);		
		sumaHaber = new TextField("Suma Haber");
		sumaHaber.setValue(haber);
		sumaHaber.setEnabled(false);
		this.saldo = new TextField("Saldo");
		this.saldo.setValue(saldo);
		this.saldo.setEnabled(false);
		etiquetas.add(sumaDebe);
		etiquetas.add(sumaHaber);
		etiquetas.add(this.saldo);
		return etiquetas;
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
    	LocalDate ret = null;
    	if(fechaContabilizacion.getValue() != null) {
    		ret = fechaContabilizacion.getValue();
    	}
    	return ret;
    }
    
    public String getTextoCabecera() {
    	String ret = "";
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
    
    public TipoPosicion getTipoPosicion() {
    	TipoPosicion ret = null;
    	if(tipoPosicion.getValue() != null) {
    		ret = tipoPosicion.getValue();
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
    
    public Double getImporte() {
    	Double ret = null;
    	if(importe.getValue() != null) {
    		ret = importe.getValue();
    	}
    	return ret;
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