package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.Cliente;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.views.ClientesView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class ClientesController {

	private ClientesView clientesView;

	private ClienteService clienteService;

	private ChangeHandler changeHandler;

	@Autowired
	public ClientesController(ClienteService clienteService) {
		this.clientesView = new ClientesView();
		this.clienteService = clienteService;
		setListeners();
		setComponents();
		listClientes();
	}

	private void setComponents() {
		this.clientesView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END);
	}

	private void setListeners() {
		setChangeHandler(this::listClientes);
		clientesView.getAddNewBtn().addClickListener(e-> saveCliente());
	}

	private void saveCliente() {
		String name = clientesView.getTextName().getValue();
		String lastName = clientesView.getTextLastName().getValue();
		String dni = clientesView.getTextDni().getValue();
		String age = clientesView.getTextAge().getValue();
		String email = "asd";
		Cliente newCliente = new Cliente(name, lastName, dni, age, email);
		clienteService.save(newCliente);
		Notification.show("Cliente Guardado");
		changeHandler.onChange();
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
