package com.tp.proyecto1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {

	private ReservaForm reservaForm;

	private ReservaService service;

	private Viaje viaje;

	@Autowired
	public ReservaFormController(ReservaService service, Viaje viaje) { 
		this.service = service;
		this.viaje = viaje;
		this.reservaForm = new ReservaForm(viaje);
		setListeners();
	}
	
	public ReservaForm getReservaForm() {
		return reservaForm;
	}
	
    private void setListeners() {
        reservaForm.getBtnSave().addClickListener(e-> guardarReserva());
        reservaForm.getBtnCancel().addClickListener(e->reservaForm.close());
    }

	
	/* Se debe permitir reservar el pasaje sin necesidad de pagar,  
	 * pero avisando al cliente que debe pagar al menos el 30% 
	 * del valor total antes que finalice la fecha de la reserva.
	 * 
	 * El resto se debe pagar a lo sumo 5 días antes del viaje.
	 */
	public void guardarReserva() {
		
	}
	
	
	public void guardarBorrador() {
		
	}
	
	public void emitirComprobante() {
		
	}
	
	public void enviarMail() {
		
	}
	
	public void modificarReserva() {
		
	}
}
