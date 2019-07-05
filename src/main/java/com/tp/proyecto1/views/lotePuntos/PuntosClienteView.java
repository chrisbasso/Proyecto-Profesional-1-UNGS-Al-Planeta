package com.tp.proyecto1.views.lotePuntos;

import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PuntosClienteView extends FilterGridLayout<LotePunto> implements View
{
	
	/*private NumberField idFilter;
	private TextField nombreFilter;
	private TextField codigoPromoFilter;
	private ComboBox<String> tipoPromoFilter;
	private DatePicker vencimientoMenorAFilter;
	private Button searchButton;
	private Button newPromocionButton;
	private Checkbox activosCheck;*/

	public PuntosClienteView() {
		super(LotePunto.class);
		setComponents();
		setLayout();
		setGrid();
	}

	public void setComponents() {

		/*this.idFilter = new NumberField("Nº promocion");
		this.idFilter.setWidth("115px");
		this.nombreFilter = new TextField("Nombre promocion");
		nombreFilter.setWidth("120px");
		this.codigoPromoFilter = new TextField("Cod. promocion");
		codigoPromoFilter.setWidth("150px");
		this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
		this.searchButton.setMinWidth("105px");
		this.newPromocionButton = new Button("Nuevo", VaadinIcon.PLUS.create());
		this.newPromocionButton.setMinWidth("105px");
		tipoPromoFilter = new ComboBox<>("Tipo de promocion");
        Collection<String> items = new ArrayList<String>();
        items.add("Descuento");
        items.add("Puntos");
        tipoPromoFilter.setItems(items);
		this.vencimientoMenorAFilter = new DatePicker("Vencimiento menor a");
		this.vencimientoMenorAFilter.setWidth("170px");
		ConfigDatePicker configDatePicker = new ConfigDatePicker();
		configDatePicker.setearLenguajeEspañol(vencimientoMenorAFilter);
		this.activosCheck = new Checkbox("Solo Activas");
		this.activosCheck.setValue(true);
		this.activosCheck.setMinWidth("135px");*/
	}

	public void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		//this.hlActions.add(idFilter, nombreFilter, codigoPromoFilter,tipoPromoFilter,vencimientoMenorAFilter,activosCheck,hlSpace, searchButton, newPromocionButton);
	}

	public void setGrid() {
		grid.setColumns("id", "fechaAlta", "fechaVencimiento", "cantidadPuntos","cantidadRestante");
		grid.getColumnByKey("id").setHeader("Nº Puntos");
		grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
		grid.getColumnByKey("fechaAlta").setHeader("Fecha de obtención");
		grid.getColumnByKey("fechaVencimiento").setHeader("Fecha de vencimiento");
		grid.getColumnByKey("cantidadPuntos").setHeader("Cantidad obtenida");
		grid.getColumnByKey("cantidadRestante").setHeader("Cantidad restante");
	}

	public Grid<LotePunto> getGrid() {
		return grid;
	}

	public void setGrid(Grid<LotePunto> grid) {
		this.grid = grid;
	}
/*
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

	public ComboBox<String> getTipoPromoFilter() {
		return tipoPromoFilter;
	}

	public void setTipoPromoFilter(ComboBox<String> tipoPromoFilter) {
		this.tipoPromoFilter = tipoPromoFilter;
	}

	public DatePicker getVencimientoMenorAFilter() {
		return vencimientoMenorAFilter;
	}

	public void setVencimientoMenorAFilter(DatePicker vencimientoMenorAFilter) {
		this.vencimientoMenorAFilter = vencimientoMenorAFilter;
	}

	public Checkbox getActivosCheck()
	{
		return activosCheck;
	}

	public void setActivosCheck(Checkbox activosCheck)
	{
		this.activosCheck = activosCheck;
	}*/

}
