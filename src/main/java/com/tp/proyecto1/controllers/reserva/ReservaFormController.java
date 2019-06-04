package com.tp.proyecto1.controllers.reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tp.proyecto1.Proyecto1Application;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Pasaje;
import com.tp.proyecto1.model.pasajes.PasajeReserva;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.ComprobanteReserva;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservaFormController {
	@Autowired
	private ConfiguracionService ConfigService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private VentaService ventaService;
	@Autowired
	private ViajeService viajeService;
	
	private ReservaForm reservaForm;
	private PagoFormController pagoFormController;
	private Viaje viaje;	
	private Reserva reserva;
	private List <Pago> listaDePagos;
	private ChangeHandler changeHandler;
	
	public ReservaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;	
		this.listaDePagos = new ArrayList<Pago>(); 
	}	
	/*
	 *	Creación de nueva reserva 
	 */
	public ReservaForm getReservaForm() {
		reservaForm = new ReservaForm(viaje);
		setListeners();
		return reservaForm;
	}
	/*
	 * Listeners nueva reserva
	 */
    private void setListeners() {
        reservaForm.setListenerCantPasajes(e->actualizarImportes());
        reservaForm.getBuscadorCliente().getFiltro().addValueChangeListener(e->habilitarPagos());
        reservaForm.setListenerBtnNuevoPago(e->formNuevoPago());        
        reservaForm.setListenerBtnSave(e->accionGuardarReserva());
        reservaForm.setListenerBtnCancel(e->reservaForm.close());
    }
    /*
     * Actualizar todos los importes en el form ante una modificación
     */
	private void actualizarImportes(){
		reservaForm.actualizarPrecioTotal(getPrecioTotal());
		reservaForm.actualizarSaldo(getSaldo());
		reservaForm.actualizarPagos(getSumatoriaPagos());
	}
	/*
	 * Calcular el precio total según los datos del form
	 */
	private double getPrecioTotal() {
		int pasajes = reservaForm.cantidadPasajesSeleccionados();
		return viaje.getPrecio() * pasajes;
	}
	/*
	 * Sumar los pagos de la reserva
	 */
	private double getSumatoriaPagos() {
		double sumatoria = 0.0;
		if(listaDePagos.size()>0) {
			for (Pago pago : listaDePagos) {
				sumatoria += pago.getImporte();
			}
		}
		return sumatoria;
	}
	/*
	 * Calcular el saldo a pagar en funcion del precio total 
	 * y la suma de pagos
	 */
	private double getSaldo() {
		return getPrecioTotal() - getSumatoriaPagos();
	}
	/*
	 * Habilitar el botón de agregar pagos una vez que se seleccionó el cliente
	 */
	private void habilitarPagos() {
		if(!reservaForm.getBuscadorCliente().getFiltro().isEmpty()){
			reservaForm.habilitarBtnAgregarPago();
		}else{
			reservaForm.deshabilitarBtnAgregarPago();
		}
	}
	/*
	 * Accion de guardar: como el botón es el mismo desde aquí se invocan
	 * el guardado de una nueva reserva o de una modificación.
	 */
	private void accionGuardarReserva() {
		Cliente cliente = reservaForm.getClienteSeleccionado();
		Double importeTotal = reservaForm.getPrecioTotal();
		int cantidadPasajes = reservaForm.cantidadPasajesSeleccionados();
		List<Pasaje> pasajes = new ArrayList<Pasaje> ();
		for (int i = 0; i < cantidadPasajes; i++) {
			pasajes.add(new PasajeReserva(viaje, cliente));
		}
		
		if(reserva == null) {
			guardarNuevaReserva(pasajes, importeTotal, cliente);
		}else { 
			guardarReservaModificada(pasajes, importeTotal);
		}
	}
	/*
	 * Evento de guardado frente a una creación de reserva.
	 */
	private void guardarNuevaReserva(List <Pasaje> pasajes, Double importeTotal, Cliente cliente) {			
		if(viaje.getPasajesRestantes()>= pasajes.size()) {
			reserva = new Reserva(pasajes, listaDePagos, importeTotal, cliente);
			actualizarTransaccionEnPagos(reserva);
			viaje.restarPasajes(pasajes.size());
			reserva.setViaje(viaje);
			reserva.setSucursal(Proyecto1Application.sucursal);
			reserva.setVendedor(Proyecto1Application.logUser);
			reserva.setFecha(LocalDate.now());
			Long id = reservaService.save(reserva);
			viajeService.save(viaje);
			imprimirComprobante(reserva);
			mensajeGuardadoCierreForm(id);
		}else {
			Notification.show("Lo sentimos, no quedan pasajes disponibles en el viaje seleccionado.");
			reservaForm.close();
		}
	}	
	private void imprimirComprobante(Reserva reserva)
	{
		System.out.println(reserva);
		ComprobanteReserva comprobante = new ComprobanteReserva(reserva);
		comprobante.open();
		UI.getCurrent().getPage().executeJavaScript("setTimeout(function() {" +
				"  print(); self.close();}, 1000);");
	}
	/*
	 *  Iniciar el form para modificaciones de reservas
	 */
	public ReservaForm getFormModificacionReserva(Reserva reserva) {
		this.reserva = reserva;
		this.listaDePagos = reserva.getPagos();
		reservaForm = new ReservaForm(reserva.getViaje());
		reservaForm.setModoModificacion(reserva.getCantidadPasajes(), reserva.getCliente(), reserva.getTotalPagado());
		setListeners(); 
		return reservaForm;		
	}
	/*
	 * Evento de guardado frente a una modificación de reserva preexistente.
	 */
	private void guardarReservaModificada(List <Pasaje> pasajes, Double importeTotal) {
		int cambio = getCambioCantidadPasajes(pasajes);
		boolean actualizarPasajes = evaluarCambioPasajes(cambio);
		
		if(actualizarPasajes) {
			reserva.setImporteTotal(importeTotal);			
			reserva.setPasajes(pasajes);
			actualizarTransaccionEnPagos(reserva);
			reserva.setPagos(listaDePagos);
			Long id = reservaService.save(reserva);
			viajeService.save(viaje);
			mensajeGuardadoCierreForm(id);
		}else {
			Notification.show("Lo sentimos, no pudimos actualizar los pasajes disponibles en el viaje seleccionado.");
			reservaForm.close();
		}
	}
	/*
	 * Evaluar si hubo un cambio en el número de pasajes respecto de
	 * la reserva original.  
	 */
	private int getCambioCantidadPasajes(List<Pasaje> pasajes) {
		int cantidadOriginal = reserva.getCantidadPasajes();
		int nuevaCantidad = pasajes.size();
		int cambio = nuevaCantidad - cantidadOriginal;
		return cambio;
	}
	/*
	 * Evaluar si hubo modificación del número de pasajes, y si el viaje permite dicha
	 * modificación. 
	 * Si no hubo cambios o el viaje admite el cambio(disponibilidad), retorna TRUE.
	 */
	private boolean evaluarCambioPasajes(int cambio) {
		boolean actualizarPasajes = true; // En principio guardamos
		
		if(cambio == 0) {
			// Do nothing on viaje
		}else if(cambio > 0) {
			actualizarPasajes = viaje.restarPasajes(cambio); // Si no quedan pasajes disp..
		}else if(cambio < 0) {
			actualizarPasajes = viaje.agregarPasajes(cambio*-1); // Si queremos devolver..
		}
		return actualizarPasajes;
	}
	/*
	 * Gestionar el agregado de pagos a la reserva.
	 */
	private void formNuevoPago() {
		double saldoReserva = getSaldo();
		pagoFormController = new PagoFormController(this);
		if(reserva != null) {
			pagoFormController.mostrarForm(saldoReserva, ventaService.findAllFomaDePagos(),reserva.getPagos());
		}else {
			pagoFormController.mostrarForm(saldoReserva, ventaService.findAllFomaDePagos(),new ArrayList<Pago>());
		}
	}
	/*
	 * Modificar la lista de pagos, inhabilitar la selección de clientes y 
	 * actualizar los importes del form.
	 */
	public void modificarListaPagos(List <Pago> pagos) {		 
		listaDePagos = pagos;
		reservaForm.inhabilitarClientes();
		actualizarImportes();
	}
	/*
	 * Recorrer el arreglo de pagos y setearle una reserva como transacción.
	 * Me parece que se soluciona con algún cascade, pero no lo sé.
	 */
	private void actualizarTransaccionEnPagos(Reserva reserva) {
		for(Pago pago : listaDePagos) {
			pago.setTransaccion(reserva);
		}
	}
	/*
	 * No recuerdo para que lo usamos, lo dejo por si acaso.
	 */
	public void setChangeHandler(ChangeHandler ch) {
		changeHandler = ch;
	}
	/*
	 * Gestión de mensajes al cerrar el form
	 */
	private void mensajeGuardadoCierreForm(Long id) {
		mensajeReservaGuardada(id);
		mensajeSaldoViaje();
		reservaForm.close();
	}
	/*
	 * Intentar recuperar el nro de reserva generado en la BD
	 */
	private void mensajeReservaGuardada(Long id) {
		Notification.show("Reserva guardada con éxito." + "\n Número de reserva: " + id.toString());	
	}
	/*
	 * Recuperar fecha máxima de la configuración y mostrar el mensaje del saldo
	 * a pagar. Se puede reservar sin pagar pero debe cancelar el 30% antes de 
	 * la fecha máxima.
	 */
	private void mensajeSaldoViaje() {
		double pago = getSumatoriaPagos();
		double porcentaje = reservaForm.getPrecioTotal() * 0.3; 
		String fechaMaxima = getConfiguracionFechaMaxima();
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
	/*
	 * Evaluar si un viaje es reservable según la configuración de fecha
	 * máxima.
	 */
	public static boolean esReservablePorFecha(Viaje viaje) {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();		
		String fechaMaxima = getConfiguracionFechaMaxima();
		if(fechaMaxima != null) {
			int fecha_maxima = Integer.parseInt(fechaMaxima);
			return fechaViaje.minusDays(fecha_maxima).isAfter(presente);
		}
		// Si no hay configuración de fecha maxima no interrumpo la 
		// operación comercial.
		return true; 
	}
	/*
	 * Recuperar información de la configuración de reservas.
	 * Intenté declarar el ConfigService como static pero no funciona
	 * el autowired.
	 */
	private static String getConfiguracionFechaMaxima() {
		ReservaFormController dummy = new ReservaFormController(null);
		String fechaMaxima = dummy.getFechaMaximaConfigurada(); 
		return fechaMaxima;
	}
	
	private String getFechaMaximaConfigurada() {
		return ConfigService.findValueByKey("reserva_fecha_maxima");
	}
}