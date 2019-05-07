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
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {
	private static String MSJ_RESERVA_GUARDADA = "Reserva guardada con éxito.";
	private static String MSJ_ERROR_FECHA = "Por la fecha del viaje solo es posible comprar.";

    private ChangeHandler changeHandler;
	private ReservaForm reservaForm;
	private Viaje viaje;	
	private Reserva reserva;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private VentaService ventaService;
	@Autowired
	private ViajeService viajeService;
	@Autowired
	private ConfiguracionService ConfigService;
	
	public ReservaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;	
	}

	public ReservaForm getReservaForm() {
		reservaForm = new ReservaForm(viaje);
		reservaForm.getComboCliente().setItems(clienteService.findAll());
		reservaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		setListeners();
		return reservaForm;
	}
	
	public boolean esReservablePorFecha() {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();
		int fecha_maxima = Integer.parseInt(ConfigService.findValueByKey("reserva_fecha_maxima"));
		return fechaViaje.minusDays(fecha_maxima).isAfter(presente);		
	}
	
    private void setListeners() {
        reservaForm.getBtnSave().addClickListener(e-> guardarReserva(reservaForm.getClienteSeleccionado(),reservaForm.getFormaPagoSeleccionada(),reservaForm.getPago().getValue()));
        reservaForm.getBtnCancel().addClickListener(e->reservaForm.close());
        reservaForm.getCantidadPasajes().addValueChangeListener(e->actualizarPrecioTotal());
        reservaForm.getPago().addValueChangeListener(e->actualizarPago());
    }

	/* Se debe permitir reservar el pasaje sin necesidad de pagar,  
	 * pero avisando al cliente que debe pagar al menos el 30% 
	 * del valor total antes que finalice la fecha de la reserva.
	 * 
	 * El resto se debe pagar a lo sumo 5 días antes del viaje.
	 */
	private void guardarReserva(Cliente cliente, FormaDePago fdp, Double pagado) {
			if(esReservablePorFecha()) {
				reserva = new Reserva(viaje, cliente);
//				if(pagado != null && pagado>0) {
//					Pago pago = new Pago(cliente, reserva, fdp, pagado, LocalDate.now());
//					reserva.agregarPago(pago);
//				}
				reservaService.save(reserva);
				Long idGuardada = reservaService.findReservaId(reserva);
				if(idGuardada > -1) {
					Notification.show(MSJ_RESERVA_GUARDADA + "\n Número de reserva: " + idGuardada.toString());	
				}else {
					Notification.show(MSJ_RESERVA_GUARDADA); 
				}
				actualizarCapacidadTransporte();
				reservaForm.close();
			}else {
				Notification.show(MSJ_ERROR_FECHA);
				reservaForm.close();
			}
	}
	
	private void actualizarCapacidadTransporte() {
		double capacidadTransporte = Double.parseDouble(viaje.getTransporte().getCapacidad());
		double pasajesReservados = reservaForm.getCantidadPasajes().getValue();
		double saldoCapacidad = capacidadTransporte - pasajesReservados;		
		viaje.getTransporte().setCapacidad(Double.toString(saldoCapacidad));
		viajeService.save(viaje);
	}

	private void actualizarPrecioTotal(){
		double pasajes = reservaForm.getCantidadPasajes().getValue();
		double precioTotal = viaje.getPrecio() * pasajes;
		reservaForm.getPrecioTotal().setReadOnly(false);
		reservaForm.getPrecioTotal().setValue(precioTotal);
		reservaForm.getPrecioTotal().setReadOnly(true);		
	}
	
	private void actualizarPago(){
		if(reservaForm.getPrecioTotal().getValue() < reservaForm.getPago().getValue()) {
			Notification.show("El total a pagar es menor que el pago ingresado.");
			reservaForm.getPago().setReadOnly(false);
			reservaForm.getPago().setValue(0.0);
			reservaForm.getPago().setReadOnly(true);
		}else {
			reservaForm.getSaldoPagar().setReadOnly(false);
			reservaForm.getSaldoPagar().setValue(reservaForm.getPrecioTotal().getValue() - reservaForm.getPago().getValue());
			reservaForm.getSaldoPagar().setReadOnly(true);	
		}		
	}

	private void guardarBorrador() {
		
	}
	
	private void emitirComprobante() {
		
	}
	
	private void enviarMail() {
		
	}
	
	private void modificarReserva() {
		
	}
	
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}