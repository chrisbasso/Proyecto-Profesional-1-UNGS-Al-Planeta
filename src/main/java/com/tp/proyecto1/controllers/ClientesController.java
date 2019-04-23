package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.Cliente;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.views.clientes.ClientesView;
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

	@Autowired
	private ClienteFormController clienteFormController;

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
		this.clientesView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("80px").setFlexGrow(0);
		this.clientesView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("80px").setFlexGrow(0);
	}

	private void setListeners() {
		setChangeHandler(this::listClientes);
		clientesView.getNewClientButton().addClickListener(e-> openNewClienteForm());

	}

	private void openNewClienteForm() {
		clienteFormController.getClienteForm().open();
		clienteFormController.setChangeHandler(this::listClientes);
	}

	private void deleteCliente(Cliente cliente) {
		clienteService.delete(cliente);
		Notification.show("Cliente Eliminado");
		changeHandler.onChange();
	}

	private Button createRemoveButton(Cliente cliente) {
		return new Button(VaadinIcon.TRASH.create(), clickEvent -> deleteCliente(cliente));
	}

	private Button createEditButton(Cliente cliente) {
		return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
			clienteFormController.setComponentsValues(cliente);
			clienteFormController.getClienteForm().open();
			clienteFormController.setChangeHandler(this::listClientes);
		});
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
