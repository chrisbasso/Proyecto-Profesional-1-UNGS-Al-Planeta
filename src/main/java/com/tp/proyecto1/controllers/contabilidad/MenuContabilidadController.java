package com.tp.proyecto1.controllers.contabilidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.MainView;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.MenuContabilidad;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class MenuContabilidadController {

	@Autowired
	AsientoFormController asientoFormController;
	@Autowired
	AsientosController asientosController;
	@Autowired
	CuentaFormController cuentaFormController;
	@Autowired
	CuentasController cuentasController;
	@Autowired
	SalidaCajaFormController salidaCajaFormController;
	@Autowired
	MovimientosCajaController movimientosCajaController;

	private MainView observer;
	private MenuContabilidad menuView;
	
	public MenuContabilidadController(MainView observer) {
		Inject.Inject(this);
		this.observer = observer;
		menuView = new MenuContabilidad();
		agregarListeners();
	}
	
	public MenuContabilidad getMenuView() {
		return menuView;
	}
	
	private void agregarListeners() {
		menuView.agregarAsientosListener(e->agregarAsientos());
		menuView.visualizarAsientosAsientosListener(e->visualizarAsientos());
		menuView.agregarCuentasListener(e->agregarCuentas());
		menuView.visualizarCuentasListener(e->visualizarCuentas());
		menuView.agregarSalidaCajaListener(e->nuevaSalidaCaja());
		menuView.visualizarMovimintosCajaListener(e->visualizarMovCaja());
	}
	
	private void agregarAsientos() {
		asientoFormController.getFormCrear();
	}
	
	private void visualizarAsientos() {		
		observer.actualizarView(asientosController.getAsientosView());
	}
	
	private void agregarCuentas() {
		cuentaFormController.getFormCrear();
	}

	private void visualizarCuentas() {		
		observer.actualizarView(cuentasController.getCuentasView());
	}

	private void nuevaSalidaCaja() {
		salidaCajaFormController.cargarNuevaSalidaCaja();		
	}
	
	private void visualizarMovCaja() {
		observer.actualizarView(movimientosCajaController.getMovimientosView());
	}
	
}