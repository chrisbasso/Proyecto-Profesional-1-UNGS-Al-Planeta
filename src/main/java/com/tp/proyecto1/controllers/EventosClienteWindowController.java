package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.EventosClienteWindow;
import org.springframework.beans.factory.annotation.Autowired;

public class EventosClienteWindowController {

	@Autowired
	private EventoService eventoService;

	private EventosClienteWindow view;

	private Cliente cliente;

	public EventosClienteWindowController(Cliente cliente) {
		Inject.Inject(this);
		this.view = new EventosClienteWindow();
		this.cliente = cliente;
		listarEventos();

	}

	private void listarEventos() {
		this.view.getGrid().setItems(eventoService.findEventosByPersona(cliente));

	}


	public EventosClienteWindow getView() {
		return view;
	}

	public void setView(EventosClienteWindow view) {
		this.view = view;
	}
}
