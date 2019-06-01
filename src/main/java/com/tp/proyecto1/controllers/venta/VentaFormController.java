package com.tp.proyecto1.controllers.venta;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.pasajes.*;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.services.*;
import com.tp.proyecto1.utils.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.views.ventas.VentaForm;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.*;


@Controller
@UIScope
public class VentaFormController {
	
	private VentaForm ventaForm;

	@Autowired
	private VentaService ventaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ViajeService viajeService;
	
	@Autowired
	private ReservaService reservaService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	private  PromocionService promocionService;
	
	private ChangeHandler changeHandler;

	private Venta venta;

	private Viaje viaje;
	
	private Reserva reserva;
	
	private Integer cantPasajesReserva;

	private Integer cantPuntosPorVenta;
	
	private Double precioFinal;
	
	private Binder<PasajeVenta> binderPasajeVenta;
	private Binder<Venta> binderVenta;
	private Binder<Viaje> binderViaje;  //NO LOS USO
	private Binder<Cliente> binderCliente;//NO LOS USO
	
	private Double pagoParcial; 
	
	public VentaFormController(Viaje viaje) {
		Inject.Inject(this);
		this.viaje = viaje;
		this.ventaForm = new VentaForm();
		this.setListeners();
        this.setComponents();
        this.setComponentesLectura(this.viaje);
	}
	
	public VentaFormController(Reserva reserva) {
		Inject.Inject(this);
		this.reserva = reserva;
		this.ventaForm = new VentaForm();
		this.setListeners(); 
		this.setComponents();
		this.setComponentesLectura(this.reserva.getViaje()); 		
	}
	
	private void setComponentesLectura(Viaje viaje) {
		ventaForm.getPais().setItems(viajeService.findAllPaises());
		ventaForm.getPais().setValue(viaje.getCiudad().getPais());
		ventaForm.getCiudad().setValue(viaje.getCiudad());
		ventaForm.getCodTransporte().setValue(viaje.getTransporte().getCodTransporte());
		ventaForm.getTransporte().setValue(viaje.getTransporte().getTipo().getDescripcion());
		ventaForm.getFechaSalida().setValue(viaje.getFechaSalida().toString());
		ventaForm.getHoraSalida().setValue(viaje.getHoraSalida().toString());
		ventaForm.getPais().setReadOnly(true);
		ventaForm.getCiudad().setReadOnly(true);
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
		ventaForm.getPais().setItems(viajeService.findAllPaises());
		ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		ventaForm.setSaldoPagarDouble(0.0);
		ventaForm.setSubtotalDouble(0.0); 
		
		this.setPromociones();
		
		if (this.reserva != null){
			//Setear Cliente
			ventaForm.getCliente().getFiltro().setValue(reserva.getCliente().getId().toString());
			ventaForm.getCliente().getFiltro().setEnabled(false);
			
			//Setear cantidad de pasajes como uno de los obligatorios para terminar la venta
			this.cantPasajesReserva =  this.reserva.getCantidadPasajes();
			
			//Setear Forma de Pago si la hay y saldo a pagar y subtotal
			if (this.reserva.getPagos().size() == 0) { //sin pagos previos
				ventaForm.setSaldoPagarDouble(reserva.getImporteTotal());//precio al momento de la reserva
				ventaForm.setSubtotalDouble(reserva.getImporteTotal());  //precio al momento de la reserva
			}
			else{//con pagos previos
				this.pagoParcial = 0.0;
				Iterator<Pago> pagosIterator = this.reserva.getPagos().iterator();
				while(pagosIterator.hasNext()){
					Pago elemento = pagosIterator.next();
					
					ventaForm.getFormaPago().setValue(elemento.getFormaDePago()); 
					
					this.pagoParcial = this.pagoParcial + elemento.getImporte();
				}
				ventaForm.setSaldoPagarDouble(reserva.getImporteTotal()-this.pagoParcial);
				ventaForm.setSubtotalDouble(reserva.getImporteTotal()-this.pagoParcial);
			}
		}
	}
	
	private void setPromociones() {
		Set<Promocion> promoCiudad = new HashSet<>();
		Set<Promocion> promoViaje = new HashSet<>();
		
		if (this.reserva !=null) {
			promoCiudad = promocionService.findByCiudadesAfectadas(this.reserva.getViaje().getCiudad());
			promoViaje = promocionService.findByViajesAfectados(this.reserva.getViaje());
		}
		else {
			promoCiudad = promocionService.findByCiudadesAfectadas(this.viaje.getCiudad());
			promoViaje = promocionService.findByViajesAfectados(this.viaje);
		}
		
		Collection<Promocion> itemsPromo = new HashSet<>();
		
		if (promoViaje.size() > 0)  itemsPromo.addAll(promoViaje);
		if (promoCiudad.size() > 0) itemsPromo.addAll(promoCiudad);
		
		if (!itemsPromo.isEmpty()) {
			itemsPromo.stream().filter(promo -> promo.isActivo()).collect(Collectors.toList()); //deja solo las promociones no vencidas
			this.ventaForm.getPromocion().setItems(itemsPromo);	
		}
	}

	private void setListeners() {
		ventaForm.getPais().addValueChangeListener(e->setComboCiudades());

		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta());//en el modo compra pasajes de viajes, y para reserva venta
		ventaForm.getFormaPago().addValueChangeListener(e-> validarCompra());
		ventaForm.getCliente().getFiltro().addValueChangeListener(e-> validarCompra());
		ventaForm.getPasajerosGridComponent().getRemoveLastButton().addClickListener(e-> this.modificarSaldoaPagar());
		ventaForm.getPasajerosGridComponent().getNewPasajero().addClickListener(e-> this.modificarSaldoaPagar());
		ventaForm.getPromocion().addValueChangeListener(e-> this.modificarDenoPromo());
		ventaForm.getPromocion().addValueChangeListener(e-> this.modificarSaldoaPagar());
		
	}

	private void modificarDenoPromo() {
		this.ventaForm.getDenoPromocion().setSuffixComponent(null);
		this.ventaForm.getDenoPromocion().setPrefixComponent(null);
		this.ventaForm.getDenoPromocion().setValue("");
		
		Promocion promo = this.ventaForm.getPromocion().getValue();
		
		if ( promo != null) {
			if(promo.getTipoPromocion().equals("Puntos")) {
				this.ventaForm.getDenoPromocion().setPrefixComponent(new Span("X"));
			}
			else if(promo.getTipoPromocion().equals("Descuento")) {
				this.ventaForm.getDenoPromocion().setSuffixComponent(new Span("%"));
			}
			this.ventaForm.getDenoPromocion().setValue(promo.getDoubleValue().toString());
		}
	}

	private void setComboCiudades() {

		Pais pais = ventaForm.getPais().getValue();
		ventaForm.getCiudad().setItems(pais.getCiudades());

	}
	
	public void setComponentsValues(Venta venta) {

		this.venta = venta;

		ventaForm.getPais().setValue(venta.getViaje().getCiudad().getPais());
		ventaForm.getCiudad().setValue(venta.getViaje().getCiudad());
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

	}
	
	
	private void validarCantPasajes() {
		//Si viene de una reserva
		if (this.reserva !=null) {
			if (this.cantPasajesReserva == this.ventaForm.getPasajerosGridComponent().getPasajerosList().size()) {
				ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(false);
			}
			else {
				ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(true);//este no sirve
			}
		}
		//validar que se pueda habilitar el boton de finalizar compra
		this.validarCompra();
	}
	
	//Incrementa o decrementa los campos de saldo a pagar y subtotal dependiendo la cant. de pasajeros
	private void modificarSaldoaPagar() {
		if (this.reserva == null) {
			Double precio = viaje.getPrecio();
			ventaForm.getSubtotal().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
			//ventaForm.getSaldoPagar().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
			ventaForm.getSaldoPagar().setValue(ventaForm.getSubtotal().getValue());
			
			this.generarDescuentos();
			this.generarPuntos();
			
			//validar que se pueda habilitar el boton de finalizar compra
			this.validarCompra();
		}
		else {
			ventaForm.getSaldoPagar().setValue(ventaForm.getSubtotal().getValue());
			
			this.generarDescuentos();
			this.generarPuntos();
			
			this.validarCantPasajes();
		}
	}
	
	private void newVenta() {
		Venta venta =  setNewVenta();
		ventaService.save(venta);
		//ventaService.save(setNewVenta());
        ventaForm.close();
      //Si viene de una reserva
        if (this.reserva != null) {
        	this.reserva.inactivar();
        	this.reserva.setEstadoTransaccion(EstadoTransaccion.VENDIDA);
            this.reservaService.save(reserva);
        }
        changeHandler.onChange();
        Notification.show("PasajeVenta comprado. Lote de Puntos Conseguidos");		
	}

	private void saveVenta(Venta venta) {

            ventaService.save(venta);
            ventaForm.close();
            Notification.show("PasajeVenta Guardado");
            changeHandler.onChange();
	}
	
	private Venta setNewVenta() {
		this.precioFinal = 0.0;
		Venta venta = new Venta();
		venta.setSucursal(Proyecto1Application.sucursal);
		venta.setVendedor(Proyecto1Application.logUser);
		venta.setFecha(LocalDate.now());

		Cliente cliente = ventaForm.getCliente().getCliente();
		FormaDePago formaPago = ventaForm.getFormaPago().getValue();

		venta.setCliente(cliente);		
		
		Pago pagoVenta = new Pago();
		//PAGOS: Si viene de una reserva
		if (this.reserva !=null) {
			
			Pago pagoReserva = new Pago(venta, null, pagoParcial, LocalDate.now());
			venta.agregarPago(pagoReserva);
						
			pagoVenta = new Pago(venta, formaPago, ventaForm.getSaldoPagar().getValue(),  LocalDate.now());//tiro null pointer al hacer la prueba q esta en la hoja
			venta.agregarPago(pagoVenta);
			
			this.viaje = reserva.getViaje();
			
			this.precioFinal = pagoReserva.getImporte() + pagoVenta.getImporte();
			
			venta.setEstadoTransaccion(EstadoTransaccion.VENDIDA);
		}
		else {
			this.precioFinal = ventaForm.getSaldoPagar().getValue();
			
			pagoVenta = new Pago(venta, formaPago, this.precioFinal,  LocalDate.now());//tiro null pointer al hacer la prueba q esta en la hoja
			venta.agregarPago(pagoVenta);
			
			
			venta.setEstadoTransaccion(EstadoTransaccion.CREADA);
			}
		
		ventaForm.getPasajerosGridComponent().getPasajerosList().stream().forEach(pasajero->{
			PasajeVenta pasajeVenta = new PasajeVenta(viaje, cliente);
			pasajeVenta.setPasajero(pasajero);
			venta.getPasajes().add(pasajeVenta);
		});		
		viaje.restarPasajes(venta.getPasajes().size());
		venta.setViaje(viaje);
		viajeService.save(viaje);
		
		if(ventaForm.getSaldoPagar().getValue() > 0) {
			LocalDate fechaVencimiento = LocalDate.now().plusYears(Integer.parseInt(this.getCantAniosVencimientoPuntos()));
			
			LotePunto lotePunto = new LotePunto(LocalDate.now(), fechaVencimiento, this.cantPuntosPorVenta , true, this.cantPuntosPorVenta, cliente);
			cliente.agregarPuntos(lotePunto);
			venta.setCliente(cliente);
			clienteService.save(cliente);
		}
				
		venta.setImporteTotal(this.precioFinal);//si es el caso que viene de reserva le paso igual el importe total, sino faltaria un campo en que diga importeVenta/parcial
		
		return venta;
	}
	
	//en la seccion de uso de puntos colocar aplicar puntos
//	if (ventaForm.getUsoPuntosCheck().getValue()/*colocar campos que no tienen que estar vacios a la hora de esta validacion*/) {
//		this.modificarSaldoPagarPorPuntos();
//	}
//	
//	//Descuento por promocion sobre el precio total
//	this.modificarSaldoPagarPorPromocion();	
//

	private void generarPuntos() {
		Integer pesosPorPunto = Integer.parseInt(this.getPesosPorPunto());
		this.cantPuntosPorVenta =  this.ventaForm.getSaldoPagar().getValue().intValue()/pesosPorPunto;
		
		//Verificar si tiene bonificacion de puntos de promocion y aplicarlos
		Promocion promoPuntos = new Promocion();
		promoPuntos = this.ventaForm.getPromocion().getValue();
		if (promoPuntos != null) {
			if (promoPuntos.getTipoPromocion().equals("Puntos")) this.cantPuntosPorVenta = promoPuntos.getDoubleValue() * this.cantPuntosPorVenta;
		}
		this.ventaForm.getPuntosObtenidos().setValue(this.cantPuntosPorVenta.toString());
	}

	private void generarDescuentos() {
		//Verificar si tiene descuento de promocion y aplicarlos
		Promocion promoDescuento = new Promocion();
		promoDescuento = this.ventaForm.getPromocion().getValue();
		if (promoDescuento != null) {
			if (promoDescuento.getTipoPromocion().equals("Descuento")) this.ventaForm.getSaldoPagar().setValue(this.ventaForm.getSaldoPagar().getValue() * ((100 - promoDescuento.getDoubleValue().doubleValue())/100) );
		}
		else {
			this.ventaForm.getSaldoPagar().setValue(this.ventaForm.getSubtotal().getValue());
		}
	}


	private void generarPuntosDeVenta(Cliente cliente, Venta venta) {
		if (this.precioFinal > 0) {
			Integer pesosPorPunto = Integer.parseInt(this.getPesosPorPunto());
			this.cantPuntosPorVenta =  this.precioFinal.intValue()/pesosPorPunto;
			
			//Verificar si tiene bonificacion de puntos de promocion y aplicarlos
			Promocion promoPuntos = new Promocion();
			promoPuntos = this.ventaForm.getPromocion().getValue();
			if (promoPuntos.getTipoPromocion().equals("Puntos")) this.cantPuntosPorVenta = promoPuntos.getDoubleValue() * this.cantPuntosPorVenta;
			
			LocalDate fechaVencimiento = LocalDate.now().plusYears(Integer.parseInt(this.getCantAniosVencimientoPuntos()));
			
			LotePunto lotePunto = new LotePunto(LocalDate.now(), fechaVencimiento, this.cantPuntosPorVenta , true, this.cantPuntosPorVenta, cliente);
			cliente.agregarPuntos(lotePunto);
			venta.setCliente(cliente);
			clienteService.save(cliente);
		}		
	}

/*
	private LocalDate agregarAnios(int cantAnios) {
		Calendar fechaVencimiento = Calendar.getInstance();
		Date fecha = new Date();
		fechaVencimiento.setTime(fecha);
		fechaVencimiento.add(Calendar.YEAR, cantAnios);

		return fechaVencimiento.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
*/


	private void validarCompra() {
		boolean isValido = false; 
		if(ventaForm.getFormaPago().getValue() != null && !ventaForm.getCliente().getFiltro().getValue().isEmpty()) {
			if (this.reserva != null) {
				if (this.cantPasajesReserva == ventaForm.getPasajerosGridComponent().getPasajerosList().size()) {
					isValido = true;
				}
			}
			else {
				if (ventaForm.getPasajerosGridComponent().getPasajerosList().size() > 0) {
					isValido = true;
				}
			}
		}
		ventaForm.getBtnFinalizarCompra().setEnabled(isValido);
	}
	
    //NO USO ESTA PODEROSA TECNICA
	private void setBinders() {
		binderVenta.setBean(venta);
		
	}
	
	public VentaForm getVentaFormCompra() {
		ventaForm.getBtnSave().setVisible(false);
		ventaForm.getBtnFinalizarCompra().setEnabled(false);
		return ventaForm;
	}
	
	public VentaForm getVentaReservaFormCompra() {
		ventaForm.getPasajerosGridComponent().getRemoveLastButton().setVisible(false);
		ventaForm.getBtnSave().setVisible(false);
		ventaForm.getBtnFinalizarCompra().setEnabled(false);
		return ventaForm;
	}
	
	public VentaForm getVentaFormEdit() {
		ventaForm.getBtnFinalizarCompra().setVisible(false);
		return ventaForm;
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public void setVentaForm(VentaForm ventaForm) {
		this.ventaForm = ventaForm;
	}
	
	private String getPesosPorPunto() {
		return configuracionService.findValueByKey("pesos_por_punto");
	}
	
	private String getCantAniosVencimientoPuntos() {
		return configuracionService.findValueByKey("cant_anios_venc_puntos");
	}
}