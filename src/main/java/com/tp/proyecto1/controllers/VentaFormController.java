package com.tp.proyecto1.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.ventas.VentaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.*;

@Controller
@UIScope
public class VentaFormController {
	
	private VentaForm ventaForm;
	
	private VentaService ventaService;
	
	private ChangeHandler changeHandler;

	private Venta venta;

	private Binder binderVenta;

	@Autowired
	public VentaFormController(VentaService ventaService,  Viaje viaje) {
		this.ventaForm = new VentaForm(viaje);
		this.ventaService = ventaService;
		setListeners();
        setComponents();
        setBinders();
	}
	//Seteo los componentes a utilizar
	private void setComponents() {
		//ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos()); AREGLAR!!!!!!!!!11		
	}
	
	private void setListeners() {
		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta(venta));//en el modo compra pasajes de viajes, 
				//Hacer de que alguna manera sea invisible en modo edit e igual con el boton save a la hora de comprar!!!
	}
	
//si es una venta desplegada desde el boton comprar
	private void newVenta(Venta venta) {
		if (venta == null) {
			venta = setNewVenta();
		}
		
	}

	private void saveVenta(Venta venta) {
		if (binderVenta.writeBeanIfValid(venta)) {
            ventaService.save(venta);
            ventaForm.close();
            //agregar el lote de puntos con el id del cliente(calculo de puntos)cada 1000, 100 ptos??!!!!!!!!!!!!!!!!!!!
            Notification.show("Venta Guardado");
            changeHandler.onChange();
        }
		
	}

	private Venta setNewVenta() {
		
		//PASAR INFO NECESARIA DESPUES COMO INFO VISUAL
//		String pais = viajeForm.getPais().getValue();
//        String ciudad = viajeForm.getCiudad().getValue();
//        LocalDate fechaSalida = viajeForm.getFechaSalida().getValue();
//        LocalTime horaSalida = viajeForm.getHoraSalida().getValue();
//        LocalDate fechaLlegada = viajeForm.getFechaLlegada().getValue();
//        LocalTime horaLlegada = viajeForm.getHoraLlegada().getValue();
//        TipoTransporte tipoTransporte = viajeForm.getTransporte().getValue();
//        String codTransporte = viajeForm.getCodTransporte().getValue();
//        String clase = viajeForm.getClase().getValue();
//        String capacidad = viajeForm.getCapacidad().getValue();
//        Double precio = viajeForm.getPrecio().getValue();
//        TagDestino tagDestino = viajeForm.getTagDestino().getValue();
//        String descipcion = viajeForm.getTextAreaDescripcion().getValue();
//        String recomendacion = viajeForm.getTextAreaRecomendaciones().getValue();

//        Transporte transporte = new Transporte(codTransporte,tipoTransporte, capacidad, clase);
//        Destino destino = new Destino(ciudad, pais, recomendacion, tagDestino);
		String nroDeCliente = ventaForm.getNroCliente().getValue();
		FormaDePago formaPago = ventaForm.getFormaPago().getValue();
		Date fechaPago = new Date();  //revisar si sale fecha actual y darle formato aceptable
		Viaje viaje = new Viaje();//ver la maneera de pasarle el viaje posta seleccionado 
		Cliente cliente = new Cliente(); //ver la maneera de pasarle el cliente posta seleccionado, seguro con el nro de cliente realizar la busqueda y obtenerlo
        //Venta venta = new Venta(viaje, cliente, formaPago, fechaPago);
		Venta venta = new Venta();

        return venta;
	}

	private void setBinders() {
		// TODO Auto-generated method stub
		
	}
	
	public VentaForm getVentaForm() {
		return ventaForm;
	}

	public void setVentaForm(VentaForm ventaForm) {
		this.ventaForm = ventaForm;
	}

	
	
}

