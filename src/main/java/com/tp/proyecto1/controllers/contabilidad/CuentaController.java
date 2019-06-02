package com.tp.proyecto1.controllers.contabilidad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.CuentaForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class CuentaController {

	@Autowired
	private AsientoService asientoService;	
	private List<Cuenta> cuentas;
	private List<TipoCuenta> tipoCuentas;
	private CuentaForm cuentaForm;

	public CuentaController(){
		Inject.Inject(this);
		cuentas = asientoService.findCuentas();
		tipoCuentas = TipoCuenta.getAllTipos();
	}

	public void getFormVisualizar(Cuenta cuenta) {
		cuentaForm = new CuentaForm(cuenta);		
		cuentaForm.open();
	}

	public void getFormCrear() {
		cuentaForm = new CuentaForm();
		cuentaForm.cargarComboTipoCuenta(tipoCuentas);
		setListeners();
		cuentaForm.open();
	}
	
	private void setListeners() {
    	cuentaForm.setBtnGuardarListener(e->validacion());
	}
	
	private void validacion(){		
		int numeroCtaNv = cuentaForm.getNumero();
		String descripCtaNv = cuentaForm.getDescripcion();
		TipoCuenta tipoCtaNv = cuentaForm.getTipoCuenta();
		boolean crear = true;
		for(Cuenta cuenta : cuentas){
			if(cuenta.getNumeroCuenta() == numeroCtaNv){
				Notification.show("Ya existe esa cuenta.");
				crear = false;
			}
		}
		if(crear){
			guardarCuenta(descripCtaNv, numeroCtaNv, tipoCtaNv);
		}
	}
	
	private void guardarCuenta(String descripcion, int numero, TipoCuenta tipo){
		Cuenta cuentaNueva = new Cuenta(numero, descripcion, tipo);
		asientoService.saveCuenta(cuentaNueva);
		Notification.show("Se creo la cuenta " + descripcion + ".");
		cuentaForm.close();
	}
}