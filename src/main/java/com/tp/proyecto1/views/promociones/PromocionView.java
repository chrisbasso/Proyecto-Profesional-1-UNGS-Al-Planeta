package com.tp.proyecto1.views.promociones;

import java.util.ArrayList;
import java.util.Collection;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

//sigue vivo el hl footer
public class PromocionView extends FilterGridLayout<Promocion> implements View {

	private NumberField idFilter;
	private TextField nombreFilter;
	private TextField codigoPromoFilter;
	//private TextField codTransporteFilter;
	private ComboBox<String> tipoPromoFilter;
	private DatePicker fechaDesdeFilter;
	private DatePicker fechaHastaFilter;
	//private Checkbox activosCheck;
	private Button searchButton;
	private Button newPromocionButton;
	/*private Button btnReservar;
	private Button btnComprar;*/

	public PromocionView() {
		super(Promocion.class);
		setComponents();
		setLayout();
		setGrid();
	}

	public void setComponents() {

		this.idFilter = new NumberField("Nº promocion");
		this.idFilter.setWidth("115px");
		this.nombreFilter = new TextField("Nombre promocion");
		nombreFilter.setWidth("120px");
		this.codigoPromoFilter = new TextField("Cod. promocion");
		codigoPromoFilter.setWidth("150px");
		this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
		this.searchButton.setMinWidth("105px");
		this.newPromocionButton = new Button("Nuevo", VaadinIcon.PLUS.create());
		this.newPromocionButton.setMinWidth("105px");
	//	this.activosCheck = new Checkbox("Solo Activos");
		tipoPromoFilter = new ComboBox<>("Tipo de promocion");
        Collection<String> items = new ArrayList<String>();
        items.add("Descuento");
        items.add("Puntos");
        tipoPromoFilter.setItems(items);
		this.fechaDesdeFilter = new DatePicker("Fecha Desde");
		this.fechaDesdeFilter.setWidth("137px");
		this.fechaHastaFilter = new DatePicker("Fecha Hasta");
		this.fechaHastaFilter.setWidth("137px");
		/*this.btnComprar = new Button("Vender");
		this.btnReservar = new Button("Reservar");*/
	//	this.activosCheck.setValue(true);
	//	this.activosCheck.setMinWidth("135px");
	}

	public void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		//this.hlFooter.add(btnReservar, btnComprar);
		hlSpace.setWidthFull();
		this.hlActions.add(idFilter, nombreFilter, codigoPromoFilter, /*codTransporteFilter, */tipoPromoFilter,fechaDesdeFilter,fechaHastaFilter/*,activosCheck(*/,hlSpace, searchButton, newPromocionButton);
	}

	public void setGrid() {
		grid.setColumns("id", "nombrePromocion","descripcion","cantidadPasajes","tipoPromocion","doubleValue", "fechaVencimiento",
				"codigoPromocion");
		grid.getColumnByKey("id").setHeader("Nº");
		grid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);
		grid.getColumnByKey("doubleValue").setHeader("Bonificador");
		grid.getColumnByKey("cantidadPasajes").setHeader("Cantidad de pasajes");
	}

	public Grid<Promocion> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Promocion> grid) {
		this.grid = grid;
	}

	public TextField getNombreFilter() {
		return nombreFilter;
	}

	public void setNombreFilter(TextField nombreFilter) {
		this.nombreFilter = nombreFilter;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public TextField getCodigoPromoFilter() {
		return codigoPromoFilter;
	}

	public void setCodigoPromoFilter(TextField codigoPromoFilter) {
		this.codigoPromoFilter = codigoPromoFilter;
	}
	
	/*

	public TextField getCodTransporteFilter() {
		return codTransporteFilter;
	}

	public void setCodTransporteFilter(TextField codTransporteFilter) {
		this.codTransporteFilter = codTransporteFilter;
	}*/

	public NumberField getIdFilter() {
		return idFilter;
	}

	public void setIdFilter(NumberField idFilter) {
		this.idFilter = idFilter;
	}

	public Button getNewPromocionButton() {
		return newPromocionButton;
	}

	public void setNewPromocionButton(Button newPromocionButton) {
		this.newPromocionButton = newPromocionButton;
	}

	/*public Checkbox getActivosCheck() {
		return activosCheck;
	}

	public void setActivosCheck(Checkbox activosCheck) {
		this.activosCheck = activosCheck;
	}*/

	public ComboBox<String> getTipoPromoFilter() {
		return tipoPromoFilter;
	}

	public void setTipoPromoFilter(ComboBox<String> tipoPromoFilter) {
		this.tipoPromoFilter = tipoPromoFilter;
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

	/*public Button getBtnReservar() {
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
	}*/
}
