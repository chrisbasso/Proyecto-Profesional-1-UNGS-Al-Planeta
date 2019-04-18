package com.tp.proyecto1.views.clientes;

import com.tp.proyecto1.model.Cliente;
import com.vaadin.flow.component.button.Button;
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
		this.newClientButton = new Button("Nuevo Cliente", VaadinIcon.PLUS.create());
		nameFilter.setLabel("Nombre");
		idFilter.setLabel("N° Cliente");
		lastNameFilter.setLabel("Apellido");
		dniFilter.setLabel("DNI");
	}

	private void setLayout() {
		HorizontalLayout actions = new HorizontalLayout(idFilter, nameFilter, lastNameFilter, dniFilter, searchButton, newClientButton);
		this.add(actions, grid);
		this.setWidthFull();
		actions.setWidthFull();
		actions.setVerticalComponentAlignment(Alignment.END, searchButton, newClientButton);
	}

	private void setGrid() {
		grid.setHeight("400px");
		grid.setColumns("id", "firstName", "lastName", "dni", "age");
		grid.getColumnByKey("id").setHeader("");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		grid.getColumnByKey("firstName").setHeader("Nombre");
		grid.getColumnByKey("lastName").setHeader("Apellido");
		grid.getColumnByKey("dni").setHeader("DNI");
		grid.getColumnByKey("age").setHeader("Edad");
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
}

