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
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {

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

	private ReservaForm reservaForm;
	private Viaje viaje;	
	private Reserva reserva;
	private PagoFormController pagoFormController;
	private ArrayList <Pago> listaDePagos;
	private ChangeHandler changeHandler;
	
	public ReservaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;	
		this.listaDePagos = new ArrayList<Pago>(); 
	}

	public ReservaForm getReservaForm() {
		reservaForm = new ReservaForm(viaje);
		reservaForm.cargarClientes(clienteService.findAll());
		setListeners();
		return reservaForm;
	}
	
    private void setListeners() {
        reservaForm.setListenerCantPasajes(e->actualizarImportes());
        reservaForm.setListenerCliente(e->habilitarPagos());
        reservaForm.setListenerBtnNuevoPago(e->formNuevoPago());        
        reservaForm.setListenerBtnSave(e->guardarReserva());
        reservaForm.setListenerBtnCancel(e->reservaForm.close());
    }
    
	private void actualizarImportes(){
		reservaForm.actualizarPrecioTotal(getPrecioTotal());
		reservaForm.actualizarSaldo(getSaldo());
		reservaForm.actualizarPagos(getSumatoriaPagos());
	}
	
	private double getPrecioTotal() {
		int pasajes = reservaForm.cantidadPasajesSeleccionados();
		return viaje.getPrecio() * pasajes;
	}
	
	private double getSumatoriaPagos() {
		double sumatoria = 0.0;
		if(listaDePagos.size()>0) {
			for (Pago pago : listaDePagos) {
				sumatoria += pago.getImporte();
			}
		}
		return sumatoria;
	}
	
	private double getSaldo() {
		return getPrecioTotal() - getSumatoriaPagos();
	}
	
	private void habilitarPagos() {
		reservaForm.habilitarBtnAgregarPago();
	}
	
	private void guardarReserva() {
		if(esReservablePorFecha()) {
			Cliente cliente = reservaForm.getClienteSeleccionado();
			Double importeTotal = reservaForm.getPrecioTotal();
			int cantidadPasajes = reservaForm.cantidadPasajesSeleccionados();			
			List<PasajeVenta> pasajes = new ArrayList<PasajeVenta> ();
			for (int i = 0; i < cantidadPasajes; i++) {
				pasajes.add(new PasajeVenta());
			}			
			reserva = new Reserva(pasajes, listaDePagos, importeTotal, cliente);
			reservaService.save(reserva);
			mensajeReservaGuardada();
			mensajeSaldoViaje(); 
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
	}
	
	private void mensajeSaldoViaje() {
		// pero avisando al cliente que debe pagar al menos el 30%
		// del valor total antes que finalice la fecha de la reserva.
		double pago = getSumatoriaPagos();
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
		Double capacidadTransporte = viaje.getTransporte().getCapacidad().doubleValue();
		Double pasajesReservados = reservaForm.cantidadPasajesSeleccionados().doubleValue();
		Double saldoCapacidad = capacidadTransporte - pasajesReservados;
		viaje.getTransporte().setCapacidad(saldoCapacidad.intValue());
		viajeService.save(viaje);
	}
	
	private void formNuevoPago() {
		double saldoReserva = getSaldo();
		pagoFormController = new PagoFormController(this);
		pagoFormController.mostrarForm(saldoReserva, ventaService.findAllFomaDePagos());
	}
	
	public void agregarPago(FormaDePago fdp, double importe) {
		Cliente cliente = reservaForm.getClienteSeleccionado();
		Pago nuevoPago = new Pago(cliente, null, fdp, importe, LocalDate.now()); 
		listaDePagos.add(nuevoPago);
		reservaForm.inhabilitarClientes();
		actualizarImportes();
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
	
	public ReservaForm getFormModificacionReserva(Reserva reserva) {
		reservaForm = new ReservaForm(reserva.getViaje());
		reservaForm.setModoModificacion(reserva.getPasajes().size(), reserva.getCliente(), reserva.getImporteTotal());
		setListeners();
		return reservaForm;		
	}

	public void setChangeHandler(ChangeHandler ch) {
		changeHandler = ch;
	}
}