package com.tp.proyecto1.controllers;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.clientes.ClientesSearch;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ClienteSearchController {
	
	private static final Logger logger = Logger.getLogger("ClienteSearchController");
	private static final Handler consoleHandler = new ConsoleHandler();
	
	private static void loggerHandler() {		
		logger.addHandler(consoleHandler);
		consoleHandler.setLevel(Level.ALL);
	}
	
	private ClientesSearch clientesSearchView;
	@Autowired
	private ClienteService clienteService;
	private ChangeHandler changeHandler;
	
	public ClienteSearchController() {
		loggerHandler();
		logger.log(Level.INFO, "Comenzando");
		Inject.Inject(this);
		clientesSearchView = new ClientesSearch();
		setListeners();
		setComponents();
		listClientes();
	}

	private void setListeners() {
		setChangeHandler(this::listClientes);
		clientesSearchView.getBtnBuscarCliente().addClickListener(e-> listClientes());
	}

	private void setComponents() {
		this.clientesSearchView.getGrid().addComponentColumn(this::crearBotonSeleccionar).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
	}

	private Button crearBotonSeleccionar(Cliente cliente) {
		return new Button(VaadinIcon.SELECT.create(), clickEvent -> {
			
		});
	}

	private void listClientes() {
		Cliente clienteBusqueda = new Cliente();
		if(checkFiltros()){
			setParametrosBusqueda(clienteBusqueda);
			clientesSearchView.getGrid().setItems(clienteService.findClientes(clienteBusqueda));
			logger.log(Level.INFO, "CheckInfoTRUE");
		}else{
			clientesSearchView.getGrid().setItems(clienteService.findAll());
			logger.log(Level.INFO, "CheckInfoFALSE");
		}
	}

	private void setParametrosBusqueda(Cliente clienteBusqueda) {
		if(!clientesSearchView.getFiltroId().isEmpty()){
			clienteBusqueda.setId(clientesSearchView.getFiltroId().getValue().longValue());
		}
		if (!clientesSearchView.getFiltroDNI().isEmpty()) {
			clienteBusqueda.setDni(String.valueOf(clientesSearchView.getFiltroDNI().getValue().intValue()));
		}
		if (!clientesSearchView.getFiltroNombre().isEmpty()) {
			clienteBusqueda.setNombre(clientesSearchView.getFiltroNombre().getValue());
		}
		if (!clientesSearchView.getFiltroApellido().isEmpty()) {
			clienteBusqueda.setApellido(clientesSearchView.getFiltroApellido().getValue());
		}
		clienteBusqueda.setActivo(true);
	}

	private boolean checkFiltros() {
		return !clientesSearchView.getFiltroId().isEmpty() || !clientesSearchView.getFiltroDNI().isEmpty() ||
		!clientesSearchView.getFiltroNombre().isEmpty() || !clientesSearchView.getFiltroApellido().isEmpty();
	}

	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
	
	public ClientesSearch getView(){
		return clientesSearchView;
	}
}