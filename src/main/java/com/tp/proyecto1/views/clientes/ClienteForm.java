package com.tp.proyecto1.views.clientes;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;


public class ClienteForm extends Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private TextField nombre;
	private TextField apellido;
	private TextField telefono;
	private TextField calle;
	private TextField altura;
	private TextField dni;
	private TextField localidad;
	private TextField ciudad;
	private TextField pais;
	private TextField codPostal;
	private EmailField email;
	private DatePicker fechaNacimiento;
	private Button save;
	private Button cancel;

	public ClienteForm() {

		setComponents();
		setForm();
		setLayouts();

	}

	private void setComponents() {

		save = new Button("Guardar");
		cancel = new Button("Cancelar");
		nombre = new TextField();
		apellido = new TextField();
		telefono = new TextField();
		email = new EmailField();
		calle = new TextField();
		altura = new TextField();
		localidad = new TextField();
		ciudad = new TextField();
		pais = new TextField();
		codPostal = new TextField();
		dni = new TextField();
		fechaNacimiento = new DatePicker();

	}

	private void setForm() {
		form.addFormItem(nombre, "Nombre");
		form.addFormItem(apellido, "Apellido");
		form.addFormItem(fechaNacimiento, "Fecha de Nacimiento");
		form.addFormItem(email, "E-mail");
		form.addFormItem(telefono, "Teléfono");
		form.addFormItem(calle, "Calle");
		form.addFormItem(altura, "Altura");
		form.addFormItem(localidad, "Localidad");
		form.addFormItem(ciudad, "Ciudad");
		form.addFormItem(pais, "País");
		form.addFormItem(codPostal, "CP");
		form.addFormItem(dni, "DNI");
	}

	private void setLayouts() {
		HorizontalLayout actions = new HorizontalLayout();
		actions.add(save, cancel);

		mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
		mainLayout.add(form, actions);
		mainLayout.setSizeFull();

		this.add(mainLayout);
		this.setWidth("800px");
		this.setHeight("100%");

	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public FormLayout getForm() {
		return form;
	}

	public TextField getNombre() {
		return nombre;
	}

	public TextField getApellido() {
		return apellido;
	}

	public TextField getTelefono() {
		return telefono;
	}

	public EmailField getEmail() {
		return email;
	}

	public DatePicker getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Button getSave() {
		return save;
	}

	public Button getCancel() {
		return cancel;
	}

	public TextField getCalle() {
		return calle;
	}

	public TextField getAltura() {
		return altura;
	}

	public TextField getDni() {
		return dni;
	}

	public TextField getLocalidad() {
		return localidad;
	}

	public TextField getCiudad() {
		return ciudad;
	}

	public TextField getPais() {
		return pais;
	}

	public TextField getCodPostal() {
		return codPostal;
	}


}
