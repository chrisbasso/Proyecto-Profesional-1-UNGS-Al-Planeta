package com.tp.proyecto1.views.reserva;

import java.util.List;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ReservaForm extends Dialog{

    private VerticalLayout mainLayout;
    private FormLayout form;
    private HorizontalLayout actions;
    private Viaje viaje;
    private TextField id;
	private TextField pais;
	private TextField ciudad;
	private TextField codTransporte;
	private TextField transporte;
	private TextField fechaDesde;
	private TextField fechaHasta;
	private NumberField precioUnitario;
	private NumberField precioTotal;
	private ComboBox<Cliente> cmbCliente;
	private NumberField cantidadPasajes;
	private ComboBox<FormaDePago> cmbFormaPago;
	private NumberField pago;
	private NumberField saldoPagar;
	private Button btnSave;
    private Button btnCancel;
    private Button btnComprar;

    public ReservaForm(Viaje viaje) {
    	if(viaje != null) {
    		this.viaje = viaje;
    		iniciliazarCampos();
    		cargarValores();
        	setReadOnly(); 
        	inicializarForm();
        	inicializarActions();        
        	inicializarMainLayout();	
    	}else {
    		// Prevenir que invoquen el form sin un viaje
    		Notification.show("Error al invocar ReservaForm.");
    	}		
    }

	private void iniciliazarCampos() {
		id = new TextField();  
		pais= new TextField();
		ciudad= new TextField();
		codTransporte= new TextField();
		transporte= new TextField();
		fechaDesde= new TextField();
		fechaHasta= new TextField();
		precioUnitario= new NumberField();
		precioUnitario.setPrefixComponent(new Span("$"));
		cmbCliente= new ComboBox<Cliente>();
		cantidadPasajes= new NumberField();		
		cantidadPasajes.setValue(1d);
		cantidadPasajes.setMin(1);
		cantidadPasajes.setMax(Double.parseDouble(viaje.getTransporte().getCapacidad()));
		cantidadPasajes.setHasControls(true);
		precioTotal= new NumberField();
		precioTotal.setPrefixComponent(new Span("$"));
		cmbFormaPago = new ComboBox<FormaDePago>();
		pago = new NumberField();		
		pago.setPrefixComponent(new Span("$"));
		pago.setMin(0.0);
		saldoPagar = new NumberField();
		saldoPagar.setPrefixComponent(new Span("$"));
		saldoPagar.setMin(0.0);
	}

	private void cargarValores() {
		id.setValue(viaje.getId().toString());
    	pais.setValue(viaje.getDestino().getPais());
    	ciudad.setValue(viaje.getDestino().getCiudad());
    	codTransporte.setValue(viaje.getTransporte().getCodTransporte().toString());
    	transporte.setValue(viaje.getTransporte().getTipo().getDescripcion());
    	fechaDesde.setValue(viaje.getFechaSalida().toString());
    	fechaHasta.setValue(viaje.getFechaLlegada().toString());
    	precioUnitario.setValue(viaje.getPrecio());
    	precioTotal.setValue(viaje.getPrecio());
    	saldoPagar.setValue(viaje.getPrecio());
	}
    
    private void setReadOnly() {
    	id.setReadOnly(true);  
		pais.setReadOnly(true);  
		ciudad.setReadOnly(true);  
		codTransporte.setReadOnly(true);  
		transporte.setReadOnly(true);  
		fechaDesde.setReadOnly(true);  
		fechaHasta.setReadOnly(true);
		precioUnitario.setReadOnly(true);
		precioTotal.setReadOnly(true);
		saldoPagar.setReadOnly(true);
    }

	private void inicializarForm() {
		form = new FormLayout();    	
    	form.addFormItem(pais, "País");
    	form.addFormItem(ciudad, "Ciudad");
    	form.addFormItem(codTransporte, "Cod Transporte");
    	form.addFormItem(transporte, "Transporte");
    	form.addFormItem(fechaDesde, "Fecha desde");
    	form.addFormItem(fechaHasta, "Fecha hasta");
    	form.addFormItem(cmbCliente, "Cliente");
    	form.addFormItem(cantidadPasajes, "Cantidad de pasajes");
    	form.addFormItem(precioUnitario, "Precio unitario");
    	form.addFormItem(precioTotal, "Precio Total");
    	form.addFormItem(cmbFormaPago, "Forma de pago");
    	form.addFormItem(pago, "Pago actual");
    	form.addFormItem(saldoPagar, "Saldo");    	
	}

	private void inicializarActions() {
		btnSave= new Button("Guardar");
		btnCancel= new Button("Cancelar");
		btnComprar= new Button("Comprar");
    	actions = new HorizontalLayout();
        actions.add(btnSave, btnCancel, btnComprar);
	}

	private void inicializarMainLayout() {
		mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form,actions);
        mainLayout.setSizeFull();
        
    	this.add(mainLayout);
        this.setWidth("800px");
        this.setHeight("100%");
	}
	
	public void cargarClientes(List <Cliente> clientes) {
		cmbCliente.setItems(clientes);
	}
	
	public void cargarFormasDePago(List <FormaDePago> fdp) {
		cmbFormaPago.setItems(fdp);
	}

	public NumberField getCantidadPasajes() {
		return cantidadPasajes;
	}

	public NumberField getPago() {
		return pago;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}
	
	public Cliente getClienteSeleccionado() {
		return cmbCliente.getValue();
	}
	
	public FormaDePago getFormaPagoSeleccionada() {
		return cmbFormaPago.getValue();
	}
	
	public double getPagoIngresado() {
		return pago.getValue();
	}
	
	public double getPrecioTotal() {
		return precioTotal.getValue();
	}
	public int cantidadPasajesSeleccionados() {
		return cantidadPasajes.getValue().intValue();
	}
	
	public void actualizarPrecioTotal(double nuevoPrecTotal) {
		precioTotal.setReadOnly(false); 
		precioTotal.setValue(nuevoPrecTotal); 
		precioTotal.setReadOnly(true);
	}	
	
	public boolean pagoEsMayorQueTotal() {
		if(pago.getValue() != null) {
			return pago.getValue() > precioTotal.getValue();
		}else {
			return false;
		}		
	}
	
	public void reiniciarPagoIngresado() {
		pago.setReadOnly(false);
		pago.setValue(0.0);
		pago.setReadOnly(true);
	}
	
	public void actualizarSaldo() {
		saldoPagar.setReadOnly(false);
		saldoPagar.setValue(precioTotal.getValue() - pago.getValue());
		saldoPagar.setReadOnly(true);
	}
	
	public void setModoModificacion(double pasajes, Cliente cliente, double pago) {
		cmbCliente.setValue(cliente);
		cmbCliente.setReadOnly(true);
		cantidadPasajes.setValue(pasajes);	
		precioTotal.setValue(pasajes * viaje.getPrecio());
		this.pago.setValue(pago);
		actualizarSaldo();
	}
	
	public void listenerCantPasajes(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
		cantidadPasajes.addValueChangeListener(e);
	}	
}