package com.tp.proyecto1.controllers.reserva;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.views.reserva.AgregarPagoForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class PagoFormController {

	private AgregarPagoForm form;
	private ReservaFormController observer;

	public PagoFormController(ReservaFormController observer) {
		this.observer = observer;			
	}
	
	public void mostrarForm(double importeMaximo, List <FormaDePago> fdp) {		
		form = new AgregarPagoForm(importeMaximo);
		form.cargarFormasDePago(fdp);
		setListeners();	
	}
	
	private void setListeners() {
		form.setListenerImporte(e->activarBtnGuardar());
		form.setListenerFDP(e->activarBtnGuardar());
		form.setListenerBtnGuardar(e->finalizarConPago());	
		form.setListenerBtnCancelar(e->finalizar());
	}
	
	private void activarBtnGuardar() {
		if(form.getFormaPagoSeleccionada() != null && form.getPagoIngresado() > 0) {
			form.activarBtnGuardar();
		}else{
			desactivarBtnGuardar(); 
		}
	}
	
	private void desactivarBtnGuardar() {
		form.desactivarBtnGuardar();
	}	
	
	private void finalizarConPago() {
		FormaDePago fdp = form.getFormaPagoSeleccionada();
		double importe = form.getPagoIngresado(); 
		observer.agregarPago(fdp, importe);		
		form.close();
	}
	
	private void finalizar() {
		form.close();
	}
}