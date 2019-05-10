package com.tp.proyecto1.controllers.reserva;

import org.springframework.stereotype.Controller;

import com.tp.proyecto1.views.reserva.AgregarPagoForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class PagoFormController {

	private AgregarPagoForm agregarPagoForm;

	public PagoFormController() {
					
	}
	
	public void mostrarForm(double importeMaximo) {
		agregarPagoForm = new AgregarPagoForm(importeMaximo);
	}
	

}
