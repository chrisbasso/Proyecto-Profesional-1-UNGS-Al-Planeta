package com.tp.proyecto1.views.reserva;

import java.util.List;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;

public class AgregarPagoForm extends Dialog{

    private VerticalLayout mainLayout;
    private FormLayout form;
    private HorizontalLayout actions;
    
	private ComboBox<FormaDePago> cmbFormaPago;
	private NumberField pago;
	private double importeMaximo;	
	private Button btnSave;
    private Button btnCancel;
    
    public AgregarPagoForm(double importeMaximo) {
    	this.importeMaximo = importeMaximo;
		iniciliazarCampos();
    	inicializarForm();
    	inicializarActions();        
    	inicializarMainLayout();    			
    }

	private void iniciliazarCampos() {		
		cmbFormaPago = new ComboBox<FormaDePago>();
		pago = new NumberField();		
		pago.setPrefixComponent(new Span("$"));
		pago.setMin(0.0);
		pago.setMax(importeMaximo);
	}
	
	private void inicializarForm() {
		form = new FormLayout();    	
    	form.addFormItem(pago, "Importe");
    	form.addFormItem(cmbFormaPago, "Forma de Pago");    	
	}

	private void inicializarActions() {
		btnSave= new Button("Guardar");
		btnSave.setEnabled(false);
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
        this.open();
	}
	
	public void cargarFormasDePago(List <FormaDePago> fdp) {
		cmbFormaPago.setItems(fdp);
	}

	public FormaDePago getFormaPagoSeleccionada() {
		return cmbFormaPago.getValue();
	}
	
	public double getPagoIngresado() {
		return pago.getValue();
	}
	
	public void setListenerImporte(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
		pago.addValueChangeListener(e);
	}
	
	public void setListenerFDP(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<FormaDePago>, FormaDePago>> e) {
		cmbFormaPago.addValueChangeListener(e);
	}
	
	public void activarBtnGuardar() {
		btnSave.setEnabled(true);		
	}
	
	public void desactivarBtnGuardar() {
		btnSave.setEnabled(false);
	}
	
	public boolean opcionesSeleccionadas() {
		return pago.getValue()>0 && (cmbFormaPago.getValue()!= null);	
	}
	
	public void setListenerBtnGuardar(ComponentEventListener<ClickEvent<Button>> e){
		btnSave.addClickListener(e);
	}
	
	public void setListenerBtnCancelar(ComponentEventListener<ClickEvent<Button>> e){
		btnCancel.addClickListener(e);
	}
}