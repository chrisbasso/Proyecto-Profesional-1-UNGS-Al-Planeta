package com.tp.proyecto1.views.reserva;

import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.controllers.reserva.ReservaREST;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.utils.Inject;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;



public class ComprobanteReserva extends Dialog {

	private Reserva reserva;
	@Autowired
	private ConfiguracionService configService;

	private static final String configuracionVencimientoReserva = "reserva_vencimiento_reserva-dias";
	private static final String configuracionVencimientoPagoParcial = "reserva_vencimiento_pago_parcial-dias";
	private static final String configuracionPorcentajePagoParcial = "reserva_porcentaje_pago_parcial-cifra";

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
	private TextField origen = new TextField("Origen");
	private TextField precioUnitario = new TextField("Precio Unitario:");	
	private TextField precioRestante = new TextField("Precio Restante a Pagar:");	
	private TextField pagos = new TextField("Pago Realizado:");
	private TextField cantPasajes = new TextField("Cant. de Pasajes Reservados:");

	private TextField precio = new TextField("Precio:");


	private TextArea reglasCancelacion = new TextArea("Política de Cancelación");

	public ComprobanteReserva(Reserva venta) {
		Inject.Inject(this);
		this.reserva=venta;
		setComponents();
		setLayouts();
	}
	
	private void setComponents() {
		cliente.setValue(reserva.getCliente().getNombreyApellido());
		cliente.setReadOnly(true);
		destino.setValue(reserva.getViaje().getDestino().toString());
		destino.setReadOnly(true);
		destino.setWidth("242px");
		diaHora.setValue(reserva.getViaje().getFechaSalida().toString() + " " + reserva.getViaje().getHoraSalida().toString());
		diaHora.setReadOnly(true);
		diaHora.setWidth("142px");
		transporte.setValue(reserva.getViaje().getTransporte().getTipo().getDescripcion());
		transporte.setReadOnly(true);
		codTransporte.setValue(reserva.getViaje().getTransporte().getCodTransporte());
		codTransporte.setReadOnly(true);
		clase.setValue(reserva.getViaje().getTransporte().getClase());
		clase.setReadOnly(true);
		if(reserva.getViaje().getRecomendacion()!=null){
			recomendacion.setValue(reserva.getViaje().getRecomendacion());
		}
		recomendacion.setReadOnly(true);
		recomendacion.setWidth("608px");

		precio.setValue(reserva.getImporteTotal().toString());
		precio.setReadOnly(true);
		

		LocalDate fechaVencReserva = reserva.getViaje().getFechaSalida().minusDays(Long.parseLong(this.getConfiguracionDiasVencimientoPagoParcial()));
		reglasCancelacion.setReadOnly(true);
		reglasCancelacion.setValue("Debe pagar al menos el " + this.getConfiguracionPorcentajeReserva() + "% del valor total antes que finalice la fecha de la reserva (" + fechaVencReserva.toString() + "). "
				+ "El resto se debe pagar a lo sumo " + this.getConfiguracionDiasVencimientoReserva() + " días antes del viaje. De no cumplirse cualquiera de los dos casos se cancelará la reserva automáticamente.");
		reglasCancelacion.setHeight("125px");
		reglasCancelacion.setWidth("608px");

		dni.setReadOnly(true);
		dni.setValue(reserva.getCliente().getDni());
		dni.setWidth("102px");
		
		mail.setReadOnly(true);
		mail.setValue(reserva.getCliente().getEmail());
		mail.setWidth("282px");

		fechaDeEmision.setValue(LocalDate.now().toString());
		fechaDeEmision.setReadOnly(true);

		nombreOperador.setValue(reserva.getVendedor().getUser());
		nombreOperador.setReadOnly(true);

		origen.setReadOnly(true);
		origen.setValue(reserva.getViaje().getOrigen().getNombre());

		precioUnitario.setReadOnly(true);
		precioUnitario.setValue(reserva.getViaje().getPrecio().toString());

		pagos.setReadOnly(true);
		pagos.setValue(this.getSumaDePagos().toString());

		cantPasajes.setReadOnly(true);
		Integer cantidadPasajes = reserva.getCantidadPasajes();
		cantPasajes.setValue(cantidadPasajes.toString());
		
		precioRestante.setReadOnly(true);
		Double sumaDePagos = reserva.getImporteTotal() - this.getSumaDePagos();
		precioRestante.setValue(sumaDePagos.toString());

	}


	private void setLayouts() {
		this.add(mainLayout);
		mainLayout.setSpacing(false);
		mainLayout.add(getLogoMasFechaEmision(),new HorizontalLayout(cliente,dni,mail),new HorizontalLayout(origen,diaHora,destino),new HorizontalLayout(transporte,codTransporte,clase),recomendacion,cantPasajes,new HorizontalLayout(precio,precioUnitario, pagos),precioRestante, reglasCancelacion);
		this.setWidth("800px");
		this.setHeight("100%");
	}

	private HorizontalLayout getLogoMasFechaEmision() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");
		return new HorizontalLayout(logo, title, fechaDeEmision, nombreOperador);
	}

	private Double getSumaDePagos() {
		Double sumaDePagos = 0.0;
		if (reserva.getPagos() != null) {
			if (reserva.getPagos().size() > 0) {
				Iterator<Pago> pagosIterator = this.reserva.getPagos().iterator();
				while(pagosIterator.hasNext()){
					Pago elemento = pagosIterator.next();
					sumaDePagos = sumaDePagos + elemento.getImporte();
				}
			}
		}
		return sumaDePagos;
	}

	private String getConfiguracionDiasVencimientoReserva() {
		return configService.findValueByKey(configuracionVencimientoReserva);
	}

	private String getConfiguracionPorcentajeReserva(){
		return configService.findValueByKey(configuracionPorcentajePagoParcial);      
	}

	private String getConfiguracionDiasVencimientoPagoParcial(){
		return configService.findValueByKey(configuracionVencimientoPagoParcial);   
	}
	
}