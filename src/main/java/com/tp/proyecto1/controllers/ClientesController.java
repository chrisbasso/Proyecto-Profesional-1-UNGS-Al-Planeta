package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.TransaccionService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
import com.tp.proyecto1.utils.GenericDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.clientes.ClientesView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@UIScope
public class ClientesController {

	private ClientesView clientesView;

	private ClienteFormController clienteFormController;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TransaccionService transaccionService;

	private ChangeHandler changeHandler;

	public ClientesController() {
		Inject.Inject(this);
		this.clientesView = new ClientesView();
		setListeners();
		setComponents();
		listClientes();
	}

	private void setComponents() {
		this.clientesView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
		this.clientesView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
	}

	private void setListeners() {
		setChangeHandler(this::listClientes);
		clientesView.getNewClientButton().addClickListener(e-> openNewClienteForm());
		clientesView.getSearchButton().addClickListener(e-> listClientes());
		clientesView.getBtnHistorialEventos().addClickListener(e->openHistorialEventos());
	}

	private void openHistorialEventos() {

		Optional<Cliente> clienteSeleccionado = clientesView.getGrid().asSingleSelect().getOptionalValue();
		if(clienteSeleccionado.isPresent()){
			EventosClienteWindowController historico = new EventosClienteWindowController(clienteSeleccionado.get());
			historico.getView().open();
		}else{
			Notification.show("Debe seleccionar un cliente");
		}
	}

	private void openNewClienteForm() {
		clienteFormController = new ClienteFormController();
		clienteFormController.getClienteForm().open();
		clienteFormController.setChangeHandler(this::listClientes);
	}

	private void deleteCliente(Cliente cliente) {
		if(transaccionService.verificarTransaccionCliente(cliente)){
			GenericDialog dialog = new GenericDialog("No puede dar de baja al cliente " + cliente.getId() + " debido a que posee viajes activos al día de la fecha");
			return;
		}
		ConfirmationDialog confirmationDialog = new ConfirmationDialog("¿Realmente desea dar de baja al Cliente?");
		confirmationDialog.getConfirmButton().addClickListener(event -> {cliente.setActivo(false);
			cliente.setFechaBaja(LocalDate.now());
			clienteService.save(cliente);
			Notification.show("Cliente fue dado de baja");
			changeHandler.onChange();
		});
		confirmationDialog.open();
	}

	private Button createRemoveButton(Cliente cliente) {
		Button botonEliminar = new Button(VaadinIcon.TRASH.create(), clickEvent -> deleteCliente(cliente));
		if(!cliente.isActivo()){
			botonEliminar.setEnabled(false);
		}
		return botonEliminar;
	}

	private Button createEditButton(Cliente cliente) {
		return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
			clienteFormController = new ClienteFormController();
			clienteFormController.setComponentsValues(cliente);
			clienteFormController.getClienteForm().open();
			clienteFormController.setChangeHandler(this::listClientes);
		});
	}

	private void listClientes() {
		Cliente clienteBusqueda = new Cliente();
		if(checkFiltros()){
			setParametrosBusqueda(clienteBusqueda);
			clientesView.getGrid().setItems(clienteService.findClientes(clienteBusqueda));
		}else{
			clientesView.getGrid().setItems(clienteService.findAll());
		}
	}

	private void setParametrosBusqueda(Cliente clienteBusqueda) {
		if(!clientesView.getIdFilter().isEmpty()){
			clienteBusqueda.setId(clientesView.getIdFilter().getValue().longValue());
		}
		if (!clientesView.getDniFilter().isEmpty()) {
			clienteBusqueda.setDni(String.valueOf(clientesView.getDniFilter().getValue().intValue()));
		}
		if (!clientesView.getNameFilter().isEmpty()) {
			clienteBusqueda.setNombre(clientesView.getNameFilter().getValue());
		}
		if (!clientesView.getLastNameFilter().isEmpty()) {
			clienteBusqueda.setApellido(clientesView.getLastNameFilter().getValue());
		}
		clienteBusqueda.setActivo(clientesView.getActivosCheck().getValue());
	}

	private boolean checkFiltros() {
		return !clientesView.getIdFilter().isEmpty() || !clientesView.getDniFilter().isEmpty() ||
		!clientesView.getNameFilter().isEmpty() || !clientesView.getLastNameFilter().isEmpty() ||
		clientesView.getActivosCheck().getValue();
	}

	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
	public ClientesView getView(){
		return clientesView;
	}

}
