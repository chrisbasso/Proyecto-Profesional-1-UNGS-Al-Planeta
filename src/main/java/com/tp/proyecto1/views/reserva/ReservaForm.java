package com.tp.proyecto1.views.reserva;

import java.lang.reflect.Field;

import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
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
    private Button btnSave;
    private Button btnCancel;

    public ReservaForm(Viaje viaje) {
		 id = new TextField();  
		pais= new TextField();
		ciudad= new TextField();
		codTransporte= new TextField();
		transporte= new TextField();
		fechaDesde= new TextField();
		fechaHasta= new TextField();
		
    	id.setValue(viaje.getId().toString());
    	pais.setValue(viaje.getDestino().getPais());
    	ciudad.setValue(viaje.getDestino().getCiudad());
    	codTransporte.setValue(viaje.getTransporte().getCodTransporte());
    	transporte.setValue(viaje.getTransporte().getTipo().toString());
    	fechaDesde.setValue(viaje.getFechaSalida().toString());
    	fechaHasta.setValue(viaje.getFechaLlegada().toString());    	
    	
    	form = new FormLayout();    	
    	form.addFormItem(pais, "País");
    	form.addFormItem(ciudad, "Ciudad");
    	form.addFormItem(codTransporte, "Cod Transporte");
    	form.addFormItem(transporte, "Transporte");
    	form.addFormItem(fechaDesde, "Fecha desde");
    	form.addFormItem(fechaHasta, "Fecha hasta");

    	btnSave= new Button("Guardar");
		btnCancel= new Button("Cancelar");
    	actions = new HorizontalLayout();
        actions.add(btnSave, btnCancel);
        
    	mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form,actions);
        mainLayout.setSizeFull();
        
    	this.add(mainLayout);
        this.setWidth("800px");
        this.setHeight("100%");
    }
}