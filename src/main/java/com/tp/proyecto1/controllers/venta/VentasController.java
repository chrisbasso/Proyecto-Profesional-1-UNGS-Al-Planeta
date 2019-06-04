package com.tp.proyecto1.controllers.venta;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.EstadoTransaccion;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.ventas.ComprobanteVenta;
import com.tp.proyecto1.views.ventas.VentaView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@UIScope
public class VentasController {

    private VentaView ventaView;

    private VentaFormController ventaFormController;

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private ViajeService viajeService;

    private ChangeHandler changeHandler;
    
    private Venta ventaBorrar;
    
	public VentasController() {
        Inject.Inject(this);
        this.ventaView = new VentaView();
        setListeners();
        setComponents();
        listVentas();

    }

    private void setComponents() {
        ventaView.getPaisFilter().setItems(viajeService.findAllPaises());
        this.ventaView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
        this.ventaView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }

    private void setListeners() {
        ventaView.getPaisFilter().addValueChangeListener(e->setComboCiudades());
        setChangeHandler(this::listVentas);
    	ventaView.getSearchButton().addClickListener(e->listVentas());
    	ventaView.getBtnComprobante().addClickListener(e->imprimirComprobante());
    }

	private void imprimirComprobante() {
		Venta venta = ventaView.getGrid().asSingleSelect().getValue();
		ComprobanteVenta comprobante = new ComprobanteVenta(venta);
		comprobante.open();
		UI.getCurrent().getPage().executeJavaScript("setTimeout(function() {" +
				"  print(); }, 1000);");//self.close();

	}

	private void setComboCiudades() {

        Pais pais = ventaView.getPaisFilter().getValue();
        ventaView.getCiudadFilter().setItems(pais.getCiudades());

    }

	private Button createRemoveButton(Venta venta) {
        Button botonEliminar = new Button(VaadinIcon.TRASH.create(), clickEvent -> borrarVenta(venta));
        if(!venta.isActivo()){
            botonEliminar.setEnabled(false);
        }
        return botonEliminar;
    }
	
	  private void borrarVenta(Venta venta) {
		  	ventaBorrar = venta;
	        ConfirmationDialog confirmationDialog = new ConfirmationDialog("¿Realmente desea cancelar la Venta?");
	        confirmationDialog.getConfirmButton().addClickListener(event -> {ventaBorrar.inactivar();
		        if ( !ventaBorrar.getViaje().isActivo() ) {
		        	Notification.show("No se puede cancelar la venta porque se encuentra vencida");
		        }
		        else {
		        	Viaje viaje = ventaBorrar.getViaje();
					viaje.agregarPasajes(ventaBorrar.getCantidadPasajes());
					
					LocalDate fechaSalida = viaje.getFechaSalida();
					 
					Double importeTotalOriginal = ventaBorrar.getImporteTotal();
					Double importeCancelacion = calcularImporteCancelacion(fechaSalida, importeTotalOriginal );
					
					ventaBorrar.setImporteTotal(importeCancelacion);
					
					viajeService.save(viaje);
		            ventaService.save(ventaBorrar);
		            
		            if (importeCancelacion == 0.0) {
		            	ventaBorrar.setEstadoTransaccion(EstadoTransaccion.CANCELADA);
		            	
		            	Notification.show("La Venta fue cancelada, se le reintegra el total al cliente " +ventaBorrar.getCliente().getNombreyApellido());
		            }
		            else {           
		            	Double reintegro = importeTotalOriginal - importeCancelacion;
		            	ventaBorrar.setEstadoTransaccion(EstadoTransaccion.PENALIZADA);
		            	ventaService.save(ventaBorrar);
		            	Notification.show("La Venta fue penalizada, se le reintegra " + reintegro + " al cliente " +ventaBorrar.getCliente().getNombreyApellido());
		            }
		        }
	            changeHandler.onChange();
	        });
	        confirmationDialog.open();
	    }
	
	private Double calcularImporteCancelacion(LocalDate fechaSalida, Double importeTotal) {
		Double importeCancelacion = 0.0;
		LocalDate fechaActual = LocalDate.now();
		int cantDiasRestantes = fechaSalida.compareTo(fechaActual);
			if (cantDiasRestantes < 5 && cantDiasRestantes > 0 ) {
				importeCancelacion = importeTotal * (0.2 * (5-cantDiasRestantes));
			}
		
		return importeCancelacion;
	}
	
    private Button createEditButton(Venta venta) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
            ventaFormController = new VentaFormController(venta.getViaje());
            ventaFormController.setComponentsValues(venta);
            ventaFormController.getVentaFormEdit().open();
            ventaFormController.setChangeHandler(this::listVentas);
        });
    }


    private void listVentas() {
        Venta ventaBusqueda = new Venta();
        if(checkFiltros()){
            setParametrosBusqueda(ventaBusqueda);
            ventaView.getGrid().setItems(ventaService.findVentas(ventaBusqueda));
        }else {
            ventaView.getGrid().setItems(ventaService.findAllVentas());
        }
    }

    private void setParametrosBusqueda(Venta venta) {
    	Cliente cliente = new Cliente();
    	cliente.setActivo(true);
        venta.setCliente(cliente);
        Transporte transporte = new Transporte();
        Viaje viaje = new Viaje();
        Ciudad ciudad = new Ciudad();
        viaje.setCiudad(ciudad);
        viaje.setTransporte(transporte);
        viaje.setActivo(true);
        venta.setViaje(viaje);
        if(!ventaView.getNumeroClienteFilter().isEmpty()){
            venta.getCliente().setId(ventaView.getNumeroClienteFilter().getValue().longValue());
        }
        if(!ventaView.getCiudadFilter().isEmpty()){
            venta.getViaje().setCiudad(ventaView.getCiudadFilter().getValue());
        }
        if(!ventaView.getPaisFilter().isEmpty()){
            venta.getViaje().getCiudad().setPais(ventaView.getPaisFilter().getValue());
        }
        if(!ventaView.getCodTransporteFilter().isEmpty()){
            venta.getViaje().getTransporte().setCodTransporte(ventaView.getCodTransporteFilter().getValue());
        }
        if(!ventaView.getFechaFilter().isEmpty()){
            venta.getViaje().setFechaSalida(ventaView.getFechaFilter().getValue());
        }
        if (ventaView.getActivosCheck().getValue()) {
        	venta.activar();
        }
        else {
        	venta.inactivar();
        }
        
    }

    private boolean checkFiltros() {
        return !ventaView.getPaisFilter().isEmpty() || !ventaView.getCiudadFilter().isEmpty() ||
                !ventaView.getCodTransporteFilter().isEmpty() || !ventaView.getNumeroClienteFilter().isEmpty() ||
                 !ventaView.getFechaFilter().isEmpty() ||
                 ventaView.getActivosCheck().getValue();
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public VentaView getView(){
        return ventaView;
    }
}
