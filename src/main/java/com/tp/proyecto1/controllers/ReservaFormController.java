package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.views.clientes.ClientesSearch;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {

	private ReservaForm reservaForm;
	private ReservaService reservaService;
	private Viaje viaje;	
	private Reserva reserva;
	private Cliente cliente;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ConfiguracionService configService;

	public ReservaFormController(ReservaService service, Viaje viaje) { 
		this.reservaService = service;
		this.viaje = viaje;
		reservaForm = new ReservaForm(viaje);
		reserva = new Reserva();
		cliente = new Cliente();
		setListeners();
	}
	
	public ReservaForm getReservaForm() {
		return reservaForm;
	}
	
    private void setListeners() {
        reservaForm.getBtnCliente().addClickListener(e-> buscarCliente());
        reservaForm.getBtnSave().addClickListener(e-> guardarReserva(reservaForm.getIdCliente()));
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
	private void guardarReserva(String id) {
		if(!verificarCliente(Long.parseLong(id))) {
			//TODO mensaje de cliente no válido
		}
		
		if(controlarFechaViaje()) {
			reserva.setCliente(cliente);
		}else {
			//TODO mensaje de fecha excedida
		}
	}
	
	private boolean verificarCliente(Long id) {
		Optional<Cliente> optCliente = clienteService.findById(id);  
		if(optCliente.isPresent()) {
			cliente = optCliente.get();
			return true;
		}
		return false;		
	}
	
	private boolean controlarFechaViaje() {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();
		int fecha_maxima = Integer.parseInt(configService.findValueByKey("reserva_fecha_maxima"));
		return fechaViaje.minusDays(fecha_maxima).isBefore(presente);		
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