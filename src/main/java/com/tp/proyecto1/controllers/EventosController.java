package com.tp.proyecto1.controllers;


import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.EventosView;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@UIScope
public class EventosController {

	private EventosView eventosView;

	private ChangeHandler changeHandler;

	@Autowired
	EventoService eventoService;

	public EventosController() {
		Inject.Inject(this);
		this.eventosView = new EventosView();
		setListeners();
		listarEventos();
	}

	private void setListeners() {
		setChangeHandler(this::listarEventos);
		eventosView.getNewConsultaButton().addClickListener(e->openConsultaForm());
		eventosView.getSearchButton().addClickListener(e->listarEventos());
	}

	private void openConsultaForm() {
		ConsultaFormController consultaFormController = new ConsultaFormController();
		consultaFormController.setChangeHandler(this::listarEventos);
		consultaFormController.getView().open();
	}

	private void listarEventos() {
		Evento eventoConsulta = new Evento();
//        if(checkFiltros()){
//            setParametrosBusqueda(clienteBusqueda);
//            clientesView.getGrid().setItems(clienteService.findClientes(clienteBusqueda));
//        }else{
            eventosView.getGrid().setItems(eventoService.findAll());
//        }
	}

	public EventosView getEventosView() {
		return eventosView;
	}

	public void setEventosView(EventosView eventosView) {
		this.eventosView = eventosView;
	}

	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}


}
