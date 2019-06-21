package com.tp.proyecto1.views.eventos;

import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.utils.ConfigDatePicker;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;


public class EventosView extends FilterGridLayout<Evento> implements View {

	private NumberField idFilter = new NumberField("Nº Evento");
	private NumberField idClienteFilter = new NumberField("Nº Cliente/Interesado");
	private TextField nombreFilter = new TextField("Nombre");
	private TextField apellidoFilter = new TextField("Apellido");
	private DatePicker fechaFilter = new DatePicker("Vence antes del");
	private Button searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
	private Button newConsultaButton = new Button("Nuevo");
	private Button helpButton = new Button(VaadinIcon.QUESTION_CIRCLE.create());
	private Checkbox checkAbierto = new Checkbox("Solo Abiertos");

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
		checkAbierto.setMinWidth("150px");
		idFilter.setWidth("100px");
		idClienteFilter.setMinWidth("150px");	
		fechaFilter.setWidth("150px");
		nombreFilter.setWidth("140px");
		apellidoFilter.setWidth("140px");
		checkAbierto.setWidth("160px");
		ConfigDatePicker configDatePicker = new ConfigDatePicker();
		configDatePicker.setearLenguajeEspañol(fechaFilter);
	}

	@Override
	public void setLayout() {
		this.hlActions.add(idFilter,idClienteFilter,nombreFilter,apellidoFilter,fechaFilter,checkAbierto,searchButton,newConsultaButton,helpButton);
	}

	@Override
	public void setGrid() {
		grid.setColumns("id", "persona.nombre", "persona.apellido", "fecha","fechaVencimiento","horaVencimiento","creadorEvento.user","usuarioAsignado.user", "cerradorEvento.user", "prioridad");
		grid.getColumnByKey("id").setHeader("Nº Evento");
		grid.getColumnByKey("creadorEvento.user").setHeader("Creador");
		grid.getColumnByKey("usuarioAsignado.user").setHeader("Asignado a");
		grid.getColumnByKey("cerradorEvento.user").setHeader("Cerrado por:");
		grid.getColumnByKey("fecha").setWidth("125px").setFlexGrow(0);
		grid.getColumnByKey("fechaVencimiento").setHeader("Vence el").setWidth("125px").setFlexGrow(0);
		grid.getColumnByKey("horaVencimiento").setHeader("A las").setWidth("120px").setFlexGrow(0);
	}

	public Grid<Evento> getGrid(){
		return this.grid;
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

	public Checkbox getCheckAbierto() {
		return checkAbierto;
	}

	public void setCheckAbierto(Checkbox checkAbierto) {
		this.checkAbierto = checkAbierto;
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

	public Button getHelpButton()
	{
		return helpButton;
	}

	public void setHelpButton(Button helpButton)
	{
		this.helpButton = helpButton;
	}

}
