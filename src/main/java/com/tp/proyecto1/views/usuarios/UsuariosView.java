package com.tp.proyecto1.views.usuarios;

import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class UsuariosView extends FilterGridLayout<User> implements View {

	private NumberField idFilter = new NumberField("Nº Usuario");
	private TextField nombreUsuarioFilter = new TextField("Nombre Usuario");
	private Button searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
	private Button newUserButton = new Button("Nuevo Usuario", VaadinIcon.PLUS.create());
	private Checkbox checkActivo = new Checkbox("Solo Activos");

	public UsuariosView() {
		super(User.class);
		setComponents();
		setLayout();
		setGrid();
	}

	@Override
	public void setComponents() {
		searchButton.setMinWidth("150px");
		newUserButton.setMinWidth("150px");
		checkActivo.setMinWidth("150px");
	}

	@Override
	public void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		this.hlActions.add(idFilter, nombreUsuarioFilter,checkActivo,hlSpace,searchButton,newUserButton);
	}

	@Override
	public void setGrid() {

		grid.setColumns("id", "user", "password", "rol.name", "sucursal.descripcion", "enabled");
		grid.getColumnByKey("user").setHeader("Nombre Usuario");
		grid.getColumnByKey("password").setHeader("Contraseña");
		grid.getColumnByKey("rol.name").setHeader("Rol");
		grid.getColumnByKey("sucursal.descripcion").setHeader("Sucursal");
		grid.getColumnByKey("enabled").setHeader("Activo");
		grid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);

	}

	public Grid<User> getGrid(){
		return this.grid;
	}

	public NumberField getIdFilter() {
		return idFilter;
	}

	public void setIdFilter(NumberField idFilter) {
		this.idFilter = idFilter;
	}

	public TextField getNombreUsuarioFilter() {
		return nombreUsuarioFilter;
	}

	public void setNombreUsuarioFilter(TextField nombreUsuarioFilter) {
		this.nombreUsuarioFilter = nombreUsuarioFilter;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public Button getNewUserButton() {
		return newUserButton;
	}

	public void setNewUserButton(Button newUserButton) {
		this.newUserButton = newUserButton;
	}

	public Checkbox getCheckActivo() {
		return checkActivo;
	}

	public void setCheckActivo(Checkbox checkActivo) {
		this.checkActivo = checkActivo;
	}
}
