package com.tp.proyecto1.views.eventos;

import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class EventosView extends FilterGridLayout<Evento> implements View {

	private NumberField idFilter = new NumberField("Nº Evento");
	private NumberField idClienteFilter = new NumberField("Nº Cliente");
	private TextField nombreFilter = new TextField("Nombre");
	private TextField apellidoFilter = new TextField("Apellido");
	private DatePicker fechaFilter = new DatePicker("Fecha");
	private Button searchButton = new Button("Buscar");
	private Button newConsultaButton = new Button("Nueva Consulta");
	private Button newReclamoButton = new Button("Nuevo Reclamo");

	public EventosView() {
		super(Evento.class);
		setComponents();
		setLayout();
		setGrid();
	}

	@Override
	public void setComponents() {
		searchButton.setMinWidth("150px");
		newConsultaButton.setMinWidth("150px");
		newReclamoButton.setMinWidth("150px");
	}

	@Override
	public void setLayout() {

		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		this.hlActions.add(idFilter,idClienteFilter,nombreFilter,apellidoFilter,fechaFilter,hlSpace,searchButton,newConsultaButton,newReclamoButton);
	}

	@Override
	public void setGrid() {

		grid.setColumns("id", "cliente.id", "cliente.nombre", "cliente.apellido", "fecha", "hora");
		grid.getColumnByKey("id").setHeader("Nº Reclamo");
		grid.getColumnByKey("cliente.id").setHeader("Nº Cliente");
		grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
	}

	public NumberField getIdFilter() {
		return idFilter;
	}

	public void setIdFilter(NumberField idFilter) {
		this.idFilter = idFilter;
	}

	public NumberField getIdClienteFilter() {
		return idClienteFilter;
	}

	public void setIdClienteFilter(NumberField idClienteFilter) {
		this.idClienteFilter = idClienteFilter;
	}

	public TextField getNombreFilter() {
		return nombreFilter;
	}

	public void setNombreFilter(TextField nombreFilter) {
		this.nombreFilter = nombreFilter;
	}

	public TextField getApellidoFilter() {
		return apellidoFilter;
	}

	public void setApellidoFilter(TextField apellidoFilter) {
		this.apellidoFilter = apellidoFilter;
	}

	public DatePicker getFechaFilter() {
		return fechaFilter;
	}

	public void setFechaFilter(DatePicker fechaFilter) {
		this.fechaFilter = fechaFilter;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public Button getNewConsultaButton() {
		return newConsultaButton;
	}

	public void setNewConsultaButton(Button newConsultaButton) {
		this.newConsultaButton = newConsultaButton;
	}

	public Button getNewReclamoButton() {
		return newReclamoButton;
	}

	public void setNewReclamoButton(Button newReclamoButton) {
		this.newReclamoButton = newReclamoButton;
	}
}
