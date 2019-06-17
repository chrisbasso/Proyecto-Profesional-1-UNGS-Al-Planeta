package com.tp.proyecto1.utils;

import com.tp.proyecto1.model.pasajes.Pasajero;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;

import java.util.*;

public class PasajerosGridComponent extends VerticalLayout {

	private Button newPasajero = new Button("Agregar Pasajero (*)");
	private Button removeLastButton = new Button("Eliminar Último");
	private Grid<Pasajero> grid = new Grid<>();
	private List<Pasajero> pasajerosList = new ArrayList<>();
	private Editor<Pasajero> editor;
	private Collection<Button> editButtons;
	private Boolean editarInvisible;
	
	public PasajerosGridComponent() {
		this.editarInvisible = Boolean.TRUE;
		setLayout();
		setGrid();
		setListener();

	}

	private void setListener() {
		newPasajero.addClickListener(e->agregarPasajero());
		removeLastButton.addClickListener(e->eliminarUltimo());
	}

	private void eliminarUltimo() {
		if(pasajerosList.size()!=0){
			pasajerosList.remove(pasajerosList.size() - 1);
			grid.getDataProvider().refreshAll();
		}
	}

	private void agregarPasajero() {

		if(!editor.isOpen()){
			Pasajero pasajero = new Pasajero();
			pasajerosList.add(pasajero);
			grid.getDataProvider().refreshAll();
			editor.editItem(pasajero);
		}
	}


	private void setLayout() {
		this.setSizeFull();
		this.add(new HorizontalLayout(newPasajero, removeLastButton), grid);
		newPasajero.addThemeVariants(ButtonVariant.LUMO_SMALL);
		removeLastButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
	}

	public void setGrid() {
		grid.setWidthFull();

		grid.setHeight("170px");

		grid.setItems(pasajerosList);

		grid.removeAllColumns();

		Grid.Column<Pasajero> columnNombre = grid.addColumn(Pasajero::getNombreCompleto)
				.setHeader("Nombre y Apellido");
		Grid.Column<Pasajero> columnDNI = grid.addColumn(Pasajero::getDni)
				.setHeader("DNI");

		Binder<Pasajero> binder = new Binder<>(Pasajero.class);
		editor = grid.getEditor();
		editor.setBinder(binder);
		editor.setBuffered(true);

		TextField fieldNombre = new TextField();
		fieldNombre.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		binder.forField(fieldNombre)
				.withValidator(name -> !name.isEmpty(),
						"Nombre no puede estar vacío").bind("nombreCompleto");
		columnNombre.setEditorComponent(fieldNombre);

		TextField fieldDni = new TextField();
		fieldDni.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		fieldDni.setPattern("[0-9]*");
		fieldDni.setPreventInvalidInput(true);
		binder.forField(fieldDni)
				.withValidator(dni -> !dni.isEmpty(),
						"DNI no puede estar vacío").bind("dni");
		columnDNI.setEditorComponent(fieldDni);

		editButtons = Collections
				.newSetFromMap(new WeakHashMap<>());

		editor.addCloseListener(event -> {
			editButtons.stream().forEach(button -> button.setEnabled(true));
		});

		Grid.Column<Pasajero> editorColumn = grid.addComponentColumn(pasajero -> {
			Button edit = new Button("Editar");
			edit.addThemeVariants(ButtonVariant.LUMO_SMALL);
			edit.addClassName("edit");
			edit.addClickListener(e -> {
				editor.editItem(pasajero);
				fieldNombre.focus();
			});
			edit.setEnabled(!editor.isOpen());
			edit.setVisible(editarInvisible);
			editButtons.add(edit);
			return edit;
		});

		editor.addOpenListener(e -> editButtons.stream()
				.forEach(button -> button.setEnabled(!editor.isOpen())));
		editor.addCloseListener(e -> editButtons.stream()
				.forEach(button -> button.setEnabled(!editor.isOpen())));

		Button save = new Button("Aceptar", e -> {editor.save();
		grid.getDataProvider().refreshAll();});
		save.addClassName("save");
		save.addThemeVariants(ButtonVariant.LUMO_SMALL);

		Button cancel = new Button("Cancelar", e -> editor.cancel());
		cancel.addClassName("cancel");
		cancel.addThemeVariants(ButtonVariant.LUMO_SMALL);

		grid.getElement().addEventListener("keyup", event -> editor.cancel())
				.setFilter("event.key === 'Escape' || event.key === 'Esc'");

		Div buttons = new Div(save, cancel);
		editorColumn.setEditorComponent(buttons);

	}

	public void setModoConsulta(){
		newPasajero.setVisible(false);
		removeLastButton.setVisible(false);
	}
	
	public Button getNewPasajero() {
		return newPasajero;
	}

	public void setNewPasajero(Button newPasajero) {
		this.newPasajero = newPasajero;
	}

	public Button getRemoveLastButton() {
		return removeLastButton;
	}

	public void setRemoveLastButton(Button removeLastButton) {
		this.removeLastButton = removeLastButton;
	}

	public Grid<Pasajero> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Pasajero> grid) {
		this.grid = grid;
	}

	public List<Pasajero> getPasajerosList() {
		return pasajerosList;
	}

	public void setPasajerosList(List<Pasajero> pasajerosList) {
		this.pasajerosList = pasajerosList;
		grid.getDataProvider().refreshAll();
	}

	public Boolean getEditarInvisible() {
		return editarInvisible;
	}

	public void setEditarInvisible(Boolean editarInvisible) {
		this.editarInvisible = editarInvisible;
	}
}
