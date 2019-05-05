package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Venta;
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

	private Binder<Venta> binderVenta;
	private Binder<Viaje> binderViaje;  //NO LOS USO
	private Binder<Cliente> binderCliente;//NO LOS USO

	public VentaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;
		this.ventaForm = new VentaForm(viaje);
		setListeners();
        setComponents();
       // setBinders(); no lo uso!!
	}
	
	private void setComponents() {
		ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		ventaForm.getCliente().setItems(clienteService.findAll());
	}
	
	private void setListeners() {
		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta(venta));//en el modo compra pasajes de viajes, 
		ventaForm.getCantidadPasaje().addValueChangeListener(e->modificarSaldoaPagar());
	}
	
	//Incrementa o decrementa los campos de saldo a pagar y subtotal dependiendo la cant. de pasajeros
	private void modificarSaldoaPagar() {
		Double precio = viaje.getPrecio();
		ventaForm.getSaldoPagar().setValue(precio * ventaForm.getCantidadPasaje().getValue());
		ventaForm.getSubtotal().setValue(precio * ventaForm.getCantidadPasaje().getValue());
		
	}
	
//una venta realizada desde el boton comprar de viajes
	private void newVenta(Venta venta) {
		if (venta == null) {
			venta = setNewVenta(true);
		}		
        ventaService.save(venta);
         
        //GENERA LOS PASAJES RESTANTEs, SOLO EL PRIMER PASAJE VA A ESTAR LINKEADO AL PAGO, VER ESTO DE DEJARLO ASI O NO, ES MEDIO CHOTO
        for(int i = 1; i < ventaForm.getCantidadPasaje().getValue();i++) {
        	venta = setNewVenta(false);
        	ventaService.save(venta); 
        }
        
        //se resta la cantidad de pasajes seleccionados a la capacidad del transporte
        restarCapacidadTransporte();
        
        ventaForm.close();
        
        //agregar el lote de puntos con el id del cliente(calculo de puntos)cada 1000, 100 ptos para la  PROX VERSIOON O LA DE PUNTOS!!!!!!!!!!!!!!!!!!!
        Notification.show("Venta Guardada");
		
	}

	private void restarCapacidadTransporte() {
		Integer capacidadTransporte = Integer.parseInt(viaje.getTransporte().getCapacidad());
		Integer cantPasajes = ventaForm.getCantidadPasaje().getValue().intValue();
		Integer capacidadActual = capacidadTransporte - cantPasajes;
		
		viaje.getTransporte().setCapacidad(capacidadActual.toString());
		viajeService.save(viaje);
		
	}

	//modificacion desde la lista de ventas
	private void saveVenta(Venta venta) {
		if (binderVenta.writeBeanIfValid(venta) ) {
            ventaService.save(venta);
            ventaForm.close();
            //agregar el lote de puntos con el id del cliente(calculo de puntos)cada 1000, 100 ptos para la  PROX VERSIOON O LA DE PUNTOS!!!!!!!!!!!!!!!!!
            Notification.show("Venta Guardado");
            changeHandler.onChange();
        }
	}

	private Venta setNewVenta(Boolean unPasaje) {
		Venta venta;
		if(unPasaje) {
			//el primer o unico pasaje
			Double importe = ventaForm.getSaldoPagar().getValue();
			Cliente cliente = ventaForm.getCliente().getValue();
			FormaDePago formaPago = ventaForm.getFormaPago().getValue();
	        venta = new Venta(viaje, cliente);
	             
	        Pago pago = new Pago(cliente, venta, formaPago, importe,  LocalDate.now());
	        venta.agregarPago(pago);
	        
		}
		else{
			//va a entrar hasta que genere la cant de pasajes necesarios menos uno
			Cliente cliente = ventaForm.getCliente().getValue();
	        venta = new Venta(viaje, cliente);
	        
		}        
        return venta;
	}
	
	//llevo los datos de de importe de viaje a este form, no hago binders, 
	//me resulta mas rapido esto, sé q no tengo las validaciones pero en este caso son campos en disabled
    public void setComponentsValues() {
	        ventaForm.setSaldoPagarDouble(viaje.getPrecio());  
	        ventaForm.setSubtotalDouble(viaje.getPrecio());
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