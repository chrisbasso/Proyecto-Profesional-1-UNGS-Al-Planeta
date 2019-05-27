
package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.AsientoForm;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class AsientoFormController {

	@Autowired
	private AsientoService asientoService;

	private AsientoForm asientoForm;
	private List<Cuenta> cuentas;
	
	public AsientoFormController() {
		Inject.Inject(this);
		cuentas = asientoService.findCuentas();
		asientoForm = new AsientoForm(cuentas);
		setListeners();
		asientoForm.open();
	}
	
    private void setListeners() {
    	asientoForm.setFechaListener(e->validacionBtnGuardar());
    	asientoForm.setCuentaListener(e->validacionBtnGuardar());
    	asientoForm.setImporteListener(e->validacionBtnGuardar());
    	asientoForm.setImporteKUListener(e->validacionBtnGuardar());
    	asientoForm.setBtnGuardarListener(e->guardarAsiento());   	
    	asientoForm.setBtnCancelarListener(e->cerrar());
    }
    
    private void validacionBtnGuardar() {
    	if(asientoForm.getCuentaSeleccionada() != null &&
    			asientoForm.getFechaSeleccionada() != null && 
    			asientoForm.getImporte() != null) {
    		asientoForm.habilitarBtnGuardar();
    	}else {
    		asientoForm.deshabilitarBtnGuardar();
    	}
    }
    
    private void guardarAsiento() {
    	LocalDate fecha = asientoForm.getFechaSeleccionada();
    	User usuario = null;
    	String textoCabecera = asientoForm.getTextoCabecera();
    	Cabecera cabecera = new Cabecera(fecha, usuario, textoCabecera);
    	
    	Cuenta cuenta = asientoForm.getCuentaSeleccionada();
    	Double importe = asientoForm.getImporte();
    	Posicion posicion = new Posicion(cuenta, importe);
    	List<Posicion> posiciones = new ArrayList<Posicion>();
    	posiciones.add(posicion);
    	
    	Asiento asiento = new Asiento(cabecera,posiciones);
    	asientoService.save(asiento, cabecera, posiciones);
    	
    	asientoForm.close();
    }
    
    private void cerrar() {
    	asientoForm.close();
    }
}