package com.tp.proyecto1.controllers.clientes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.eventos.Reclamo;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.utils.GenericDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.EventosClienteWindow;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
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
		setComponents();
		listarEventos();
		setListeners();

	}

	private void setListeners() {
		this.view.getGrid().addItemDoubleClickListener(this::mostrarDetalleEvento);
	}

	private void mostrarDetalleEvento(ItemDoubleClickEvent<Evento> e) {
		Evento eventoSeleccionado = e.getItem();
		GenericDialog genericDialog = new GenericDialog(eventoSeleccionado.getMensaje());
	}

	private void setComponents() {
		this.view.getGrid().addColumn(this::setTipo).setHeader("Tipo");
		this.view.getGrid().addColumn(this::setEstado).setHeader("Estado");
	}


	private String setEstado(Evento evento) {
		String tipo;
		if(evento.isAbierto()){
			tipo = "Abierto";
		}else{
			tipo = "Cerrado";
		}
		return tipo;
	}

	private String setTipo(Evento evento) {
		String tipo;
		if(evento instanceof Reclamo){
			tipo = "Reclamo";
		}else{
			tipo = "Consulta";
		}
		return tipo;
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
