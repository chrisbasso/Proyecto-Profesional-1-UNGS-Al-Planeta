package com.tp.proyecto1.controllers;


import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.EventosView;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Controller;


@Controller
@UIScope
public class EventosController {

	private EventosView eventosView;

	private ChangeHandler changeHandler;

	public EventosController() {
		Inject.Inject(this);
		this.eventosView = new EventosView();
		setListeners();
	}

	private void setListeners() {
		eventosView.getNewConsultaButton().addClickListener(e->openConsultaForm());
	}

	private void openConsultaForm() {
		ConsultaFormController consultaFormController = new ConsultaFormController();
		consultaFormController.getView().open();
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
