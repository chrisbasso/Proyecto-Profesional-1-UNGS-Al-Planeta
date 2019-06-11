package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.contabilidad.MovimientoCaja;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.SucursalService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.MovimientosCajaView;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class MovimientosCajaController {

	@Autowired
	private AsientoService asientoService;
	@Autowired
	private SucursalService sucursalService;
	
	private MovimientosCajaView view;
	private List <MovimientoCaja> movimientos;
	
	
	public MovimientosCajaController() {
		Inject.Inject(this);
		cargarUltimosMovimientos();
		view = new MovimientosCajaView();
		view.cargarComboSucursal(sucursalService.findAll());		
		view.cargarMovimientos(movimientos);
		view.cargarEtiquetasSaldos(totalizarValores());
	}

	private void cargarUltimosMovimientos() {
		int diasDelMes = LocalDate.now().getMonth().length(LocalDate.now().isLeapYear());
		LocalDate desde = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
		LocalDate hasta = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), diasDelMes);
		movimientos = asientoService.findMovimientosCaja(desde, hasta);
	}
	
	private Map <Sucursal, Double> totalizarValores(){
		Map <Sucursal, Double> ret = new HashMap<Sucursal, Double>();
		
		for (MovimientoCaja movimientoCaja : movimientos) {
			Sucursal suc = movimientoCaja.getCabecera().getSucursal();
			Double valorIteracion = movimientoCaja.getPosicion().getImporte();
		
			if(ret.containsKey(suc)) {
				Double valorAnterior = ret.get(suc);
				Double valorNuevo = valorAnterior + valorIteracion;
				ret.replace(suc, valorNuevo);
			}else {
				ret.put(suc, valorIteracion);
			}
		}		
		return ret;
	}
	
	public MovimientosCajaView getMovimientosView() {
		return view;
	}
}
