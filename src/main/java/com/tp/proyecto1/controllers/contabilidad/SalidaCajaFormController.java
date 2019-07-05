package com.tp.proyecto1.controllers.contabilidad;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Egreso;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.SucursalService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.SalidaCajaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

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
	private ChangeHandler changeHandler;
	
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
		salidaCajaFormView.btnAgregarListener(e->guardar());
		salidaCajaFormView.fechaContabilizacionListener(e->valorIngresado());				
		salidaCajaFormView.sucursalListener(e->valorIngresado());		
		salidaCajaFormView.cuentaListener(e->valorIngresado());		
		salidaCajaFormView.importeListener(e->valorIngresado());
	}
	
	private void valorIngresado() {
		boolean habilitarGuardado = controlTodosValoresIngresados();
		salidaCajaFormView.habilitarBtnGuardado(habilitarGuardado);
	}
	
	private boolean controlTodosValoresIngresados() {
		return (salidaCajaFormView.getFechaContabilizacion()!=null && 
	    		salidaCajaFormView.getSucursal()!=null &&
	    		salidaCajaFormView.getCuenta()!=null &&
	    		salidaCajaFormView.getimporte()!=null);
	}
	
	private void guardar() {
		if(controlTodosValoresIngresados()) {
			prepararDatosAsiento();
	    	contabilizar();	
			changeHandler.onChange();
		}    
	}
	
	private void prepararDatosAsiento() {
		fechaContabilizacion = salidaCajaFormView.getFechaContabilizacion();	
		textoCabecera = salidaCajaFormView.getCuenta().toString();
		sucursal = salidaCajaFormView.getSucursal();
		cuenta =  salidaCajaFormView.getCuenta();
	    importe = salidaCajaFormView.getimporte();
	}
	
	private void contabilizar() {
    	AsientoREST.contabilizarSalidaCaja(fechaContabilizacion, textoCabecera, sucursal, cuenta, importe, Proyecto1Application.logUser);
    	Notification.show("Guardado");
    	salidaCajaFormView.close();
	}
	
	
	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}