package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.Cliente;
import com.tp.proyecto1.model.Domicilio;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.views.clientes.ClienteForm;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@UIScope
public class ClienteFormController {

	private ClienteForm clienteForm;

	private ClienteService clienteService;

	private ChangeHandler changeHandler;

	private Cliente cliente;

	private Binder<Cliente> binderCliente = new Binder<>();
	private Binder<Domicilio> binderDomicilio = new Binder<>();

	@Autowired
	public ClienteFormController(ClienteService clienteService) {
		this.clienteService = clienteService;
		this.clienteForm = new ClienteForm();
		setListeners();
		setBinders();
	}

	private void setListeners() {
		clienteForm.getSave().addClickListener(e->saveCliente(cliente));
		clienteForm.getCancel().addClickListener(e->{clean();clienteForm.close();});
	}

	private void saveCliente(Cliente cliente) {

		if(cliente==null){
			cliente = setNewCliente();
		}
		Domicilio domicilioNewCliente = cliente.getDomicilio();

		if (binderCliente.writeBeanIfValid(cliente) &&
				binderDomicilio.writeBeanIfValid(domicilioNewCliente)) {
			clienteService.save(cliente);
			clienteForm.close();
			Notification.show("Cliente Guardado");
			changeHandler.onChange();
			clean();
		}
	}

	private Cliente setNewCliente() {
		String nombre = clienteForm.getNombre().getValue();
		String apellido = clienteForm.getApellido().getValue();
		String dni = clienteForm.getDni().getValue();
		String email = clienteForm.getEmail().getValue();
		String telefono = clienteForm.getTelefono().getValue();
		LocalDate fechaNacimiento = clienteForm.getFechaNacimiento().getValue();
		String calle = clienteForm.getCalle().getValue();
		String altura = clienteForm.getAltura().getValue();
		String localidad = clienteForm.getLocalidad().getValue();
		String ciudad = clienteForm.getCiudad().getValue();
		String pais = clienteForm.getPais().getValue();
		String codPostal = clienteForm.getCodPostal().getValue();
		Domicilio domicilio = new Domicilio(calle, altura, localidad, ciudad, pais, codPostal);
		return new Cliente(nombre, apellido, dni, fechaNacimiento, domicilio, email, telefono);
	}

	public void setComponentsValues(Cliente cliente) {
		this.cliente = cliente;
		clienteForm.getNombre().setValue(cliente.getNombre());
		clienteForm.getApellido().setValue(cliente.getApellido());
		clienteForm.getTelefono().setValue(cliente.getTelefono());
		clienteForm.getEmail().setValue(cliente.getEmail());
		clienteForm.getCalle().setValue(cliente.getDomicilio().getCalle());
		clienteForm.getAltura().setValue(cliente.getDomicilio().getAltura());
		clienteForm.getLocalidad().setValue(cliente.getDomicilio().getLocalidad());
		clienteForm.getCiudad().setValue(cliente.getDomicilio().getCiudad());
		clienteForm.getPais().setValue(cliente.getDomicilio().getPais());
		clienteForm.getCodPostal().setValue(cliente.getDomicilio().getCodPostal());
		clienteForm.getDni().setValue(cliente.getDni());
		clienteForm.getFechaNacimiento().setValue(cliente.getFechaNacimiento());
	}

	private void setBinders() {

		setBinderTextFieldCliente(clienteForm.getNombre(), Cliente::getNombre, Cliente::setNombre);
		setBinderTextFieldCliente(clienteForm.getApellido(), Cliente::getApellido, Cliente::setApellido);
		setBinderTextFieldCliente(clienteForm.getTelefono(), Cliente::getTelefono, Cliente::setTelefono);
		setBinderEmailFieldCliente(clienteForm.getEmail(), Cliente::getEmail, Cliente::setEmail);
		setBinderTextFieldDomicilio(clienteForm.getCalle(), Domicilio::getCalle, Domicilio::setCalle);
		setBinderTextFieldDomicilio(clienteForm.getAltura(), Domicilio::getAltura, Domicilio::setAltura);
		setBinderTextFieldDomicilio(clienteForm.getLocalidad(), Domicilio::getLocalidad, Domicilio::setLocalidad);
		setBinderTextFieldDomicilio(clienteForm.getCiudad(), Domicilio::getCiudad, Domicilio::setCiudad);
		setBinderTextFieldDomicilio(clienteForm.getPais(), Domicilio::getPais, Domicilio::setPais);
		setBinderTextFieldDomicilio(clienteForm.getCodPostal(), Domicilio::getCodPostal, Domicilio::setCodPostal);
		setBinderTextFieldCliente(clienteForm.getDni(), Cliente::getDni, Cliente::setDni);
		setBinderDatePickerCliente(clienteForm.getFechaNacimiento(), Cliente::getFechaNacimiento, Cliente::setFechaNacimiento);

	}

	private void setBinderTextFieldCliente(TextField textField, ValueProvider<Cliente, String> valueProvider, Setter<Cliente, String> setter){

		textField.setValueChangeMode(ValueChangeMode.EAGER);
		SerializablePredicate<String> predicate = value -> !textField
				.getValue().trim().isEmpty();

		Binder.Binding<Cliente, String> binding = binderCliente.forField(textField)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		clienteForm.getSave().addClickListener(event -> binding.validate());
	}
	private void setBinderDatePickerCliente(DatePicker datePicker, ValueProvider<Cliente, LocalDate> valueProvider, Setter<Cliente, LocalDate> setter){

		SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue() != null;

		Binder.Binding<Cliente, LocalDate> binding = binderCliente.forField(datePicker)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		clienteForm.getSave().addClickListener(event -> binding.validate());
	}

	private void setBinderTextFieldDomicilio(TextField textField, ValueProvider<Domicilio, String> valueProvider,  Setter<Domicilio, String> setter){

		textField.setValueChangeMode(ValueChangeMode.EAGER);
		SerializablePredicate<String> predicate = value -> !textField
				.getValue().trim().isEmpty();

		Binder.Binding<Domicilio, String> binding = binderDomicilio.forField(textField)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		clienteForm.getSave().addClickListener(event -> binding.validate());
	}

	private void setBinderEmailFieldCliente(EmailField emailField, ValueProvider<Cliente, String> valueProvider,  Setter<Cliente, String> setter){

		emailField.setValueChangeMode(ValueChangeMode.EAGER);
		SerializablePredicate<String> predicate = value -> !emailField
				.getValue().trim().isEmpty();

		Binder.Binding<Cliente, String> binding = binderCliente.forField(emailField)
				.withValidator(predicate, "El campo es obligatorio")
				.bind(valueProvider, setter);
		clienteForm.getSave().addClickListener(event -> binding.validate());
	}

	public void clean() {
		binderCliente.readBean(null);
		binderDomicilio.readBean(null);
		clienteForm.getNombre().setValue("");
		clienteForm.getApellido().setValue("");
		clienteForm.getTelefono().setValue("");
		clienteForm.getEmail().setValue("");
		clienteForm.getCalle().setValue("");
		clienteForm.getAltura().setValue("");
		clienteForm.getLocalidad().setValue("");
		clienteForm.getCiudad().setValue("");
		clienteForm.getPais().setValue("");
		clienteForm.getCodPostal().setValue("");
		clienteForm.getDni().setValue("");
		clienteForm.getFechaNacimiento().setValue(null);
	}


	public ClienteForm getClienteForm() {
		return clienteForm;
	}

	public interface ChangeHandler {
		void onChange();
	}
	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}
