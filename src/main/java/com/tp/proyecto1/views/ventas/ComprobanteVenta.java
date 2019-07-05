package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.LotePuntoService;
import com.tp.proyecto1.utils.Inject;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	private TextField puntosDisponibles	= new TextField("Puntos Disponibles | Usados | Obtenidos:");
	private TextArea reglasCancelacion = new TextArea("Política de Cancelación");

	private String promo = "Promoción de";
	private Label pasajerosTitle = new Label("Pasajeros:");
	private PasajerosGridComponent pasajeros = new PasajerosGridComponent();

	private TextField precio = new TextField("Precio Total:");

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private LotePuntoService lotePuntoService;

	@Autowired
	private ConfiguracionService configuracionService;


	public ComprobanteVenta(Venta venta) {

		Inject.Inject(this);
		this.venta=venta;
		setComponents();
		setLayouts();
	}
	private void setComponents() {
		cliente.setValue(venta.getCliente().getNombreyApellido());
		cliente.setReadOnly(true);
		destino.setValue(venta.getViaje().getDestino().toString());
		destino.setReadOnly(true);
		destino.setWidth("242px");
		diaHora.setValue(venta.getViaje().getFechaSalida().toString() + " " + venta.getViaje().getHoraSalida().toString());
		diaHora.setReadOnly(true);
		diaHora.setWidth("142px");
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
		this.pasajeros.setEditarInvisible(Boolean.FALSE);
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
		reglasCancelacion.setHeight("102px");
		reglasCancelacion.setWidth("608px");

		dni.setReadOnly(true);
		dni.setValue(venta.getCliente().getDni());
		dni.setWidth("102px");
		
		mail.setReadOnly(true);
		mail.setValue(venta.getCliente().getEmail());	
		mail.setWidth("282px");
		
		precioUnitario.setReadOnly(true);
		precioUnitario.setValue(venta.getViaje().getPrecio().toString());
		
		precioSubtotal.setReadOnly(true);
		precioSubtotal.setValue(venta.getSubtotal().toString());

		this.setDatosPromocion();

		puntosDisponibles.setReadOnly(true);
		this.setPuntosDisponibles();	

	}



	private void setLayouts() {
		this.add(mainLayout);
		mainLayout.setSpacing(false);
		mainLayout.add(getLogoMasFechaEmision(),new HorizontalLayout(cliente,dni,mail), new HorizontalLayout(origen,diaHora,destino),new HorizontalLayout(transporte,codTransporte,clase),
			recomendacion,pasajerosTitle,pasajeros,new HorizontalLayout(puntosDisponibles,denoPromocion,valorPromocion), new HorizontalLayout(precio,precioSubtotal,precioUnitario),reglasCancelacion);
		this.setWidth("800px");
		this.setHeight("100%");
	}
	
	private HorizontalLayout getLogoMasFechaEmision() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");
		return new HorizontalLayout(logo, title, fechaDeEmision, nombreOperador);
	}

	private void setPuntosDisponibles() {

		Cliente clienteSeleccionado = new Cliente();
		Optional<Cliente> cliente = clienteService.findById(venta.getCliente().getId());
		Integer cantPuntosTotales;
		cantPuntosTotales = 0;

		if(cliente.isPresent()){
            clienteSeleccionado = cliente.get();
            List<LotePunto> lotesPuntos = lotePuntoService.findAllByCliente(clienteSeleccionado);
            List<LotePunto> lotesPuntosActivos = new ArrayList<>();
            lotesPuntosActivos = lotesPuntos.stream().filter(lote -> lote.getActivo()).collect(Collectors.toList());//deja solo los lotes  no vencidos
        	for (LotePunto lote : lotesPuntosActivos) {
        		cantPuntosTotales += lote.getCantidadRestante();
        	}
        	Integer cantPagos = venta.getPagos().size() - 1;
        	Integer pesosPorPunto = Integer.parseInt(this.getPesosPorPunto());
    		Integer puntos = new Double(venta.getImporteTotal()/pesosPorPunto).intValue();
        	puntosDisponibles.setValue(cantPuntosTotales.toString() + " | " + venta.getPagos().get(cantPagos).getPuntosUsados().toString() +
        			" | " + puntos.toString());
        	puntosDisponibles.setWidth("270px");
		}
	}

	private void setDatosPromocion() {
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
			this.valorPromocion.setReadOnly(true);
			this.valorPromocion.setWidth("114px");
			this.denoPromocion.setValue(venta.getPromocion().getNombrePromocion());
			this.denoPromocion.setReadOnly(true);
		}

	}

	private String getPesosPorPunto() {
		return configuracionService.findValueByKey("pesos_por_punto");
	}
}