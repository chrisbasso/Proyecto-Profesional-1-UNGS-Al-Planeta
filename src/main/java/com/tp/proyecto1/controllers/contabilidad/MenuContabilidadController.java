package com.tp.proyecto1.controllers.contabilidad;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.MainView;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.MenuContabilidad;
import com.tp.proyecto1.views.contabilidad.ReporteCaja;
import com.tp.proyecto1.views.contabilidad.ReporteContabilidad;
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
	
	private MainView observer;
	private MenuContabilidad menuView;
	private ReporteContabilidad reporteView;
	private ReporteCaja reporteCajaView;
	
	public MenuContabilidadController(MainView observer) {
		Inject.Inject(this);
		this.observer = observer;
		menuView = new MenuContabilidad();
		agregarListeners();
		actualizarEtiquetas();
	}
	
	public MenuContabilidad getMenuView() {
		return menuView;
	}
	
	private void agregarListeners() {
		menuView.agregarAsientosListener(e->agregarAsientos());
		menuView.visualizarAsientosAsientosListener(e->visualizarAsientos());
		menuView.agregarCuentasListener(e->agregarCuentas());
		menuView.visualizarCuentasListener(e->visualizarCuentas());
		menuView.reporteCajaListener(e->reporteCaja());
		menuView.reporteCuentasListener(e->reporteCuentas());
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

	private void reporteCaja() {
		this.reporteCajaView = new ReporteCaja();
		observer.actualizarView(reporteCajaView);
	}
	
	private void reporteCuentas() {
		this.reporteView = new ReporteContabilidad();
		observer.actualizarView(reporteView);
	}
	
	private void actualizarEtiquetas() {
		menuView.borrarEtiquetas();
		menuView.cargarEtiquetas(generarEtiquetas());
	}

	private Map<String, String> generarEtiquetas() {
		Map<String, String> nuevasEtiquetas = new HashMap<String, String>();
		return nuevasEtiquetas;
	}
}