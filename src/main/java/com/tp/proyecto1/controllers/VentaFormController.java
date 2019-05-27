package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.pasajes.*;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.services.*;
import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.ventas.VentaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.*;


@Controller
@UIScope
public class VentaFormController {
	
	private VentaForm ventaForm;

	@Autowired
	private VentaService ventaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ViajeService viajeService;
	
	@Autowired
	private ReservaService reservaService;

	@Autowired
	private UserService userService;
	
	private ChangeHandler changeHandler;

	private Venta venta;

	private Viaje viaje;
	
	private Reserva reserva;
	
	private Integer cantPasajesReserva;

	private Binder<PasajeVenta> binderPasajeVenta;
	private Binder<Venta> binderVenta;
	private Binder<Viaje> binderViaje;  //NO LOS USO
	private Binder<Cliente> binderCliente;//NO LOS USO
	
	private Double pagoParcial; 
	
	public VentaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;
		this.ventaForm = new VentaForm();
		this.setListeners();
        this.setComponents();
        this.setComponentesLectura(this.viaje);
	}
	
	public VentaFormController(Reserva reserva) {
		Inject.Inject(this);
		this.reserva = reserva;
		this.ventaForm = new VentaForm();
		this.setListeners(); 
		this.setComponents();
		this.setComponentesLectura(this.reserva.getViaje()); 		
	}
	
	private void setComponentesLectura(Viaje viaje) {
		ventaForm.getPais().setItems(viajeService.findAllPaises());
		ventaForm.getPais().setValue(viaje.getDestino().getCiudad().getPais());
		ventaForm.getCiudad().setValue(viaje.getDestino().getCiudad());
		ventaForm.getCodTransporte().setValue(viaje.getTransporte().getCodTransporte());
		ventaForm.getTransporte().setValue(viaje.getTransporte().getTipo().getDescripcion());
		ventaForm.getFechaSalida().setValue(viaje.getFechaSalida().toString());
		ventaForm.getHoraSalida().setValue(viaje.getHoraSalida().toString());
		ventaForm.getPais().setReadOnly(true);
		ventaForm.getCiudad().setReadOnly(true);
		ventaForm.getCodTransporte().setReadOnly(true);
		ventaForm.getTransporte().setReadOnly(true);
		ventaForm.getFechaSalida().setReadOnly(true);
		ventaForm.getHoraSalida().setReadOnly(true);
	}

	private void setComponents() {
		ventaForm.getPais().setItems(viajeService.findAllPaises());
		ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		
		if (this.reserva != null){
			//Setear Cliente
			ventaForm.getCliente().getFiltro().setValue(reserva.getCliente().getId().toString());
			ventaForm.getCliente().getFiltro().setEnabled(false);
			
			//Setear cantidad de pasajes como uno de los obligatorio para terminar la venta
			this.cantPasajesReserva =  this.reserva.getCantidadPasajes();
			
			//Setear Forma de Pago si la hay y saldo a pagar y subtotal
			if (this.reserva.getPagos().size() == 0) { //sin pagos previos
				ventaForm.setSaldoPagarDouble(reserva.getImporteTotal());//precio al momento de la reserva
				ventaForm.setSubtotalDouble(reserva.getImporteTotal());//precio al momento de la reserva
			}
			else{//con pagos previos
				this.pagoParcial = 0.0;
				Iterator<Pago> pagosIterator = this.reserva.getPagos().iterator();
				while(pagosIterator.hasNext()){
					Pago elemento = pagosIterator.next();
					
					ventaForm.getFormaPago().setValue(elemento.getFormaDePago()); 
					
					this.pagoParcial = this.pagoParcial + elemento.getImporte();
				}
				ventaForm.setSaldoPagarDouble(reserva.getImporteTotal()-this.pagoParcial);
				ventaForm.setSubtotalDouble(reserva.getImporteTotal()-this.pagoParcial);
			}
		}
	}
	
	private void setListeners() {
		ventaForm.getPais().addValueChangeListener(e->setComboCiudades());

		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta());//en el modo compra pasajes de viajes, y para reserva venta
		ventaForm.getFormaPago().addValueChangeListener(e-> validarCompra());
		ventaForm.getCliente().getFiltro().addValueChangeListener(e-> validarCompra());
		ventaForm.getPasajerosGridComponent().getRemoveLastButton().addClickListener(e-> this.modificarSaldoaPagar());
		ventaForm.getPasajerosGridComponent().getNewPasajero().addClickListener(e-> this.modificarSaldoaPagar());
	}

	private void setComboCiudades() {

		Pais pais = ventaForm.getPais().getValue();
		ventaForm.getCiudad().setItems(pais.getCiudades());

	}
	
	//Incrementa o decrementa los campos de saldo a pagar y subtotal dependiendo la cant. de pasajeros
	private void validarCantPasajes() {
		//Si viene de una reserva
		if (this.reserva !=null) {
			if (this.cantPasajesReserva == this.ventaForm.getPasajerosGridComponent().getPasajerosList().size()) {
				ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(false);
			}
			else {
				ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(true);//este no sirve
			}
		}
		//validar que se pueda habilitar el boton de finalizar compra
		this.validarCompra();
	}
	
	private void modificarSaldoaPagar() {
		if (this.reserva ==null) {
			Double precio = viaje.getPrecio();
			ventaForm.getSaldoPagar().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
			ventaForm.getSubtotal().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());

			//validar que se pueda habilitar el boton de finalizar compra
			this.validarCompra();
		}
		else {
			this.validarCantPasajes();
		}
	}
	
	private void newVenta() {
		ventaService.save(setNewVenta());
        ventaForm.close();
      //Si viene de una reserva
        if (this.reserva !=null) {
        	this.reserva.inactivar();
            this.reservaService.save(reserva);
        }
        changeHandler.onChange();
        Notification.show("PasajeVenta comprado. Lote de puntos consequidos");		
	}

	private void saveVenta(Venta venta) {

            ventaService.save(venta);
            ventaForm.close();
            Notification.show("PasajeVenta Guardado");
            changeHandler.onChange();
	}
	
	private Venta setNewVenta() {
		Double precioTotal;
		Venta venta = new Venta();
		venta.setSucursal(Proyecto1Application.sucursal);
		venta.setVendedor(Proyecto1Application.logUser);

		Cliente cliente = ventaForm.getCliente().getCliente();
		FormaDePago formaPago = ventaForm.getFormaPago().getValue();

		venta.setCliente(cliente);
		Pago pagoVenta = new Pago();
		//Si viene de una reserva
		if (this.reserva !=null) {
			
			Pago pagoReserva = new Pago(venta, null, pagoParcial, LocalDate.now());
			venta.agregarPago(pagoReserva);
						
			pagoVenta = new Pago(venta, formaPago, ventaForm.getSaldoPagar().getValue(),  LocalDate.now());//tiro null pointer al hacer la prueba q esta en la hoja
			venta.agregarPago(pagoVenta);
			
			this.viaje = reserva.getViaje();
			
			precioTotal = reserva.getImporteTotal();
			
			venta.setEstadoTransaccion(EstadoTransaccion.VENDIDA);
		}
		else {
				pagoVenta = new Pago(venta, formaPago, viaje.getPrecio(),  LocalDate.now());//tiro null pointer al hacer la prueba q esta en la hoja
				venta.agregarPago(pagoVenta);
				
				precioTotal = ventaForm.getSaldoPagar().getValue();
				
				venta.setEstadoTransaccion(EstadoTransaccion.CREADA);
			}
		
		ventaForm.getPasajerosGridComponent().getPasajerosList().stream().forEach(pasajero->{
			PasajeVenta pasajeVenta = new PasajeVenta(viaje, cliente);
			pasajeVenta.setPasajero(pasajero);
			venta.getPasajes().add(pasajeVenta);
		});		
		viaje.restarPasajes(venta.getPasajes().size());
		venta.setViaje(viaje);
		viajeService.save(viaje);

		Integer cantPuntos =  precioTotal.intValue()/10;
		
		LotePunto  lotePunto = new LotePunto(LocalDate.now(), null, cantPuntos , true, cantPuntos, cliente);
		cliente.agregarPuntos(lotePunto);
		venta.setCliente(cliente);
		clienteService.save(cliente);
		
		venta.setImporteTotal(precioTotal);//si es el caso que viene de reserva le paso igual el importe total, sino faltaria un campo en que diga importeVenta/parcial
		
		return venta;
	}
	
	public void setComponentsValues(Venta venta) {

		this.venta = venta;

		ventaForm.getPais().setValue(venta.getViaje().getDestino().getCiudad().getPais());
		ventaForm.getCiudad().setValue(venta.getViaje().getDestino().getCiudad());
		ventaForm.getHoraSalida().setValue(venta.getViaje().getHoraSalida().toString());
		ventaForm.getFechaSalida().setValue(venta.getViaje().getFechaSalida().toString());
		ventaForm.getTransporte().setValue(venta.getViaje().getTransporte().getTipo().getDescripcion());
		ventaForm.getCodTransporte().setValue(venta.getViaje().getTransporte().getCodTransporte());
		
		if(venta.getPagos().get(0).getFormaDePago() != null){
			ventaForm.getFormaPago().setValue(venta.getPagos().get(0).getFormaDePago());
		}
		else {
			ventaForm.getFormaPago().setValue(venta.getPagos().get(1).getFormaDePago());
		}
		
		ventaForm.getSubtotal().setValue(venta.getImporteTotal());
		ventaForm.getSaldoPagar().setValue(venta.getImporteTotal());
		ventaForm.getCliente().getFiltro().setValue(venta.getCliente().getId().toString());
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		ventaForm.getPasajerosGridComponent().setPasajerosList(pasajeros);
		ventaForm.getPasajerosGridComponent().setGrid();
		ventaForm.getPasajerosGridComponent().setModoConsulta();
		ventaForm.getCliente().getFiltro().setReadOnly(true);
		ventaForm.getCliente().getSearchButton().setVisible(false);
		ventaForm.getFormaPago().setReadOnly(true);

	}
	
	private void validarCompra() {
		boolean isValido = false; 
		if(ventaForm.getFormaPago().getValue() != null && !ventaForm.getCliente().getFiltro().getValue().isEmpty()) {
			if (this.reserva != null) {
				if (this.cantPasajesReserva == ventaForm.getPasajerosGridComponent().getPasajerosList().size()) {
					isValido = true;
				}
			}
			else {
				if (ventaForm.getPasajerosGridComponent().getPasajerosList().size() > 0) {
					isValido = true;
				}
			}
		}
		ventaForm.getBtnFinalizarCompra().setEnabled(isValido);
	}
	
    //NO USO ESTA PODEROSA TECNICA
	private void setBinders() {
		binderVenta.setBean(venta);
		
	}
	
	public VentaForm getVentaFormCompra() {
		ventaForm.getBtnSave().setVisible(false);
		ventaForm.getBtnFinalizarCompra().setEnabled(false);
		return ventaForm;
	}
	
	public VentaForm getVentaReservaFormCompra() {
		ventaForm.getPasajerosGridComponent().getRemoveLastButton().setVisible(false);
		ventaForm.getBtnSave().setVisible(false);
		ventaForm.getBtnFinalizarCompra().setEnabled(false);
		return ventaForm;
	}
	
	public VentaForm getVentaFormEdit() {
		ventaForm.getBtnFinalizarCompra().setVisible(false);
		return ventaForm;
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public void setVentaForm(VentaForm ventaForm) {
		this.ventaForm = ventaForm;
	}	
}