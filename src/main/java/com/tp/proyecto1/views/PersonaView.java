package com.tp.proyecto1.views;

import com.tp.proyecto1.model.Persona;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class PersonaView extends VerticalLayout {

	private Grid<Persona> grid;
	private TextField textName;
	private TextField textLastName;
	private TextField textDni;
	private TextField textAge;
	private Button addNewBtn;

	public PersonaView() {
		setComponents();
		setLayout();
		setGrid();
	}

	private void setComponents() {
		this.grid = new Grid<>(Persona.class);
		this.textName = new TextField();
		this.textLastName = new TextField();
		this.textDni = new TextField();
		this.textAge = new TextField();
		this.addNewBtn = new Button("Nueva Persona", VaadinIcon.PLUS.create());
		textName.setPlaceholder("Ingrese Nombre");
		textLastName.setPlaceholder("Ingrese Apellido");
		textDni.setPlaceholder("Ingrese DNI");
		textAge.setPlaceholder("Ingrese Edad");
	}

	private void setLayout() {
		HorizontalLayout actions = new HorizontalLayout(textName,textLastName,textAge,textDni,addNewBtn);
		this.add(actions, grid);
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

	public Grid<Persona> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Persona> grid) {
		this.grid = grid;
	}

	public TextField getTextName() {
		return textName;
	}

	public void setTextName(TextField textName) {
		this.textName = textName;
	}

	public Button getAddNewBtn() {
		return addNewBtn;
	}

	public void setAddNewBtn(Button addNewBtn) {
		this.addNewBtn = addNewBtn;
	}

	public TextField getTextLastName() {
		return textLastName;
	}

	public void setTextLastName(TextField textLastName) {
		this.textLastName = textLastName;
	}

	public TextField getTextDni() {
		return textDni;
	}

	public void setTextDni(TextField textDni) {
		this.textDni = textDni;
	}

	public TextField getTextAge() {
		return textAge;
	}

	public void setTextAge(TextField textAge) {
		this.textAge = textAge;
	}
}

