package com.tp.proyecto1.controllers.reserva;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.views.reserva.AgregarPagoForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class PagoFormController {

	private AgregarPagoForm form;
	private ReservaFormController observer;
	private List <Pago> pagos;
	private Double importeMaximo;
	private Boolean pagoAgregado;
	
	public PagoFormController(ReservaFormController observer) {
		this.observer = observer;			
	}
	/*
	 * Cargar el form con el máximo a pagar y los pagos anteriores, si los hay.
	 */
	public void mostrarForm(Double importeMaximo, List <FormaDePago> fdp, List <Pago> pagos) {
		this.importeMaximo = importeMaximo; 
		this.pagos = pagos;
		pagoAgregado = false;
		form = new AgregarPagoForm(importeMaximo);
		form.cargarFormasDePago(fdp);
		form.cargarPagosAnteriores(pagos);
		//form.mostrarEtiquetaSumaDePagos(sumarPagos());
		setListeners();	
		form.open();
	}
	/*
	 * Listeners para los botones del form
	 */
	private void setListeners() {
		form.setListenerImporte(e->validacionBotones());
		form.setListenerFDP(e->validacionBotones());
		form.setListenerBtnAgregar(e->accionAgregarPago());
		form.setListenerBtnGuardar(e->finalizarConPago());	
		form.setListenerBtnCancelar(e->finalizar());
	}
	/*
	 * Se habilita el botón "guardar" cuando se seleccionó un forma de pago y 
	 * se ingreso un importe. Los listeners de esos componentes invocan este método. 
	 */
	private void validacionBotones() {
		if(form.getFormaPagoSeleccionada() != null && form.getPagoIngresado() > 0) {
			form.activarBtnAgregar();
		}else{
			form.desactivarBtnAgregar();			 
		}
		if(pagoAgregado) {
			form.activarBtnGuardar();
		}else {
			form.desactivarBtnGuardar();
		}
	}	
	/*
	 * Agregar un pago y seguir agregando más.
	 */
	private void accionAgregarPago() {
		pagoAgregado = true;
		agregarPagoIngresado();
		actualizarForm();		
		// Tengo que corregir esto:
		//form.mostrarEtiquetaSumaDePagos(sumarPagos());
	}
	/*
	 * Agregar el pago ingresado a la lista de pagos.
	 */
	private void agregarPagoIngresado() {
		FormaDePago fdp = form.getFormaPagoSeleccionada();
		double importe = form.getPagoIngresado();
		Pago nuevo = new Pago(null,fdp,importe,LocalDate.now());
		pagos.add(nuevo);
	}
	/*
	 * Actualizar form después de un nuevo pago ingresado.
	 * Evaluar si la suma de pagos alcanzó el importe total.
	 */
	private void actualizarForm() {
		form.cargarPagosAnteriores(pagos);
		if(importeMaximo-sumarPagos()==0) {
			Notification.show("La suma de pagos cubre la totalidad de la reserva");
			form.close();
			finalizarConPago(); 
		}else {
			form.actualizarPagoMaximo(importeMaximo-sumarPagos());
		}		
	}
	/*
	 * Informar al observer que se finalizó el ingreso de pagos
	 * y devolverle la lista de pagos.
	 */
	private void finalizarConPago() { 
		observer.modificarListaPagos(pagos);		
		form.close();
	}
	/*
	 * Sumar los pagos para actualizar la etiqueta.
	 */
	private Double sumarPagos() {
		Double suma = 0.0;
		for(Pago pago : pagos) {
			suma += pago.getImporte();
		}
		return suma;
	}
	/*
	 * Finalización por el botón cancelar.
	 */
	private void finalizar() {
		form.close();
	}
}