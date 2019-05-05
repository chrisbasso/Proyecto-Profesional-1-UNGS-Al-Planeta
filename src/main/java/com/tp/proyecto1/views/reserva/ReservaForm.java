package com.tp.proyecto1.views.reserva;

import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ReservaForm extends Dialog{

    private VerticalLayout mainLayout;
    private FormLayout form;
    private HorizontalLayout actions;
    private TextField id;
	private TextField pais;
	private TextField ciudad;
	private TextField codTransporte;
	private TextField transporte;
	private TextField fechaDesde;
	private TextField fechaHasta;
	private TextField cliente;
	private Button btnCliente;
	private Button btnSave;
    private Button btnCancel;

    public ReservaForm(Viaje viaje) {
		iniciliazarCampos();
		cargarValores(viaje);
    	setReadOnly(); 
    	inicializarForm();
    	inicializarActions();        
    	inicializarMainLayout();
    }

	private void iniciliazarCampos() {
		id = new TextField();  
		pais= new TextField();
		ciudad= new TextField();
		codTransporte= new TextField();
		transporte= new TextField();
		fechaDesde= new TextField();
		fechaHasta= new TextField();
		cliente= new TextField();
		btnCliente = new Button("Buscar", VaadinIcon.SEARCH.create());
		btnCliente.setMaxWidth("20px");
	}

	private void cargarValores(Viaje viaje) {
		id.setValue(viaje.getId().toString());
    	pais.setValue(viaje.getDestino().getPais());
    	ciudad.setValue(viaje.getDestino().getCiudad());
    	codTransporte.setValue(viaje.getTransporte().getCodTransporte());
    	transporte.setValue(viaje.getTransporte().getTipo().toString());
    	fechaDesde.setValue(viaje.getFechaSalida().toString());
    	fechaHasta.setValue(viaje.getFechaLlegada().toString());
	}
    
    private void setReadOnly() {
    	id.setReadOnly(true);  
		pais.setReadOnly(true);  
		ciudad.setReadOnly(true);  
		codTransporte.setReadOnly(true);  
		transporte.setReadOnly(true);  
		fechaDesde.setReadOnly(true);  
		fechaHasta.setReadOnly(true);
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
    	form.addFormItem(btnCliente,"");
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
	
	public Button getBtnCliente() {
		return btnCliente;
	}

	public String getIdCliente() {
		return cliente.getValue();
	}
}