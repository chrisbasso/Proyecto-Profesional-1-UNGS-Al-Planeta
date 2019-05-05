package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.views.clientes.ClientesSearch;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {

	private ReservaForm reservaForm;
	private ReservaService service;
	private Viaje viaje;	
	private Reserva reserva;
	@Autowired
	private ClienteService clienteService;

	public ReservaFormController(ReservaService service, Viaje viaje) { 
		this.service = service;
		this.viaje = viaje;
		reservaForm = new ReservaForm(viaje);
		reserva = new Reserva();
		setListeners();
	}
	
	public ReservaForm getReservaForm() {
		return reservaForm;
	}
	
    private void setListeners() {
        reservaForm.getBtnCliente().addClickListener(e-> buscarCliente());
        reservaForm.getBtnSave().addClickListener(e-> guardarReserva(reservaForm.getCliente()));
        reservaForm.getBtnCancel().addClickListener(e->reservaForm.close());
    }

	private void buscarCliente() {		
		ClientesSearch cs = new ClientesSearch();
		cs.open();
	}
	/* Se debe permitir reservar el pasaje sin necesidad de pagar,  
	 * pero avisando al cliente que debe pagar al menos el 30% 
	 * del valor total antes que finalice la fecha de la reserva.
	 * 
	 * El resto se debe pagar a lo sumo 5 días antes del viaje.
	 */
	private void guardarReserva(Cliente cliente) {
		if(!verificarCliente(cliente)) {
			//TODO mensaje de cliente no válido
		}
		
		if(controlarFechaViaje()) {
			reserva.setCliente(cliente);
		}
	}
	
	private boolean verificarCliente(Cliente cliente) {
		return clienteService.findById(cliente.getId()).isPresent();		
	}
	
	private boolean controlarFechaViaje() {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();
		return fechaViaje.minusDays(5).isBefore(presente);		
	}
	
	private boolean controlarCliente() {
		
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
