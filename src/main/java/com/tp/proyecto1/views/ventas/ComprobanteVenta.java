package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.PasajerosGridComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

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
		destino.setValue(venta.getViaje().getCiudad().toString());
		destino.setReadOnly(true);
		diaHora.setValue(venta.getViaje().getFechaSalida().toString() + " " + venta.getViaje().getHoraSalida().toString());
		diaHora.setReadOnly(true);
		transporte.setValue(venta.getViaje().getTransporte().getTipo().getDescripcion());
		transporte.setReadOnly(true);
		codTransporte.setValue(venta.getViaje().getTransporte().getCodTransporte());
		codTransporte.setReadOnly(true);
		clase.setValue(venta.getViaje().getTransporte().getClase());
		clase.setReadOnly(true);
		recomendacion.setValue(venta.getViaje().getRecomendacion());
		recomendacion.setReadOnly(true);
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		this.pasajeros.setPasajerosList(pasajeros);
		this.pasajeros.setGrid();
		this.pasajeros.setModoConsulta();
		
		precio.setValue(venta.getImporteTotal().toString());
		precio.setReadOnly(true);

	}

	private void setLayouts() {
		this.add(mainLayout);
		mainLayout.setSpacing(false);
		mainLayout.add(cliente,new HorizontalLayout(destino,diaHora),new HorizontalLayout(transporte,codTransporte),clase,recomendacion,pasajerosTitle,pasajeros,precio);
		this.setWidth("800px");
		this.setHeight("100%");
	}

}
