package com.tp.proyecto1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.views.reserva.ReservaView;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaController {

	private ReservaView view;

	private ReservaService service;


	@Autowired
	public ReservaController(ReservaService service) {
		this.view = new ReservaView();
		this.service = service;
		

	}

}
