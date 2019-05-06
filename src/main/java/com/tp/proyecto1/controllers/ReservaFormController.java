package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {
//  Se comenta el codigo ya que cambio a combo seleccionable para cliente	
//	private static String MSJ_CLIENTE_INEXISTENTE = "El código de cliente ingresado no existe en la base de datos.";
	private static String MSJ_RESERVA_GUARDADA = "Reserva guardada con éxito.";
	private static String MSJ_ERROR_FECHA = "Por la fecha del viaje solo es posible comprar.";
	@Autowired
	private static ConfiguracionService staticConfigService; 

	private ReservaForm reservaForm;
	private ReservaService reservaService;
	private Viaje viaje;	
	private Reserva reserva;
	private Dialog mensaje;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private VentaService ventaService;

	public ReservaFormController(ReservaService service, Viaje viaje) { 
		this.reservaService = service;
		this.viaje = viaje;
		reservaForm = new ReservaForm(viaje);
		reservaForm.getComboCliente().setItems(clienteService.findAll());
		reservaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		mensaje = new Dialog();
		setListeners();
	}

	public ReservaForm getReservaForm() {
		return reservaForm;
	}
	
    private void setListeners() {
//        reservaForm.getBtnBuscarCliente().addClickListener(e-> buscarCliente());
        reservaForm.getBtnSave().addClickListener(e-> guardarReserva(reservaForm.getClienteSeleccionado(),reservaForm.getFormaPagoSeleccionada(),reservaForm.getPago().getValue()));
        reservaForm.getBtnCancel().addClickListener(e->reservaForm.close());
        reservaForm.getCantidadPasajes().addValueChangeListener(e->actualizarPrecioTotal());
    }

//	private void buscarCliente() {		
//		ClientesSearch cs = new ClientesSearch();
//		cs.open();
//	}
    
	/* Se debe permitir reservar el pasaje sin necesidad de pagar,  
	 * pero avisando al cliente que debe pagar al menos el 30% 
	 * del valor total antes que finalice la fecha de la reserva.
	 * 
	 * El resto se debe pagar a lo sumo 5 días antes del viaje.
	 */
	private void guardarReserva(Cliente cliente, FormaDePago fdp, Double pagado) {
//		if(!verificarCliente(Long.parseLong(id))) {			
//			mensaje.add(MSJ_CLIENTE_INEXISTENTE);
//			mensaje.open();
//		}else {		
			if(esReservablePorFecha(viaje)) {
				reserva = new Reserva(viaje, cliente);
				Pago pago = new Pago(cliente, reserva, fdp, pagado, LocalDate.now());
				reserva.agregarPago(pago);
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
//		}
	}
	
//	private boolean verificarCliente(Long id) {
//		Optional<Cliente> optCliente = clienteService.findById(id);  
//		if(optCliente.isPresent()) {
//			cliente = optCliente.get();
//			return true;
//		}
//		return false;		
//	}
	
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
	
	public static boolean esReservablePorFecha(Viaje viaje) {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();		
		int fecha_maxima = Integer.parseInt(staticConfigService.findValueByKey("reserva_fecha_maxima"));
		return fechaViaje.minusDays(fecha_maxima).isBefore(presente);		
	}	
}