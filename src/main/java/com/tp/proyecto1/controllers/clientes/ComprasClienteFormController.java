package com.tp.proyecto1.controllers.clientes;


import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.ventas.VentaClienteForm;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@UIScope
public class ComprasClienteFormController
{
	
	private VentaClienteForm ventaForm;
	
	@Autowired
	private VentaService ventaService;
	
	private Venta venta;

	private Viaje viaje;

	@Autowired
	private ConfiguracionService configuracionService;
	
	
	public ComprasClienteFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;
		this.ventaForm = new VentaClienteForm();
		this.setListeners();
        this.setComponents();
        this.setComponentesLectura(this.viaje);
	}
	
	public VentaClienteForm getView()
	{
		return ventaForm;
	}
	
	private void setComponentesLectura(Viaje viaje) {
		ventaForm.getContinenteDestino().setValue(viaje.getDestino().getProvincia().getPais().getContinente().toString());
		ventaForm.getContinenteOrigen().setValue(viaje.getOrigen().getProvincia().getPais().getContinente().toString());
		ventaForm.getPaisDestino().setValue(viaje.getDestino().getProvincia().getPais().toString());
		ventaForm.getPaisOrigen().setValue(viaje.getOrigen().getProvincia().getPais().toString());
		ventaForm.getProvinciaDestino().setValue(viaje.getDestino().getProvincia().toString());
		ventaForm.getProvinciaOrigen().setValue(viaje.getOrigen().getProvincia().toString());
		ventaForm.getCiudadDEstino().setValue(viaje.getDestino().getNombre());
		ventaForm.getCiudadOrigen().setValue(viaje.getOrigen().getNombre());
		ventaForm.getCodTransporte().setValue(viaje.getTransporte().getCodTransporte());
		ventaForm.getTransporte().setValue(viaje.getTransporte().getTipo().getDescripcion());
		ventaForm.getFechaSalida().setValue(viaje.getFechaSalida().toString());
		ventaForm.getHoraSalida().setValue(viaje.getHoraSalida().toString());
		ventaForm.getProvinciaDestino().setReadOnly(true);
		ventaForm.getCiudadDEstino().setReadOnly(true);
		ventaForm.getPaisDestino().setReadOnly(true);
		ventaForm.getContinenteDestino().setReadOnly(true);
		ventaForm.getProvinciaOrigen().setReadOnly(true);
		ventaForm.getCiudadOrigen().setReadOnly(true);
		ventaForm.getContinenteOrigen().setReadOnly(true);
		ventaForm.getPaisOrigen().setReadOnly(true);
		ventaForm.getCodTransporte().setReadOnly(true);
		ventaForm.getTransporte().setReadOnly(true);
		ventaForm.getFechaSalida().setReadOnly(true);
		ventaForm.getHoraSalida().setReadOnly(true);
		ventaForm.getSaldoPagar().setReadOnly(true);
		ventaForm.getSubtotal().setReadOnly(true);
		ventaForm.getPuntosObtenidos().setReadOnly(true);
		ventaForm.getDenoPromocion().setReadOnly(true);
	}	

	private void setComponents() {		
		ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		ventaForm.setSaldoPagarDouble(0.0);
		ventaForm.setSubtotalDouble(0.0); 
	}
	
	public void setComponentsValues(Venta venta) {

		this.venta = venta;
		
		ventaForm.getHoraSalida().setValue(venta.getViaje().getHoraSalida().toString());
		ventaForm.getFechaSalida().setValue(venta.getViaje().getFechaSalida().toString());
		ventaForm.getTransporte().setValue(venta.getViaje().getTransporte().getTipo().getDescripcion());
		ventaForm.getCodTransporte().setValue(venta.getViaje().getTransporte().getCodTransporte());
		
		if(venta.getPagos().get(0).getFormaDePago() != null){
			ventaForm.getFormaPago().setValue(venta.getPagos().get(0).getFormaDePago());
		}
		else {
			ventaForm.getFormaPago().setValue(venta.getPagos().get(1).getFormaDePago());
		}
		
		ventaForm.getSubtotal().setValue(venta.getImporteTotal());
		ventaForm.getSaldoPagar().setValue(venta.getImporteTotal());
		ventaForm.getCliente().getFiltro().setValue(venta.getCliente().getId().toString());
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		ventaForm.getPasajerosGridComponent().setPasajerosList(pasajeros);
		ventaForm.getPasajerosGridComponent().setGrid();
		ventaForm.getPasajerosGridComponent().setModoConsulta();
		ventaForm.getCliente().getFiltro().setReadOnly(true);
		ventaForm.getCliente().getSearchButton().setVisible(false);
		ventaForm.getFormaPago().setReadOnly(true);
		ventaForm.getPromocion().setReadOnly(true);
		ventaForm.getDenoPromocion().setReadOnly(true);
		if(venta.getPromocion() != null) {
			ventaForm.getPromocion().setValue(venta.getPromocion().toString());
			ventaForm.getDenoPromocion().setValue(venta.getPromocion().getDoubleValue().toString());
		}
		ventaForm.getPuntosObtenidos().setReadOnly(true);
		ventaForm.getPasajerosGridComponent().setEditarInvisible(false);
		this.generarPuntos();
	}

	private void setListeners() {
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
	}
	

	private void generarPuntos() {
		Integer cantPuntosPorVenta;
		Integer pesosPorPunto = Integer.parseInt(getPesosPorPunto());
		cantPuntosPorVenta =  this.ventaForm.getSaldoPagar().getValue().intValue()/pesosPorPunto;
		
		//Verificar si tiene bonificacion de puntos de promocion y aplicarlos
		Promocion promoPuntos = new Promocion();
		promoPuntos = venta.getPromocion();
		if (promoPuntos != null) {
			if (promoPuntos.getTipoPromocion().equals("Puntos")) cantPuntosPorVenta = promoPuntos.getDoubleValue() * cantPuntosPorVenta;
		}
		this.ventaForm.getPuntosObtenidos().setValue(cantPuntosPorVenta.toString());
	}
	private String getPesosPorPunto() {
		return configuracionService.findValueByKey("pesos_por_punto");
	}
}
