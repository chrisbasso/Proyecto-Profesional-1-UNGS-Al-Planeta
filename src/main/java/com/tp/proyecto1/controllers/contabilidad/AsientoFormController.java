package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.contabilidad.TipoPosicion;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.SucursalService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.AsientoForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class AsientoFormController {

	@Autowired
	private AsientoService asientoService;
	@Autowired
	private SucursalService sucursalService;
	
	private AsientoForm asientoForm;
	private List<Sucursal> sucursales;
	private List<TipoPosicion> tipoPosicion;
	private List<Cuenta> cuentas;
	private Cabecera cabecera;
	private List<Posicion> posiciones;
	private Double debe;
	private Double haber;
	private ChangeHandler changeHandler;
	
	public AsientoFormController() {
		Inject.Inject(this);
		cuentas = asientoService.findCuentas();
		sucursales = sucursalService.findAll();
		tipoPosicion = new ArrayList<TipoPosicion>();
		tipoPosicion.add(TipoPosicion.DEBE);
		tipoPosicion.add(TipoPosicion.HABER);
		posiciones = new ArrayList <Posicion>();
	}
	
	public void getFormVisualizar(Asiento asiento) {
		asientoForm = new AsientoForm(asiento);		
		asientoForm.open();
	}

	public void getFormCrear() {
		asientoForm = new AsientoForm();
		cargarCombos();
		setListeners();
		asientoForm.bloquearPosiciones();
		asientoForm.open();
	}

    private void cargarCombos() {
    	asientoForm.cargarComboSucursal(sucursales);
    	asientoForm.cargarComboTipoPosicion(tipoPosicion);
    	asientoForm.cargarComboCuentas(cuentas);    	
    }
    
    private void setListeners() {
    	// Cabecera del asiento
    	asientoForm.setFechaListener(e->validacionCabecera());
    	//asientoForm.setTextoListener(e->validacionCabecera());
    	asientoForm.setSucursalListener(e->validacionCabecera());
    	// Posicion nueva
    	asientoForm.setCuentaListener(e->validacionBtnAgregar());
    	asientoForm.setImporteListener(e->validacionBtnAgregar());
    	asientoForm.setImporteKUListener(e->validacionBtnAgregar());
    	// Botones
    	asientoForm.setBtnAgregarListener(e->agregarPosicion());
    	asientoForm.setBtnGuardarListener(e->guardarAsiento());   	
    	asientoForm.setBtnCancelarListener(e->cerrar());
    }
    
    private void validacionCabecera() {
    	if(asientoForm.getFechaSeleccionada() != null && asientoForm.getSucursal() != null) {
    		asientoForm.habilitarPosiciones();    		
    	}    	 
    }
    
    private void validacionBtnAgregar() {
    	if(asientoForm.getTipoPosicion() != null && asientoForm.getCuenta() != null && asientoForm.getImporte() != null) {
    		asientoForm.habilitarBtnAgregar();
    	}else {
    		asientoForm.deshabilitarBtnAgregar();
    	}
    }
    
    private void agregarPosicion() {
    	TipoPosicion tp = asientoForm.getTipoPosicion();
    	Cuenta ct = asientoForm.getCuenta();
    	Double imp = asientoForm.getImporte();
    	Posicion posicion = new Posicion(tp, ct, imp);    	
    	posiciones.add(posicion);
		asientoForm.actualizarGridPosiciones(posiciones);
		asientoForm.deshabilitarBtnAgregar();
		actualizarEtiquetas();
    }
    
	private void actualizarEtiquetas(){
		actualizarDebeHaber();
		asientoForm.actualizarEtiquetaDebe(debe);
		asientoForm.actualizarEtiquetaHaber(haber);
		asientoForm.actualizarEtiquetaSaldo(debe - haber);
	}
	
    private void guardarAsiento() {
    	if(faltanDatosCabecera()) {
    		Notification.show("No se completaron todos los datos de la cabecera.");
    	}else if(faltanPosiciones()) {
    		Notification.show("Muy pocas posiciones, imposible guardar.");
    	}else if(saldoEsCero()) {
    		cabecera = new Cabecera(LocalDate.now(), 
    				asientoForm.getFechaSeleccionada(), Proyecto1Application.logUser,
    				asientoForm.getTextoCabecera(),asientoForm.getSucursal());
    		Asiento asiento = new Asiento(cabecera,posiciones);
        	asientoService.save(asiento);    	
        	cerrar();	
    	}else {
    		Notification.show("El asiento no balancea, no es posible guardar.");
    	}
    }
    
    private boolean faltanDatosCabecera() {
    	return (asientoForm.getFechaSeleccionada() != null && asientoForm.getSucursal() != null);
    }
    
    private boolean faltanPosiciones() {
    	return posiciones.size()<2;
    }
    
    private boolean saldoEsCero() {
		actualizarDebeHaber();
    	return Double.compare(debe, haber) == 0;
    }
    
	private void actualizarDebeHaber(){
		debe = 0.0;
    	haber = 0.0;
    	for(Posicion posicion : posiciones) {
    		if(posicion.getDebeHaber() == TipoPosicion.DEBE) {
    			debe += posicion.getImporte();
    		}else {
    			haber += posicion.getImporte();
    		}
    	}
	}
	
    private void cerrar() {
    	asientoForm.close();
    }
    
    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}