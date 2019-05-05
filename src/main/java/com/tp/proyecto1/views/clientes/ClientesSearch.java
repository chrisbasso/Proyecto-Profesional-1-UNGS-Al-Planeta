package com.tp.proyecto1.views.clientes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ClientesSearch extends Dialog{

    private HorizontalLayout mainLayout;
	private Grid<Cliente> grid;
	private NumberField filtroId;
	private TextField filtroNombre;
	private TextField filtroApellido;
	private NumberField filtroDNI;
	private Button btnBuscarCliente;
	
	public ClientesSearch() {		    	
		mainLayout = new HorizontalLayout();		
		setComponents();
		setLayout();
		setGrid();		
	}

	private void setComponents() {
		grid = new Grid<>(Cliente.class);
		filtroId = new NumberField();
		filtroNombre = new TextField();
		filtroApellido = new TextField();
		filtroDNI = new NumberField();
		btnBuscarCliente = new Button("Buscar", VaadinIcon.SEARCH.create());
		btnBuscarCliente.setMinWidth("130px");
		filtroNombre.setLabel("Nombre");
		filtroId.setLabel("N° Cliente");
		filtroApellido.setLabel("Apellido");
		filtroDNI.setLabel("DNI");		
	}

	private void setLayout() {
		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		HorizontalLayout actions = new HorizontalLayout(filtroId, filtroNombre, filtroApellido, filtroDNI,hlSpace, btnBuscarCliente);
		this.add(actions, grid);
		this.setSizeFull();
		actions.setWidthFull();
		actions.setVerticalComponentAlignment(Alignment.END, btnBuscarCliente);
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

	public NumberField getFiltroId() {
		return filtroId;
	}

	public void setFiltroId(NumberField idFilter) {
		this.filtroId = idFilter;
	}

	public TextField getFiltroNombre() {
		return filtroNombre;
	}

	public void setFiltroNombre(TextField filtroNombre) {
		this.filtroNombre = filtroNombre;
	}

	public TextField getFiltroApellido() {
		return filtroApellido;
	}

	public void setFiltroApellido(TextField filtroApellido) {
		this.filtroApellido = filtroApellido;
	}

	public NumberField getFiltroDNI() {
		return filtroDNI;
	}

	public void setFiltroDNI(NumberField filtroDNI) {
		this.filtroDNI = filtroDNI;
	}

	public Button getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public void setBtnBuscarCliente(Button btnBuscarCliente) {
		this.btnBuscarCliente = btnBuscarCliente;
	}
}