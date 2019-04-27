package com.tp.proyecto1.views.clientes;

import com.tp.proyecto1.model.Cliente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;


public class ClientesView extends VerticalLayout {

	private Grid<Cliente> grid;
	private NumberField idFilter;
	private TextField nameFilter;
	private TextField lastNameFilter;
	private NumberField dniFilter;
	private Checkbox activosCheck;
	private Button searchButton;
	private Button newClientButton;

	public ClientesView() {
		setComponents();
		setLayout();
		setGrid();
	}

	private void setComponents() {
		this.grid = new Grid<>(Cliente.class);
		this.idFilter = new NumberField();
		this.nameFilter = new TextField();
		this.lastNameFilter = new TextField();
		this.dniFilter = new NumberField();
		this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
		this.searchButton.setMinWidth("130px");
		this.newClientButton = new Button("Nuevo", VaadinIcon.PLUS.create());
		this.newClientButton.setMinWidth("130px");
		this.activosCheck = new Checkbox("Activos");
		activosCheck.setValue(true);
		nameFilter.setLabel("Nombre");
		idFilter.setLabel("N° Cliente");
		lastNameFilter.setLabel("Apellido");
		dniFilter.setLabel("DNI");
	}

	private void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		HorizontalLayout actions = new HorizontalLayout(idFilter, nameFilter, lastNameFilter, dniFilter,activosCheck,hlSpace, searchButton, newClientButton);
		this.add(actions, grid);
		this.setSizeFull();
		actions.setWidthFull();
		actions.setVerticalComponentAlignment(Alignment.END, searchButton, newClientButton, activosCheck);
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
}

