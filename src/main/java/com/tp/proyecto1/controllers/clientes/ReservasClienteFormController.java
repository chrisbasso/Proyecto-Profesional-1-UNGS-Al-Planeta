package com.tp.proyecto1.controllers.clientes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.ReservaClienteForm;
import com.tp.proyecto1.views.reserva.ReservaClienteView;
import com.tp.proyecto1.views.reserva.ReservaForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservasClienteFormController
{
	
	private ReservaClienteForm reservaClienteForm;
	
	private Reserva reserva;
	private Viaje viaje;	
	private List <Pago> listaDePagos;
	
	public ReservasClienteFormController(Viaje viaje)
	{
		 Inject.Inject(this);
	     this.viaje = viaje;
	     this.listaDePagos = new ArrayList<Pago>(); 
	}
	
	public ReservaClienteForm getView(Reserva reserva) {
		this.reserva = reserva;
		this.listaDePagos = reserva.getPagos();
		reservaClienteForm = new ReservaClienteForm(reserva.getViaje());
		reservaClienteForm.setModoModificacion(reserva.getCantidadPasajes(), reserva.getCliente(), reserva.getTotalPagado());
		setListeners(); 
		return reservaClienteForm;		
	}
	
	private void setListeners()
	{
		this.reservaClienteForm.setListenerBtnCancel(e->{this.reservaClienteForm.close();});
	}
	
	private void actualizarImportes(){
		reservaClienteForm.actualizarPrecioTotal(getPrecioTotal());
		Double saldo = getSaldo();
		reservaClienteForm.actualizarSaldo(saldo);
		reservaClienteForm.actualizarPagos(getSumatoriaPagos());
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
	/*
	 * Calcular el saldo a pagar en funcion del precio total 
	 * y la suma de pagos
	 */
	private double getSaldo() {
		return getPrecioTotal() - getSumatoriaPagos();
	}
	
	private double getPrecioTotal() {
		int pasajes = reservaClienteForm.cantidadPasajesSeleccionados();
		return viaje.getPrecio() * pasajes;
	}

}
