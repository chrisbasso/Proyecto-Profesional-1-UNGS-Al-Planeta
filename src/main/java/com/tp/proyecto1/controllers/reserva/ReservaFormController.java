package com.tp.proyecto1.controllers.reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.PasajeVenta;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.AgregarPagoForm;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {

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
	@Autowired
	private PagoFormController pagoFormController;
	private ChangeHandler changeHandler;
	
	public ReservaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;	
	}

	public ReservaForm getReservaForm() {
		reservaForm = new ReservaForm(viaje);
		reservaForm.cargarClientes(clienteService.findAll());
		reservaForm.cargarFormasDePago(ventaService.findAllFomaDePagos());
		setListeners();
		return reservaForm;
	}
	
    private void setListeners() {
        reservaForm.listenerCantPasajes(e->actualizarPrecioTotal());
        reservaForm.getPago().addValueChangeListener(e->actualizarPago());        
        reservaForm.getBtnSave().addClickListener(e->guardarReserva());
        reservaForm.getBtnCancel().addClickListener(e->reservaForm.close());
    }
    
	private void actualizarPrecioTotal(){
		int pasajes = reservaForm.cantidadPasajesSeleccionados();
		double precioTotal = viaje.getPrecio() * pasajes;		
		reservaForm.actualizarPrecioTotal(precioTotal);
		reservaForm.actualizarSaldo();
		actualizarPago(); //Caso del usuario jugando*
	}

	private void actualizarPago(){
		if(reservaForm.pagoEsMayorQueTotal()) {
			Notification.show("El total a pagar es menor que el pago ingresado.");
			reservaForm.reiniciarPagoIngresado();
		}else {
			reservaForm.actualizarSaldo();
		}
	}

	private void guardarReserva() {
		Cliente cliente = reservaForm.getClienteSeleccionado();
		FormaDePago fdp = reservaForm.getFormaPagoSeleccionada();
		Double pagado = reservaForm.getPagoIngresado();
		Double importeTotal = reservaForm.getPrecioTotal();
		int cantidadPasajes = reservaForm.cantidadPasajesSeleccionados();
		
		if(esReservablePorFecha()) {
			List<PasajeVenta> pasajes = new ArrayList<PasajeVenta> ();
			for (int i = 0; i < cantidadPasajes; i++) {
				pasajes.add(new PasajeVenta());
			}
			
			List<Pago> pagos = new ArrayList<Pago> ();
			reserva = new Reserva(pasajes, pagos, importeTotal, cliente);
			// Se debe permitir reservar el pasaje sin necesidad de pagar
			if(pagado != null && pagado>0) {
				Pago pago = new Pago(cliente, reserva, fdp, pagado, LocalDate.now());
				pagos.add(pago);
			}
			reservaService.save(reserva);
			mensajeReservaGuardada();
			actualizarCapacidadTransporte();
			reservaForm.close();
		}else {
			Notification.show("Por la fecha del viaje solo es posible comprar.");
			reservaForm.close();
		}
	}

	private void mensajeReservaGuardada() {
		Long idGuardada = reservaService.findReservaId(reserva);
		if(idGuardada > -1) {
			Notification.show("Reserva guardada con éxito." + "\n Número de reserva: " + idGuardada.toString());	
		}else {
			Notification.show("Reserva guardada con éxito."); 
		}
		// pero avisando al cliente que debe pagar al menos el 30%
		// del valor total antes que finalice la fecha de la reserva.
		double pago = reservaForm.getPago().getValue();
		double porcentaje = reservaForm.getPrecioTotal() * 0.3; 
		String fechaMaxima = ConfigService.findValueByKey("reserva_fecha_maxima");
		if(pago < porcentaje) {
			if (fechaMaxima != null) {
				Notification.show("Recuerde que debe abonar $" + porcentaje + 
						" antes de la fecha fin de la reserva, que es " +
						fechaMaxima + " días antes de la fecha del viaje.");	
			}else {
				Notification.show("Recuerde que debe abonar $" + porcentaje + 
						" antes de la fecha fin de la reserva.");
			}			
		}
	}
		
	private void actualizarCapacidadTransporte() {
		double capacidadTransporte = Double.parseDouble(viaje.getTransporte().getCapacidad());
		double pasajesReservados = reservaForm.cantidadPasajesSeleccionados();
		double saldoCapacidad = capacidadTransporte - pasajesReservados;		
		viaje.getTransporte().setCapacidad(Double.toString(saldoCapacidad));
		viajeService.save(viaje);
	}
	
	public ReservaForm formModificacionReserva(Reserva reserva) {
		reservaForm = new ReservaForm(reserva.getViaje());
		reservaForm.setModoModificacion(reserva.getPasajes().size(), reserva.getCliente(), reserva.getImporteTotal());
		setListeners();
		return reservaForm;		
	}
	
	public void agregarPago() {
		double saldoReserva = 0.0;
		pagoFormController.mostrarForm(saldoReserva);
	}
	
	private void emitirComprobante() {
		
	}
	
	private void enviarMail() {
		
	}
	
	public boolean esReservablePorFecha() {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();		
		String fechaMaxima = ConfigService.findValueByKey("reserva_fecha_maxima");
		if(fechaMaxima != null) {
			int fecha_maxima = Integer.parseInt(fechaMaxima);
			return fechaViaje.minusDays(fecha_maxima).isAfter(presente);
		}
		// Si no hay configuración de fecha maxima no interrumpo la 
		// operación comercial.
		return true; 
	}
	
	public void setChangeHandler(ChangeHandler ch) {
		changeHandler = ch;
	}
}