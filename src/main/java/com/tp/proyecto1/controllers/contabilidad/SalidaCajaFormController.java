package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Egreso;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.SucursalService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.SalidaCajaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class SalidaCajaFormController {
	@Autowired
	AsientoService asientoService;
	@Autowired
	SucursalService sucursalService;
	
	private SalidaCajaForm salidaCajaFormView;
	private LocalDate fechaContabilizacion;	
	private String textoCabecera;
	private Sucursal sucursal;
	private Cuenta cuenta;
    private Double importe;
	
	public SalidaCajaFormController() {
		Inject.Inject(this);
	}
	
	public void cargarNuevaSalidaCaja() {
		salidaCajaFormView = new SalidaCajaForm();
		List <Cuenta> cuentas = asientoService.findCuentasGasto();
		salidaCajaFormView.cargarComboCuentas(cuentas);
		List <Sucursal> sucursales = sucursalService.findAll();
	    salidaCajaFormView.cargarComboSucursales(sucursales);
		agregarListener();
		salidaCajaFormView.open();
	}
	
	public void visualizarSalidaCaja(Egreso salida) {
		LocalDate fecha = salida.getCabecera().getFechaContabilizacion();
		String txt = salida.getCabecera().getTextoCabecera();
		Sucursal suc = salida.getCabecera().getSucursal();
		Cuenta cta = salida.getPosicion().getCuenta();		
	    Double impte = salida.getPosicion().getImporte();
	    
		salidaCajaFormView = new SalidaCajaForm(fecha, txt, suc, cta, impte);
		salidaCajaFormView.open();
	}	
	
	private void agregarListener() {
		salidaCajaFormView.btnAgregarListener(e->validacionGuardar());
	}
	
	private void validacionGuardar() {
	    if(salidaCajaFormView.getFechaContabilizacion()!=null && 
	    		salidaCajaFormView.getTexto()!=null &&
	    		salidaCajaFormView.getSucursal()!=null &&
	    		salidaCajaFormView.getCuenta()!=null &&
	    		salidaCajaFormView.getimporte()!=null) {
	    	
	    	Notification.show("Acá va el método grabar");
	    	prepararDatosAsiento();
	    	contabilizar();
	    }else {
			Notification.show("Faltan datos");	    	
	    }
	}
	
	private void prepararDatosAsiento() {
		fechaContabilizacion = salidaCajaFormView.getFechaContabilizacion();	
		textoCabecera = salidaCajaFormView.getTexto();
		sucursal = salidaCajaFormView.getSucursal();
		cuenta =  salidaCajaFormView.getCuenta();
	    importe = salidaCajaFormView.getimporte();
	}
	
	private void contabilizar() {
    	AsientoREST.contabilizarSalidaCaja(fechaContabilizacion, textoCabecera, sucursal, cuenta, importe, Proyecto1Application.logUser);
	}
}