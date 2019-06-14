package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Modulo;
import com.tp.proyecto1.model.contabilidad.MovimientoCaja;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.SucursalService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.MovimientosCajaView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class MovimientosCajaController {

	@Autowired
	private AsientoService asientoService;
	@Autowired
	private SucursalService sucursalService;
	@Autowired
	private SalidaCajaFormController salidaCajaFormController;
	@Autowired
	private UserService userService;
	
	private MovimientosCajaView view;
	private List <MovimientoCaja> movimientos;
	private ChangeHandler changeHandler;
	
	public MovimientosCajaController() {
		Inject.Inject(this);
	}

	public MovimientosCajaView getMovimientosView() {
		view = new MovimientosCajaView();
		view.cargarComboSucursal(sucursalService.findAll());
		leerMovimientosDelMes();
		view.cargarMovimientos(movimientos);
		view.cargarEtiquetasSaldos(totalizarValores());
		agregarListeners();
		view.agregarColumnaBorrado(this::createDeleteButton);
		return view;
	}

	private void leerMovimientosDelMes() {
		int diasDelMes = LocalDate.now().getMonth().length(LocalDate.now().isLeapYear());
		LocalDate desde = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
		LocalDate hasta = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), diasDelMes);
		movimientos = asientoService.findMovimientosCaja(desde, hasta);
	}
	
	private Map <Sucursal, Double> totalizarValores(){
		Map <Sucursal, Double> ret = new HashMap<Sucursal, Double>();		
		for (MovimientoCaja movimientoCaja : movimientos) {
			Sucursal suc = movimientoCaja.getCabecera().getSucursal();
			Double valorIteracion = movimientoCaja.getImporte();
		
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
	
	private void agregarListeners() {
		setChangeHandler(this::refrescarDatosVista);
		view.setBtnAgregarListener(e->nuevoEgreso());
		view.setBtnBuscarListener(e->buscarMovimientos());
	}
	
	private Button createDeleteButton(MovimientoCaja movimiento){
        Button btnBorrar = new Button(VaadinIcon.TRASH.create(), clickEvent -> borrarMovimiento(movimiento));
    		if(movimiento.getCabecera().isAnulado() || !movimiento.getCabecera().getModulo().equals(Modulo.TESORERIA)){
    			btnBorrar.setEnabled(false);
    		}
    		return btnBorrar;
	}

	private void borrarMovimiento(MovimientoCaja movimiento) {
		Optional<Asiento> asientoPorAnular = asientoService.findById(movimiento.getIdAsiento());
		if(asientoPorAnular.isPresent()) {
			Long idAnulac = AsientoREST.anularAsiento(asientoPorAnular.get(), Proyecto1Application.logUser);
			Notification.show("Movimiento anulado con el registro: " + idAnulac);			
		}		
		refrescarDatosVista();
	}
	
	private void nuevoEgreso() {
		salidaCajaFormController.cargarNuevaSalidaCaja();
		salidaCajaFormController.setChangeHandler(this::refrescarDatosVista);
	}

	private void buscarMovimientos() {
		LocalDate fecha = view.getValueFecha();
		User usuario = buscarConId(view.getValueUsuario());
		Sucursal suc = view.getValueSucursal();
		
		if(fecha != null){
			if(usuario != null){
				if(suc != null){
					Cabecera cabEjemplo = new Cabecera();
					cabEjemplo.setFechaContabilizacion(fecha);
					Posicion posEjemplo = new Posicion();
					MovimientoCaja movEjemplo = MovimientoCaja.getInstancia(0L, cabEjemplo, posEjemplo);
					movimientos = asientoService.findMovimientosCajaFiltrado(fecha, usuario, suc);
					refrescarDatosVista();
				}
			}
		}
	}	
		
	private User buscarConId(Long id) {
		return userService.getUserById(id);
	}
	
	private void refrescarDatosVista() {
		leerMovimientosDelMes();
		view.cargarMovimientos(movimientos);
		view.cargarEtiquetasSaldos(totalizarValores());
	}
	
	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
}