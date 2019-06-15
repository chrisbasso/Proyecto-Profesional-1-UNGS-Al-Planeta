package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.utils.PasajerosGridComponent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class ComprobanteVenta extends Dialog {

	private Venta venta;

	private VerticalLayout mainLayout = new VerticalLayout();
	private TextField cliente = new TextField("Cliente:");
	private TextField dni = new TextField("DNI:");
	private TextField mail = new TextField("E-Mail:");
	private TextField destino = new TextField("Destino:");
	private TextField diaHora = new TextField("Salida:");
	private TextField transporte = new TextField ("Transporte:");
	private TextField codTransporte = new TextField("Código Transporte:");
	private TextField clase = new TextField("Clase:");
	private TextField recomendacion = new TextField("Recomendaciones:");
	private TextField fechaDeEmision = new TextField("Fecha:");
	private TextField nombreOperador = new TextField("Operador:");
	private TextField espacio = new TextField();
	private TextField origen = new TextField("Origen");
	private TextField precioUnitario = new TextField("Precio Unitario:");	
	private TextField precioSubtotal = new TextField("Subtotal:");
	private TextField valorPromocion	= new TextField("Valor:");
	private TextField denoPromocion	= new TextField("Promocion:");
	private TextArea reglasCancelacion = new TextArea("Política de Cancelación");

	private String promo = "Promoción de";
	private Label pasajerosTitle = new Label("Pasajeros:");
	private PasajerosGridComponent pasajeros = new PasajerosGridComponent();

	private TextField precio = new TextField("Precio Total:");


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
		recomendacion.setWidth("608px");
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		this.pasajeros.setPasajerosList(pasajeros);
		this.pasajeros.setGrid();
		this.pasajeros.setModoConsulta();
		
		precio.setValue(venta.getImporteTotal().toString());
		precio.setReadOnly(true);
		
		fechaDeEmision.setValue(LocalDate.now().toString());
		fechaDeEmision.setReadOnly(true);
		
		nombreOperador.setValue(venta.getVendedor().getUser());
		nombreOperador.setReadOnly(true);
		
		espacio.setVisible(false);
		
		origen.setReadOnly(true);
		origen.setValue(venta.getViaje().getOrigen().getNombre());
		
		reglasCancelacion.setReadOnly(true);
		reglasCancelacion.setValue("Hasta cinco dias antes de la salida del viaje se podrá cancelar y obtener el 100% de reintegro. Luego se retendra un 20% del valor del pasaje por día, hasta llegar al día de salida, que ya no se podrá cancelar.");
		reglasCancelacion.setHeight("100px");
		reglasCancelacion.setWidth("608px");
		
		dni.setReadOnly(true);
		dni.setValue(venta.getCliente().getDni());
		
		mail.setReadOnly(true);
		mail.setValue(venta.getCliente().getEmail());	
	
		precioUnitario.setReadOnly(true);
		precioUnitario.setValue(venta.getViaje().getPrecio().toString());
		
		precioSubtotal.setReadOnly(true);
		precioSubtotal.setValue(venta.getSubtotal().toString());
		
		if ( venta.getPromocion() != null) {
			if(venta.getPromocion().getTipoPromocion().equals("Puntos")) {
				this.valorPromocion.setPrefixComponent(new Span("X"));
				this.denoPromocion.setLabel(promo + " Puntos:");
			}
			else if(venta.getPromocion().getTipoPromocion().equals("Descuento")) {
				this.valorPromocion.setSuffixComponent(new Span("%"));
				this.denoPromocion.setLabel(promo + " Descuento:");
			}
			this.valorPromocion.setValue(venta.getPromocion().getDoubleValue().toString());
			denoPromocion.setValue(venta.getPromocion().getNombrePromocion());
		}
	}

	private void setLayouts() {
		this.add(mainLayout);
		mainLayout.setSpacing(false);
		mainLayout.add(getLogoMasFechaEmision(),new HorizontalLayout(cliente,dni,mail), new HorizontalLayout(origen,diaHora,destino),new HorizontalLayout(transporte,codTransporte,clase),
			recomendacion,pasajerosTitle,pasajeros,new HorizontalLayout(denoPromocion,valorPromocion), new HorizontalLayout(precio,precioSubtotal,precioUnitario),reglasCancelacion,nombreOperador);
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
