package com.tp.proyecto1.controllers;

import java.time.LocalDate;

import com.tp.proyecto1.model.pasajes.*;
import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;
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
	
	private ChangeHandler changeHandler;

	private Venta venta;

	private Viaje viaje;

	private Binder<PasajeVenta> binderPasajeVenta;
	private Binder<Venta> binderVenta;
	private Binder<Viaje> binderViaje;  //NO LOS USO
	private Binder<Cliente> binderCliente;//NO LOS USO

	public VentaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;
		this.ventaForm = new VentaForm();
		setListeners();
        setComponents();
        setComponentesLectura();
       // setBinders(); no lo uso!!
	}

	private void setComponentesLectura() {
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
		ventaForm.getCliente().setItems(clienteService.findAll());
	}
	
	private void setListeners() {
		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta());//en el modo compra pasajes de viajes,
		ventaForm.getPasajerosGridComponent().getGrid().getEditor().addCloseListener(e->modificarSaldoaPagar());
	}
	
	//Incrementa o decrementa los campos de saldo a pagar y subtotal dependiendo la cant. de pasajeros
	private void modificarSaldoaPagar() {
		Double precio = viaje.getPrecio();
		ventaForm.getSaldoPagar().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
		ventaForm.getSubtotal().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
	}
	
	private void newVenta() {

		ventaService.save(setNewVenta());
        restarCapacidadTransporte();
        ventaForm.close();
        Notification.show("PasajeVenta Guardada");
		
	}

	private void restarCapacidadTransporte() {
		Integer capacidadTransporte = Integer.parseInt(viaje.getTransporte().getCapacidad());
		Integer cantPasajes = ventaForm.getPasajerosGridComponent().getPasajerosList().size();
		Integer capacidadActual = capacidadTransporte - cantPasajes;
		
		viaje.getTransporte().setCapacidad(capacidadActual.toString());
		viajeService.save(viaje);
	}

	private void saveVenta(Venta venta) {
		if (binderVenta.writeBeanIfValid(venta) ) {
            ventaService.save(venta);
            ventaForm.close();
            //agregar el lote de puntos con el id del cliente(calculo de puntos)cada 1000, 100 ptos para la  PROX VERSIOON O LA DE PUNTOS!!!!!!!!!!!!!!!!!
            Notification.show("PasajeVenta Guardado");
            changeHandler.onChange();
        }
	}

	private Venta setNewVenta() {

		Venta venta = new Venta();

		Cliente cliente = ventaForm.getCliente().getValue();
		FormaDePago formaPago = ventaForm.getFormaPago().getValue();

		ventaForm.getPasajerosGridComponent().getPasajerosList().stream().forEach(pasajero->{
			PasajeVenta pasajeVenta = new PasajeVenta(viaje, cliente);
			pasajeVenta.setPasajero(pasajero);
			venta.getPasajes().add(pasajeVenta);
		});

		venta.setCliente(cliente);

		Pago pago = new Pago(cliente, venta, formaPago, viaje.getPrecio(),  LocalDate.now());
		venta.getPagos().add(pago);
		venta.setViaje(viaje);

		venta.setImporteTotal(ventaForm.getSaldoPagar().getValue());

		return venta;
	}
	
	//llevo los datos de de importe de viaje a este form, no hago binders, 
	//me resulta mas rapido esto, sé q no tengo las validaciones pero en este caso son campos en disabled
    public void setComponentsValues() {
//	        ventaForm.setSaldoPagarDouble(viaje.getPrecio());
//	        ventaForm.setSubtotalDouble(viaje.getPrecio());
    }
	
    //NO USO ESTA PODEROSA TECNICA
	private void setBinders() {
		binderVenta.setBean(venta);
		
	}
	
	public VentaForm getVentaFormCompra() {
		ventaForm.getBtnSave().setVisible(false);
		return ventaForm;
	}
	
	public VentaForm getVentaFormEdit() {
		ventaForm.getBtnFinalizarCompra().setVisible(false);
		return ventaForm;
	}

	public void setVentaForm(VentaForm ventaForm) {
		this.ventaForm = ventaForm;
	}	
}