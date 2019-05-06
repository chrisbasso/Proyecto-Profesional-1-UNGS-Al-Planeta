package com.tp.proyecto1.views.reserva;

import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
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
	private TextField cliente;
	private NumberField cantidadPasajes;
	private Button btnBuscarCliente;
	private Button btnSave;
    private Button btnCancel;

    public ReservaForm(Viaje viaje) {
    	if(viaje != null) {
    		this.viaje = viaje;
    		iniciliazarCampos();
    		cargarValores(viaje);
        	setReadOnly(); 
        	inicializarForm();
        	inicializarActions();        
        	inicializarMainLayout();	
    	}else {
    		// Prevenir que invoquen el form sin un viaje
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
		cliente= new TextField();
		cliente.setAutoselect(true);
		
		cantidadPasajes= new NumberField();		
		cantidadPasajes.setValue(1d);
		cantidadPasajes.setMin(1);
		cantidadPasajes.setMax(Double.parseDouble(viaje.getTransporte().getCapacidad()));
		cantidadPasajes.setHasControls(true);		
		
		precioTotal= new NumberField();
		btnBuscarCliente = new Button("Buscar", VaadinIcon.SEARCH.create());
		btnBuscarCliente.setMaxWidth("20px");
	}

	private void cargarValores(Viaje viaje) {
		id.setValue(viaje.getId().toString());
    	pais.setValue(viaje.getDestino().getPais());
    	ciudad.setValue(viaje.getDestino().getCiudad());
    	codTransporte.setValue(viaje.getTransporte().getCodTransporte());
    	transporte.setValue(viaje.getTransporte().getTipo().toString());
    	fechaDesde.setValue(viaje.getFechaSalida().toString());
    	fechaHasta.setValue(viaje.getFechaLlegada().toString());
    	precioUnitario.setValue(viaje.getPrecio());
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
    }

	private void inicializarForm() {
		form = new FormLayout();    	
    	form.addFormItem(pais, "País");
    	form.addFormItem(ciudad, "Ciudad");
    	form.addFormItem(codTransporte, "Cod Transporte");
    	form.addFormItem(transporte, "Transporte");
    	form.addFormItem(fechaDesde, "Fecha desde");
    	form.addFormItem(fechaHasta, "Fecha hasta");
    	form.addFormItem(cliente, "Cliente");
    	form.addFormItem(cantidadPasajes, "Cantidad de pasajes");
    	form.addFormItem(precioUnitario, "Precio unitario");
    	form.addFormItem(precioTotal, "Precio Total");
    	form.addFormItem(btnBuscarCliente,"");
	}

	private void inicializarActions() {
		btnSave= new Button("Guardar");
		btnCancel= new Button("Cancelar");
    	actions = new HorizontalLayout();
        actions.add(btnSave, btnCancel);
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

    public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}
	
	public Button getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public String getIdCliente() {
		return cliente.getValue();
	}
	
	public NumberField getCantidadPasajes() {
		return cantidadPasajes;
	}
	
	public NumberField getPrecioTotal() {
		return precioTotal;
	}
}