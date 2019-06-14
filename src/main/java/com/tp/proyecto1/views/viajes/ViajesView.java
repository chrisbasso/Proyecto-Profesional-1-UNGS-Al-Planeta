package com.tp.proyecto1.views.viajes;

import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ViajesView extends FilterGridLayout<Viaje> implements View {

	private NumberField idFilter;
	private ComboBox<Provincia> provinciaFilter;
	private ComboBox<Ciudad> ciudadFilter;
	private TextField codTransporteFilter;
	private ComboBox<TipoTransporte> transporteFilter;
	private DatePicker fechaDesdeFilter;
	private DatePicker fechaHastaFilter;
	private Checkbox activosCheck;
	private Button nuevoDestino;
	private Button searchButton;
	private Button newViajeButton;
	private Button btnReservar;
	private Button btnComprar;

	public ViajesView() {
		super(Viaje.class);
		setComponents();
		setLayout();
		setGrid();
	}

	public void setComponents() {

		this.idFilter = new NumberField("Nº Viaje");
		this.idFilter.setWidth("70px");
		this.provinciaFilter = new ComboBox<>("Provincia");
		this.provinciaFilter.setItemLabelGenerator(Provincia::getNombre);
		provinciaFilter.setWidth("150px");
		this.ciudadFilter = new ComboBox<>("Ciudad");
		this.ciudadFilter.setItemLabelGenerator(Ciudad::getNombre);
		ciudadFilter.setWidth("200px");
		this.codTransporteFilter = new TextField("Cod. Transporte");
		codTransporteFilter.setWidth("105px");
		this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
		this.searchButton.setMinWidth("105px");
		this.newViajeButton = new Button("Nuevo", VaadinIcon.PLUS.create());
		this.newViajeButton.setMinWidth("105px");
		this.nuevoDestino = new Button("Destino",VaadinIcon.PLUS.create());
		this.nuevoDestino.setMinWidth("110px");
		this.activosCheck = new Checkbox("Solo Activos");
		this.transporteFilter = new ComboBox<>("Transporte");
		this.transporteFilter.setWidth("130px");
		this.transporteFilter.setItemLabelGenerator(TipoTransporte::getDescripcion);
		this.fechaDesdeFilter = new DatePicker("Fecha Desde");
		this.fechaDesdeFilter.setWidth("137px");
		this.fechaHastaFilter = new DatePicker("Fecha Hasta");
		this.fechaHastaFilter.setWidth("137px");
		this.btnComprar = new Button("Vender");
		this.btnReservar = new Button("Reservar");
		this.activosCheck.setValue(true);
		this.activosCheck.setMinWidth("135px");
	}

	public void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		this.hlFooter.add(btnReservar, btnComprar);
		hlSpace.setWidthFull();
		this.hlActions.add(idFilter, provinciaFilter, ciudadFilter, codTransporteFilter, transporteFilter,fechaDesdeFilter,fechaHastaFilter,activosCheck,hlSpace, searchButton,nuevoDestino, newViajeButton);
	}

	public void setGrid() {
		grid.setColumns("id", "destino.nombre","destino.provincia.nombre","destino.provincia.pais.nombre", "transporte.codTransporte",
				"transporte.tipo.descripcion", "transporte.capacidadRestante", "transporte.clase",
				"fechaSalida", "horaSalida", "precio");
		grid.getColumnByKey("id").setHeader("Nº");
		grid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);
		grid.getColumnByKey("transporte.capacidadRestante").setHeader("Disponibles");
		grid.getColumnByKey("transporte.tipo.descripcion").setHeader("Tipo Transporte");
		grid.getColumnByKey("destino.provincia.nombre").setHeader("Provincia");
		grid.getColumnByKey("destino.provincia.pais.nombre").setHeader("País");
		grid.getColumnByKey("destino.nombre").setHeader("Ciudad");

	}

	public Grid<Viaje> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Viaje> grid) {
		this.grid = grid;
	}

	public ComboBox<Provincia> getProvinciaFilter() {
		return provinciaFilter;
	}

	public void setProvinciaFilter(ComboBox<Provincia> provinciaFilter) {
		this.provinciaFilter = provinciaFilter;
	}

	public ComboBox<Ciudad> getCiudadFilter() {
		return ciudadFilter;
	}

	public void setCiudadFilter(ComboBox<Ciudad> ciudadFilter) {
		this.ciudadFilter = ciudadFilter;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public Button getNuevoDestino() {
		return nuevoDestino;
	}

	public void setNuevoDestino(Button nuevoDestino) {
		this.nuevoDestino = nuevoDestino;
	}

	public TextField getCodTransporteFilter() {
		return codTransporteFilter;
	}

	public void setCodTransporteFilter(TextField codTransporteFilter) {
		this.codTransporteFilter = codTransporteFilter;
	}

	public NumberField getIdFilter() {
		return idFilter;
	}

	public void setIdFilter(NumberField idFilter) {
		this.idFilter = idFilter;
	}

	public Button getNewViajeButton() {
		return newViajeButton;
	}

	public void setNewViajeButton(Button newViajeButton) {
		this.newViajeButton = newViajeButton;
	}

	public Checkbox getActivosCheck() {
		return activosCheck;
	}

	public void setActivosCheck(Checkbox activosCheck) {
		this.activosCheck = activosCheck;
	}

	public ComboBox<TipoTransporte> getTransporteFilter() {
		return transporteFilter;
	}

	public void setTransporteFilter(ComboBox<TipoTransporte> transporteFilter) {
		this.transporteFilter = transporteFilter;
	}

	public DatePicker getFechaDesdeFilter() {
		return fechaDesdeFilter;
	}

	public void setFechaDesdeFilter(DatePicker fechaDesdeFilter) {
		this.fechaDesdeFilter = fechaDesdeFilter;
	}

	public DatePicker getFechaHastaFilter() {
		return fechaHastaFilter;
	}

	public void setFechaHastaFilter(DatePicker fechaHastaFilter) {
		this.fechaHastaFilter = fechaHastaFilter;
	}

	public Button getBtnReservar() {
		return btnReservar;
	}

	public void setBtnReservar(Button btnReservar) {
		this.btnReservar = btnReservar;
	}

	public Button getBtnComprar() {
		return btnComprar;
	}

	public void setBtnComprar(Button btnComprar) {
		this.btnComprar = btnComprar;
	}
}
