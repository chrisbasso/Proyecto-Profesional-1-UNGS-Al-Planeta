package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.Cliente;
import com.tp.proyecto1.model.Domicilio;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.views.clientes.ClientesView;
import com.tp.proyecto1.views.clientes.ClienteForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@UIScope
public class ClientesController {

	private ClientesView clientesView;

	private ClienteForm clienteForm;

	private ClienteService clienteService;

	private ChangeHandler changeHandler;

	@Autowired
	public ClientesController(ClienteService clienteService) {
		this.clientesView = new ClientesView();
		this.clienteService = clienteService;
		this.clienteForm = new ClienteForm();
		setListeners();
		setComponents();
		listClientes();
	}

	private void setComponents() {
		this.clientesView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END);
	}

	private void setListeners() {
		setChangeHandler(this::listClientes);
		clientesView.getNewClientButton().addClickListener(e-> openNewClienteForm());
		clienteForm.getSave().addClickListener(e->saveCliente());
		clienteForm.getCancel().addClickListener(e->{clienteForm.clean();clienteForm.close();});
	}

	private void openNewClienteForm() {
		clienteForm.open();
	}

	private void saveCliente() {

		Cliente newCliente = setNewCliente();
		Domicilio domicilioNewCliente = newCliente.getDomicilio();

		if (clienteForm.getBinderCliente().writeBeanIfValid(newCliente) &&
				clienteForm.getBinderDomicilio().writeBeanIfValid(domicilioNewCliente)) {
			clienteService.save(newCliente);
			clienteForm.close();
			Notification.show("Cliente Guardado");
			changeHandler.onChange();
			clienteForm.clean();
		}
	}

	private Cliente setNewCliente() {
		String nombre = clienteForm.getNombre().getValue();
		String apellido = clienteForm.getApellido().getValue();
		String dni = String.valueOf(clienteForm.getDni().getValue());
		String email = clienteForm.getEmail().getValue();
		String telefono = clienteForm.getTelefono().getValue();
		LocalDate fechaNacimiento = clienteForm.getFechaNacimiento().getValue();
		String calle = clienteForm.getCalle().getValue();
		String altura = String.valueOf(clienteForm.getAltura().getValue());
		String localidad = clienteForm.getLocalidad().getValue();
		String ciudad = clienteForm.getCiudad().getValue();
		String pais = clienteForm.getPais().getValue();
		String codPostal = clienteForm.getCodPostal().getValue();
		Domicilio domicilio = new Domicilio(calle, altura, localidad, ciudad, pais, codPostal);
		return new Cliente(nombre, apellido, dni, fechaNacimiento, domicilio, email, telefono);
	}

	private void deleteCliente(Cliente cliente) {
		clienteService.delete(cliente);
		Notification.show("Cliente Eliminado");
		changeHandler.onChange();
	}

	private Button createRemoveButton(Cliente cliente) {
		return new Button(VaadinIcon.TRASH.create(), clickEvent -> deleteCliente(cliente));
	}

	private void listClientes() {
		clientesView.getGrid().setItems(clienteService.findAll());
	}
	public interface ChangeHandler {
		void onChange();
	}
	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
	public ClientesView getView(){
		return clientesView;
	}

}
