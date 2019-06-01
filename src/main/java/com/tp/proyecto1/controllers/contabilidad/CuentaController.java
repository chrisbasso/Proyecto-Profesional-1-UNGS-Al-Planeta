package com.tp.proyecto1.controllers.contabilidad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.contabilidad.Cuenta;
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
	private CuentaForm cuentaForm;

	public CuentaController(){
		Inject.Inject(this);
		cuentas = asientoService.findAllCuentas();
	}

	public void getFormVisualizar(Cuenta cuenta) {
		cuentaForm = new CuentaForm(cuenta);		
		cuentaForm.open();
	}

	public void getFormCrear() {
		cuentaForm = new CuentaForm();
		setListeners();
		cuentaForm.open();
	}
	
	private void setListeners() {
    	cuentaForm.setBtnGuardarListener(e->validacion());
	}
	
	private void validacion(){		
		String cuentaNueva = cuentaForm.getDescripcion();
		boolean crear = true;
		for(Cuenta cuenta : cuentas){
			if(cuenta.getDescripcion().equals(cuentaNueva)){
				Notification.show("Ya existe esa cuenta.");
				crear = false;
			}
		}
		if(crear){
			guardarCuenta(cuentaNueva);
		}
	}
	
	private void guardarCuenta(String descripcion){
		Cuenta cuentaNueva = new Cuenta(descripcion);
		asientoService.saveCuenta(cuentaNueva);
		Notification.show("Se creo la cuenta " + descripcion + ".");
		cuentaForm.close();
	}
}