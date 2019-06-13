package com.tp.proyecto1.views.eventos;

import com.tp.proyecto1.model.eventos.Evento;
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
	private DatePicker fechaFilter = new DatePicker("Fecha");
	private Button searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
	private Button newConsultaButton = new Button("Nueva Evento");
	private Checkbox checkAbierto = new Checkbox("Solo Abiertos");
	private Button btnAgregarRecordatorio = new Button("Agregar recordatorio");

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
		idClienteFilter.setMinWidth("150px");	
	}

	@Override
	public void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		this.hlFooter.add(btnAgregarRecordatorio);
		this.hlActions.add(idFilter,idClienteFilter,nombreFilter,apellidoFilter,fechaFilter,checkAbierto,hlSpace,searchButton,newConsultaButton);
	}

	@Override
	public void setGrid() {
		grid.setColumns("id", "persona.id", "persona.nombre", "persona.apellido", "fecha", "hora","creadorEvento.user","usuarioAsignado.user", "cerradorEvento.user", "prioridad");
		grid.getColumnByKey("id").setHeader("Nº Evento");
		grid.getColumnByKey("persona.id").setHeader("Nº Cliente/Interesado");
		grid.getColumnByKey("creadorEvento.user").setHeader("Creador por:");
		grid.getColumnByKey("usuarioAsignado.user").setHeader("Asignado a:");
		grid.getColumnByKey("cerradorEvento.user").setHeader("Cerrado por:");
		grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
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

	public Button getBtnAgregarRecordatorio()
	{
		return btnAgregarRecordatorio;
	}

	public void setBtnAgregarRecordatorio(Button btnAgregarRecordatorio)
	{
		this.btnAgregarRecordatorio = btnAgregarRecordatorio;
	}

}
