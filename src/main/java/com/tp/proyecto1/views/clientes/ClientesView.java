package com.tp.proyecto1.views.clientes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;


public class ClientesView extends FilterGridLayout<Cliente> {

	private NumberField idFilter;
	private TextField nameFilter;
	private TextField lastNameFilter;
	private NumberField dniFilter;
	private Checkbox activosCheck;
	private Button searchButton;
	private Button newClientButton;
	private Button btnHistorialEventos;

	public ClientesView() {
		super(Cliente.class);
		setComponents();
		setLayout();
		setGrid();
	}

	private void setComponents() {
		this.btnHistorialEventos = new Button("Historico Eventos");
		this.idFilter = new NumberField();
		this.nameFilter = new TextField();
		this.lastNameFilter = new TextField();
		this.dniFilter = new NumberField();
		this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
		this.searchButton.setMinWidth("130px");
		this.newClientButton = new Button("Nuevo", VaadinIcon.PLUS.create());
		this.newClientButton.setMinWidth("130px");
		this.activosCheck = new Checkbox("Solo Activos");
		this.activosCheck.setMinWidth("140px");
		activosCheck.setValue(true);
		nameFilter.setLabel("Nombre");
		idFilter.setLabel("N° Cliente");
		lastNameFilter.setLabel("Apellido");
		dniFilter.setLabel("DNI");
	}

	private void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		this.hlFooter.add(btnHistorialEventos);
		this.hlActions.add(idFilter, nameFilter, lastNameFilter, dniFilter,activosCheck,hlSpace, searchButton, newClientButton);
	}

	private void setGrid() {
		grid.setColumns("id", "nombre", "apellido", "dni", "fechaAlta", "fechaBaja");
		grid.getColumnByKey("id").setHeader("Nº");
		grid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);
		grid.getColumnByKey("nombre").setHeader("Nombre");
		grid.getColumnByKey("apellido").setHeader("Apellido");
		grid.getColumnByKey("dni").setHeader("DNI");
		grid.getColumnByKey("fechaAlta").setHeader("Fecha Alta");
		grid.getColumnByKey("fechaBaja").setHeader("Fecha Baja");
		grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);

	}

	public Grid<Cliente> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Cliente> grid) {
		this.grid = grid;
	}

	public TextField getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(TextField nameFilter) {
		this.nameFilter = nameFilter;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public TextField getLastNameFilter() {
		return lastNameFilter;
	}

	public void setLastNameFilter(TextField lastNameFilter) {
		this.lastNameFilter = lastNameFilter;
	}

	public NumberField getDniFilter() {
		return dniFilter;
	}

	public void setDniFilter(NumberField dniFilter) {
		this.dniFilter = dniFilter;
	}

	public NumberField getIdFilter() {
		return idFilter;
	}

	public void setIdFilter(NumberField idFilter) {
		this.idFilter = idFilter;
	}

	public Button getNewClientButton() {
		return newClientButton;
	}

	public void setNewClientButton(Button newClientButton) {
		this.newClientButton = newClientButton;
	}

	public Checkbox getActivosCheck() {
		return activosCheck;
	}

	public void setActivosCheck(Checkbox activosCheck) {
		this.activosCheck = activosCheck;
	}

	public Button getBtnHistorialEventos() {
		return btnHistorialEventos;
	}

	public void setBtnHistorialEventos(Button btnHistorialEventos) {
		this.btnHistorialEventos = btnHistorialEventos;
	}
}

