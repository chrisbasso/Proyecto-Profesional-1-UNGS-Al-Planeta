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
	private Button addNewBtn;

	public PersonaView() {
		this.grid = new Grid<>(Persona.class);
		this.textName = new TextField();
		this.textLastName = new TextField();
		this.addNewBtn = new Button("Nueva Persona", VaadinIcon.PLUS.create());

		HorizontalLayout actions = new HorizontalLayout(textName,textLastName,addNewBtn);
		this.add(actions, grid);

		grid.setHeight("200px");
		grid.setColumns("id", "firstName", "lastName");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		textName.setPlaceholder("Ingrese Nombre");
		textLastName.setPlaceholder("Ingrese Apellido");

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
}

