package com.tp.proyecto1.views.clientes;


import com.tp.proyecto1.controllers.ClientesController;
import com.tp.proyecto1.model.Cliente;
import com.tp.proyecto1.model.Domicilio;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;

import java.time.LocalDate;
import java.util.Date;

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
	private Label infoLabel;
	private Button save;
	private Button cancel;

	Binder<Cliente> binderCliente = new Binder<>();
	Binder<Domicilio> binderDomicilio = new Binder<>();

	public ClienteForm() {

		setComponents();
		setForm();
		setLayouts();

	}

	private void setComponents() {

		save = new Button("Guardar");
		cancel = new Button("Cancelar");
		nombre = new TextField();
		setBinderTextFieldCliente(nombre, Cliente::getNombre, Cliente::setNombre);
		apellido = new TextField();
		setBinderTextFieldCliente(apellido, Cliente::getApellido, Cliente::setApellido);
		telefono = new TextField();
		setBinderTextFieldCliente(telefono, Cliente::getTelefono, Cliente::setTelefono);
		email = new EmailField();
		setBinderEmailFieldCliente(email, Cliente::getEmail, Cliente::setEmail);
		calle = new TextField();
		setBinderTextFieldDomicilio(calle, Domicilio::getCalle, Domicilio::setCalle);
		altura = new TextField();
		setBinderTextFieldDomicilio(altura, Domicilio::getAltura, Domicilio::setAltura);
		localidad = new TextField();
		setBinderTextFieldDomicilio(localidad, Domicilio::getLocalidad, Domicilio::setLocalidad);
		ciudad = new TextField();
		setBinderTextFieldDomicilio(ciudad, Domicilio::getCiudad, Domicilio::setCiudad);
		pais = new TextField();
		setBinderTextFieldDomicilio(pais, Domicilio::getPais, Domicilio::setPais);
		codPostal = new TextField();
		setBinderTextFieldDomicilio(codPostal, Domicilio::getCodPostal, Domicilio::setCodPostal);
		dni = new TextField();
		setBinderTextFieldCliente(dni, Cliente::getDni, Cliente::setDni);
		fechaNacimiento = new DatePicker();
		setBinderDatePickerCliente(fechaNacimiento, Cliente::getFechaNacimiento, Cliente::setFechaNacimiento);
		infoLabel = new Label();

	}

	private void setBinderTextFieldCliente(TextField textField, ValueProvider<Cliente, String> valueProvider,  Setter<Cliente, String> setter){

		//textField.setValueChangeMode(ValueChangeMode.EAGER);
		SerializablePredicate<String> predicate = value -> !textField
				.getValue().trim().isEmpty();

		Binder.Binding<Cliente, String> binding = binderCliente.forField(textField)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		save.addClickListener(event -> binding.validate());
	}
	private void setBinderDatePickerCliente(DatePicker datePicker, ValueProvider<Cliente, LocalDate> valueProvider, Setter<Cliente, LocalDate> setter){

		SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue() != null;

		Binder.Binding<Cliente, LocalDate> binding = binderCliente.forField(datePicker)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		save.addClickListener(event -> binding.validate());
	}

	private void setBinderTextFieldDomicilio(TextField textField, ValueProvider<Domicilio, String> valueProvider,  Setter<Domicilio, String> setter){

		textField.setValueChangeMode(ValueChangeMode.EAGER);
		SerializablePredicate<String> predicate = value -> !textField
				.getValue().trim().isEmpty();

		Binder.Binding<Domicilio, String> binding = binderDomicilio.forField(textField)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		save.addClickListener(event -> binding.validate());
	}

	private void setBinderEmailFieldCliente(EmailField emailField, ValueProvider<Cliente, String> valueProvider,  Setter<Cliente, String> setter){

		emailField.setValueChangeMode(ValueChangeMode.EAGER);
		SerializablePredicate<String> predicate = value -> !emailField
				.getValue().trim().isEmpty();

		Binder.Binding<Cliente, String> binding = binderCliente.forField(emailField)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		save.addClickListener(event -> binding.validate());
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
	public void clean() {
		binderCliente.readBean(null);
		binderDomicilio.readBean(null);
		nombre.setValue("");
		apellido.setValue("");
		telefono.setValue("");
		email.setValue("");
		calle.setValue("");
		altura.setValue("");
		localidad.setValue("");
		ciudad.setValue("");
		pais.setValue("");
		codPostal.setValue("");
		dni.setValue("");
		fechaNacimiento.setValue(null);
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

	public Label getInfoLabel() {
		return infoLabel;
	}

	public Binder<Cliente> getBinderCliente() {
		return binderCliente;
	}

	public Binder<Domicilio> getBinderDomicilio() {
		return binderDomicilio;
	}
}
