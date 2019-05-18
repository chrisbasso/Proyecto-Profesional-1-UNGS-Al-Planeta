package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.tp.proyecto1.model.pasajes.*;
import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.ventas.VentaForm;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.util.StringUtils;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

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
		setListeners();
        setComponents();
        setComponentesLectura(this.viaje);
       // setBinders(); no lo uso!!
	}
	
	public VentaFormController(Reserva reserva) {
		Inject.Inject(this);
		this.reserva = reserva;
		this.ventaForm = new VentaForm();
		setListeners(); //revisar q le estoy pasando para RESERVA-> Venta ver si funciona
		setComponents();//revisar q le estoy pasando para RESERVA-> Venta ver si funciona 
		setComponentesLectura(this.reserva.getViaje()); //ver si funciona
		//Setear Cliente, encapsular luego NO PUEDO SETEAR EL CLIENTE EN ESTA FUNCION, SEGUIR CON LA PARTE DE GUARDAR Y ACOMODAT ESTOS SETS
		ventaForm.getCliente().getFiltro().setValue(reserva.getCliente().getId().toString());
		ventaForm.getCliente().getFiltro().setEnabled(false);
		
		//Setear Forma de Pago si la hay y saldo a pagar y subtotal, encapsular luego
		if (this.reserva.getPagos().size() == 0) { //sin pagos previos
			ventaForm.setSaldoPagarDouble(reserva.getImporteTotal());//precio al momento de la reserva
			ventaForm.setSubtotalDouble(reserva.getImporteTotal());//precio al momento de la reserva
//			ventaForm.setSaldoPagarDouble(reserva.getViaje().getPrecio());//o saco el precio del viaje, por si se modifico
//			ventaForm.setSubtotalDouble(reserva.getViaje().getPrecio());//o saco el precio del viaje, por si se modifico
		}
		else{//con pagos previos
//			ventaForm.setFormaPago(this.reserva.getPagos().get(1).getFormaDePago().getId().toString());
//			ventaForm.getFormaPago().setValue(venta.getPagos().get(0).getFormaDePago());
			/*ventaForm.setSaldoPagarDouble(reserva.getPagos().get(1).getImporte());
			ventaForm.setSubtotalDouble(reserva.getPagos().get(1).getImporte());
			*/
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
					
		//Setear cantidad de pasajes como obligatorio para terminar la venta
		this.cantPasajesReserva =  reserva.getCantidadPasajes();
		
	}


	private void setComponentesLectura(Viaje viaje) {
		ventaForm.getPais().setValue(viaje.getDestino().getPais());
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
		ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
	}
	
	private void setListeners() {
		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta());//en el modo compra pasajes de viajes, y para reserva venta
		ventaForm.getPasajerosGridComponent().getGrid().getEditor().addCloseListener(e->modificarSaldoaPagar());
	}
	
	//Incrementa o decrementa los campos de saldo a pagar y subtotal dependiendo la cant. de pasajeros
	private void modificarSaldoaPagar() {//pasar metodo original a otro lado y cambiar por el nuevo al que llama a getPasajeros
		if (this.reserva !=null) {//Si viene de una reserva
			if (this.cantPasajesReserva < this.ventaForm.getPasajerosGridComponent().getPasajerosList().size()) {
				ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(false);
			}
			else {
				ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(false);
			}
		}
		else{
			Double precio = viaje.getPrecio();
			ventaForm.getSaldoPagar().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
			ventaForm.getSubtotal().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
		}
		
	}
	
	private void newVenta() {

		ventaService.save(setNewVenta());
        ventaForm.close();
        if (this.reserva !=null) {//Si viene de una reserva
        	this.reserva.setActivo(false);
            this.reservaService.save(reserva);
        }
        changeHandler.onChange();
        Notification.show("PasajeVenta Guardada");		
	}

	private void saveVenta(Venta venta) {

            ventaService.save(venta);
            //reservaService.save(venta);
            ventaForm.close();
            
            //agregar el lote de puntos con el id del cliente(calculo de puntos)cada 1000, 100 ptos para la  PROX VERSIOON O LA DE PUNTOS!!!!!!!!!!!!!!!!!
            Notification.show("PasajeVenta Guardado");
            changeHandler.onChange();
	}
	
	private Venta setNewVenta() {

		Venta venta = new Venta();

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
		}
		else {
				pagoVenta = new Pago(venta, formaPago, viaje.getPrecio(),  LocalDate.now());//tiro null pointer al hacer la prueba q esta en la hoja
				venta.agregarPago(pagoVenta);
			}
		
		ventaForm.getPasajerosGridComponent().getPasajerosList().stream().forEach(pasajero->{
			PasajeVenta pasajeVenta = new PasajeVenta(viaje, cliente);
			pasajeVenta.setPasajero(pasajero);
			venta.getPasajes().add(pasajeVenta);
		});
		
		viaje.restarPasajes(venta.getPasajes().size());
		venta.setViaje(viaje);
		viajeService.save(viaje);
		
		venta.setImporteTotal(ventaForm.getSaldoPagar().getValue());

		return venta;
	}
	
	public void setComponentsValues(Venta venta) {

		this.venta = venta;

		ventaForm.getPais().setValue(venta.getViaje().getDestino().getPais());
		ventaForm.getCiudad().setValue(venta.getViaje().getDestino().getCiudad());
		ventaForm.getHoraSalida().setValue(venta.getViaje().getHoraSalida().toString());
		ventaForm.getFechaSalida().setValue(venta.getViaje().getFechaSalida().toString());
		ventaForm.getTransporte().setValue(venta.getViaje().getTransporte().getTipo().getDescripcion());
		ventaForm.getCodTransporte().setValue(venta.getViaje().getTransporte().getCodTransporte());
		ventaForm.getFormaPago().setValue(venta.getPagos().get(0).getFormaDePago());
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
	
    //NO USO ESTA PODEROSA TECNICA
	private void setBinders() {
		binderVenta.setBean(venta);
		
	}
	
	public VentaForm getVentaFormCompra() {
		ventaForm.getBtnSave().setVisible(false);
		return ventaForm;
	}
	
	public VentaForm getVentaReservaFormCompra() {
		ventaForm.getPasajerosGridComponent().getRemoveLastButton().setEnabled(false);
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