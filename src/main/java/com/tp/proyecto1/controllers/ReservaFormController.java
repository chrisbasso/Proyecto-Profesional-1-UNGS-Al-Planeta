package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.views.clientes.ClientesSearch;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {
	
	private static String MSJ_CLIENTE_INEXISTENTE = "El código de cliente ingresado no existe en la base de datos.";
	private static String MSJ_RESERVA_GUARDADA = "Reserva guardada con éxito.";
	private static String MSJ_ERROR_FECHA = "Por la fecha del viaje solo es posible comprar.";
	
	private ReservaForm reservaForm;
	private ReservaService reservaService;
	private Viaje viaje;	
	private Reserva reserva;
	private Cliente cliente;
	private Dialog mensaje;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ConfiguracionService configService;

	public ReservaFormController(ReservaService service, Viaje viaje) { 
		this.reservaService = service;
		this.viaje = viaje;
		reservaForm = new ReservaForm(viaje);
		mensaje = new Dialog();
		setListeners();
	}
	
	public ReservaForm getReservaForm() {
		return reservaForm;
	}
	
    private void setListeners() {
        reservaForm.getBtnBuscarCliente().addClickListener(e-> buscarCliente());
        reservaForm.getBtnSave().addClickListener(e-> guardarReserva(reservaForm.getIdCliente()));
        reservaForm.getBtnCancel().addClickListener(e->reservaForm.close());
        reservaForm.getCantidadPasajes().addValueChangeListener(e->actualizarPrecioTotal());
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
			mensaje.add(MSJ_CLIENTE_INEXISTENTE);
			mensaje.open();
		}else {		
			if(controlarFechaViaje()) {
				reserva = new Reserva(viaje, cliente);				
				reservaService.save(reserva);
				Long idGuardada = reservaService.findReservaId(reserva);
				mensaje.add(MSJ_RESERVA_GUARDADA + "\n Número de reserva: " + idGuardada.toString());
				mensaje.open();
				while(mensaje.isOpened()) {
					/*	Esperamos que se cierre el mensaje
					 * 	antes de cerrar el form de reserva
					 */					
				}
				reservaForm.close();
			}else {
				mensaje.add(MSJ_ERROR_FECHA);
				mensaje.open();
				while(mensaje.isOpened()) {
					/*	Esperamos que se cierre el mensaje
					 * 	antes de cerrar el form de reserva
					 */
					
				}
				reservaForm.close();
			}
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
	
	private void actualizarPrecioTotal(){
		double pasajes = reservaForm.getCantidadPasajes().getValue();
		double precioTotal = viaje.getPrecio() * pasajes;
		reservaForm.getPrecioTotal().setReadOnly(false);
		reservaForm.getPrecioTotal().setValue(precioTotal);
		reservaForm.getPrecioTotal().setReadOnly(true);		
	}
	
	private void guardarBorrador() {
		
	}
	
	private void emitirComprobante() {
		
	}
	
	private void enviarMail() {
		
	}
	
	private void modificarReserva() {
		
	}
}