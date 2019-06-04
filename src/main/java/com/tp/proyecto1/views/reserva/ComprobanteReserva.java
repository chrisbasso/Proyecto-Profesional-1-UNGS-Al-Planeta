package com.tp.proyecto1.views.reserva;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;



public class ComprobanteReserva extends Dialog {

	private Reserva reserva;

	private VerticalLayout mainLayout = new VerticalLayout();
	private TextField cliente = new TextField("Cliente:");
	private TextField destino = new TextField("Destino:");
	private TextField diaHora = new TextField("Salida:");
	private TextField transporte = new TextField ("Transporte:");
	private TextField codTransporte = new TextField("Código Transporte:");
	private TextField clase = new TextField("Clase:");
	private TextField recomendacion = new TextField("Recomendaciones:");

	private TextField precio = new TextField("Precio:");


	public ComprobanteReserva(Reserva venta) {

		this.reserva=venta;
		setComponents();
		setLayouts();

	}

	private void setComponents() {

		cliente.setValue(reserva.getCliente().getNombreyApellido());
		cliente.setReadOnly(true);
		destino.setValue(reserva.getViaje().getCiudad().toString());
		destino.setReadOnly(true);
		diaHora.setValue(reserva.getViaje().getFechaSalida().toString() + " " + reserva.getViaje().getHoraSalida().toString());
		diaHora.setReadOnly(true);
		transporte.setValue(reserva.getViaje().getTransporte().getTipo().getDescripcion());
		transporte.setReadOnly(true);
		codTransporte.setValue(reserva.getViaje().getTransporte().getCodTransporte());
		codTransporte.setReadOnly(true);
		clase.setValue(reserva.getViaje().getTransporte().getClase());
		clase.setReadOnly(true);
		recomendacion.setValue(reserva.getViaje().getRecomendacion());
		recomendacion.setReadOnly(true);
		
		precio.setValue(reserva.getImporteTotal().toString());
		precio.setReadOnly(true);

	}

	private void setLayouts() {
		this.add(mainLayout);
		mainLayout.setSpacing(false);
		mainLayout.add(cliente,new HorizontalLayout(destino,diaHora),new HorizontalLayout(transporte,codTransporte),clase,recomendacion,precio);
		this.setWidth("800px");
		this.setHeight("100%");
	}

}
