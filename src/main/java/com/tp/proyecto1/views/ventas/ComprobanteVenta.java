package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.utils.PasajerosGridComponent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class ComprobanteVenta extends Dialog {

	private Venta venta;

	private VerticalLayout mainLayout = new VerticalLayout();
	private TextField cliente = new TextField("Cliente:");
	private TextField destino = new TextField("Destino:");
	private TextField diaHora = new TextField("Salida:");
	private TextField transporte = new TextField ("Transporte:");
	private TextField codTransporte = new TextField("Código Transporte:");
	private TextField clase = new TextField("Clase:");
	private TextField recomendacion = new TextField("Recomendaciones:");
	private TextField fechaDeEmision = new TextField("Fecha:");
	private TextField nombreOperador = new TextField("Operador:");
	private TextField espacio = new TextField();

	private Label pasajerosTitle = new Label("Pasajeros:");
	private PasajerosGridComponent pasajeros = new PasajerosGridComponent();

	private TextField precio = new TextField("Precio:");


	public ComprobanteVenta(Venta venta) {

		this.venta=venta;
		setComponents();
		setLayouts();

	}

	private void setComponents() {

		cliente.setValue(venta.getCliente().getNombreyApellido());
		cliente.setReadOnly(true);
		destino.setValue(venta.getViaje().getDestino().toString());
		destino.setReadOnly(true);
		diaHora.setValue(venta.getViaje().getFechaSalida().toString() + " " + venta.getViaje().getHoraSalida().toString());
		diaHora.setReadOnly(true);
		transporte.setValue(venta.getViaje().getTransporte().getTipo().getDescripcion());
		transporte.setReadOnly(true);
		codTransporte.setValue(venta.getViaje().getTransporte().getCodTransporte());
		codTransporte.setReadOnly(true);
		clase.setValue(venta.getViaje().getTransporte().getClase());
		clase.setReadOnly(true);
		if(venta.getViaje().getRecomendacion()!=null){
			recomendacion.setValue(venta.getViaje().getRecomendacion());
		}
		recomendacion.setReadOnly(true);
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		this.pasajeros.setPasajerosList(pasajeros);
		this.pasajeros.setGrid();
		this.pasajeros.setModoConsulta();
		
		precio.setValue(venta.getImporteTotal().toString());
		precio.setReadOnly(true);
		
		fechaDeEmision.setValue(LocalDate.now().toString());
		fechaDeEmision.setReadOnly(true);
		
		nombreOperador.setValue("Pepe");
		nombreOperador.setReadOnly(true);
		
		espacio.setReadOnly(true);
		espacio.setVisible(false);
	}

	private void setLayouts() {
		this.add(mainLayout);
		mainLayout.setSpacing(false);
		mainLayout.add(getLogoMasFechaEmision(),cliente,new HorizontalLayout(destino,diaHora),new HorizontalLayout(transporte,codTransporte),clase,recomendacion,pasajerosTitle,pasajeros,precio,nombreOperador);
		this.setWidth("800px");
		this.setHeight("100%");
	}
	
	private HorizontalLayout getLogoMasFechaEmision() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");
		return new HorizontalLayout(logo, title, fechaDeEmision);
	}
}
