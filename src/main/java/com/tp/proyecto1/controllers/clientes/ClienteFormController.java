package com.tp.proyecto1.controllers.clientes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.clientes.Domicilio;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.clientes.ClienteForm;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
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

	@Autowired
	private ClienteService clienteService;

	private ChangeHandler changeHandler;

	private Cliente cliente;

	private Binder<Cliente> binderCliente = new Binder<>();
	private Binder<Domicilio> binderDomicilio = new Binder<>();

	public ClienteFormController() {
		Inject.Inject(this);
		this.clienteForm = new ClienteForm();
		setListeners();
		setBinders();
	}

	private void setListeners() {
		clienteForm.getSave().addClickListener(e->saveCliente(cliente));
		clienteForm.getCancel().addClickListener(e->clienteForm.close());
	}

	private void saveCliente(Cliente cliente) {

		if(cliente==null){
			cliente = setNewCliente();
		}
		Domicilio domicilioNewCliente = cliente.getDomicilio();

		if (binderCliente.writeBeanIfValid(cliente) &&
				binderDomicilio.writeBeanIfValid(domicilioNewCliente)) {
			if(!cliente.isActivo()){
				cliente.setFechaBaja(LocalDate.now());
			}else{
				cliente.setFechaBaja(null);
			}
			clienteService.save(cliente);
			clienteForm.close();
			Notification.show("Cliente Guardado");
			changeHandler.onChange();
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
		return new Cliente(nombre, apellido,dni,fechaNacimiento,domicilio,email,telefono,true,null, LocalDate.now());
	}

	public void setComponentsValues(Cliente cliente) {
		this.cliente = cliente;
		binderCliente.setBean(cliente);
		binderDomicilio.setBean(cliente.getDomicilio());
		clienteForm.getCheckActivo().setVisible(true);
		setBinders();
	}

	private void setBinders() {

		setBinderFieldCliente(clienteForm.getNombre(), Cliente::getNombre, Cliente::setNombre);
		setBinderFieldCliente(clienteForm.getApellido(), Cliente::getApellido, Cliente::setApellido);
		setBinderFieldCliente(clienteForm.getTelefono(), Cliente::getTelefono, Cliente::setTelefono);
		setBinderFieldCliente(clienteForm.getEmail(), Cliente::getEmail, Cliente::setEmail);
		setBinderFieldDomicilio(clienteForm.getCalle(), Domicilio::getCalle, Domicilio::setCalle);
		setBinderFieldDomicilio(clienteForm.getAltura(), Domicilio::getAltura, Domicilio::setAltura);
		setBinderFieldDomicilio(clienteForm.getLocalidad(), Domicilio::getLocalidad, Domicilio::setLocalidad);
		setBinderFieldDomicilio(clienteForm.getCiudad(), Domicilio::getCiudad, Domicilio::setCiudad);
		setBinderFieldDomicilio(clienteForm.getPais(), Domicilio::getPais, Domicilio::setPais);
		setBinderFieldDomicilio(clienteForm.getCodPostal(), Domicilio::getCodPostal, Domicilio::setCodPostal);
		setBinderFieldCliente(clienteForm.getDni(), Cliente::getDni, Cliente::setDni);
		setBinderDatePickerCliente(clienteForm.getFechaNacimiento(), Cliente::getFechaNacimiento, Cliente::setFechaNacimiento);
		setBinderCheckCliente(clienteForm.getCheckActivo(), Cliente::isActivo, Cliente::setActivo);
	}

	private void setBinderCheckCliente(Checkbox check, ValueProvider<Cliente, Boolean> valueProvider, Setter<Cliente, Boolean> setter){
		Binder.Binding<Cliente, Boolean> binding = binderCliente.forField(check).bind(valueProvider, setter);
		clienteForm.getSave().addClickListener(event -> binding.validate());
	}

	private void setBinderFieldCliente(AbstractField field, ValueProvider<Cliente, String> valueProvider, Setter<Cliente, String> setter){

		SerializablePredicate<String> predicate = value -> !field.isEmpty();

		Binder.Binding<Cliente, String> binding = binderCliente.forField(field)
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

	private void setBinderFieldDomicilio(AbstractField field, ValueProvider<Domicilio, String> valueProvider, Setter<Domicilio, String> setter){

		SerializablePredicate<String> predicate = value -> !field.isEmpty();

		Binder.Binding<Domicilio, String> binding = binderDomicilio.forField(field)
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

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}
