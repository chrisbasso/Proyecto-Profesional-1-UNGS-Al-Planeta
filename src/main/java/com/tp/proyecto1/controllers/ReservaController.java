package com.tp.proyecto1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.views.reserva.ReservaFormView;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaController {

	private ReservaFormView view;

	private ReservaService service;

	private Viaje viaje;

	@Autowired
	public ReservaController(ReservaService service, Viaje viaje) {
		this.view = new ReservaFormView();
		this.service = service;
		this.viaje = viaje;
	}
	
	public ReservaFormView getView() {
		return view;
	}

}
