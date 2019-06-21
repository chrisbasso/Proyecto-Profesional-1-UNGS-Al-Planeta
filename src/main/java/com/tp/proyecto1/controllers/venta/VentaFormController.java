package com.tp.proyecto1.controllers.venta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.controllers.contabilidad.AsientoREST;
import com.tp.proyecto1.model.pasajes.*;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Provincia;
import com.tp.proyecto1.services.*;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reportes.ComprobanteVentaJR;
import com.tp.proyecto1.views.ventas.ComprobanteVenta;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.EnviadorDeMail;
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
	
	@Autowired
	private LotePuntoService lotePuntoService;
	
	private ChangeHandler changeHandler;

	private Venta venta;

	private Viaje viaje;
	
	private Reserva reserva;
	
	private Integer cantPasajesReserva;

	private Integer cantPuntosPorVenta;
	
	private Integer cantPuntosDisponibles;
	
	private Double precioFinal;
	
	private Binder<PasajeVenta> binderPasajeVenta;
	private Binder<Venta> binderVenta;
	private Binder<Viaje> binderViaje;  //NO LOS USO
	private Binder<Cliente> binderCliente;//NO LOS USO
	
	private Double pagoParcial;

	private Integer puntosaUsarVenta; 
	private Integer puntosAUsar; 
	
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
		ventaForm.getContinenteDestino().setItems(viajeService.findAllContinente());
		ventaForm.getContinenteDestino().setValue(viaje.getDestino().getProvincia().getPais().getContinente());
		ventaForm.getContinenteOrigen().setItems(viajeService.findAllContinente());
		ventaForm.getContinenteOrigen().setValue(viaje.getOrigen().getProvincia().getPais().getContinente());
		ventaForm.getPaisDestino().setItems(viajeService.findAllPaises());
		ventaForm.getPaisDestino().setValue(viaje.getDestino().getProvincia().getPais());
		ventaForm.getPaisOrigen().setItems(viajeService.findAllPaises());
		ventaForm.getPaisOrigen().setValue(viaje.getOrigen().getProvincia().getPais());
		ventaForm.getProvinciaDestino().setItems(viajeService.findAllProvincias());
		ventaForm.getProvinciaDestino().setValue(viaje.getDestino().getProvincia());
		ventaForm.getProvinciaOrigen().setItems(viajeService.findAllProvincias());
		ventaForm.getProvinciaOrigen().setValue(viaje.getOrigen().getProvincia());
		ventaForm.getCiudadDEstino().setItems(viajeService.findAllCiudades());
		ventaForm.getCiudadDEstino().setValue(viaje.getDestino());
		ventaForm.getCiudadOrigen().setItems(viajeService.findAllCiudades());
		ventaForm.getCiudadOrigen().setValue(viaje.getOrigen());
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
		ventaForm.getPuntosDisponibles().setReadOnly(true);
	}	

	private void setComponents() {		
//		ventaForm.getProvincia().setItems(viajeService.findAllProvincias());
		ventaForm.getFormaPago().setItems(ventaService.findAllFomaDePagos());
		ventaForm.setSaldoPagarDouble(0.0);
		ventaForm.setSubtotalDouble(0.0); 
		ventaForm.getPuntosDisponibles().setEnabled(false);
		ventaForm.getPuntosaUsar().setEnabled(false);
		this.setPromociones();
		
		if (this.reserva != null){
			//Setear Cliente
			ventaForm.getCliente().getFiltro().setValue(reserva.getCliente().getId().toString());
			ventaForm.getCliente().getFiltro().setEnabled(false);
			
			this.setPuntosDisponibles();
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
			promoCiudad = promocionService.findByCiudadesAfectadas(this.reserva.getViaje().getDestino());
			promoViaje = promocionService.findByViajesAfectados(this.reserva.getViaje());
		}
		else {
			promoCiudad = promocionService.findByCiudadesAfectadas(this.viaje.getDestino());
			promoViaje = promocionService.findByViajesAfectados(this.viaje);
		}
		
		Collection<Promocion> itemsPromo = new HashSet<>();
		Collection<Promocion> itemsPromoActivos = new HashSet<>();
		
		if (promoViaje.size() > 0)  itemsPromo.addAll(promoViaje);
		if (promoCiudad.size() > 0) itemsPromo.addAll(promoCiudad);
		
		if (!itemsPromo.isEmpty()) {
			itemsPromoActivos = itemsPromo.stream().filter(promo -> promo.isActivo()).collect(Collectors.toList());//deja solo las promociones no vencidas
			
			this.ventaForm.getPromocion().setItems(itemsPromoActivos);	
		}
	}

	private void setListeners() {
//		ventaForm.getProvincia().addValueChangeListener(e->setComboCiudades());
		ventaForm.getBtnSave().addClickListener(e-> saveVenta(venta));//en el modo edit
		ventaForm.getBtnCancel().addClickListener(e->ventaForm.close());
		ventaForm.getBtnFinalizarCompra().addClickListener(e-> newVenta());//en el modo compra pasajes de viajes, y para reserva venta
		ventaForm.getFormaPago().addValueChangeListener(e-> validarCompra());
		ventaForm.getCliente().getFiltro().addValueChangeListener(e-> validarCompra());
		ventaForm.getCliente().getFiltro().addValueChangeListener(e-> setPuntosDisponibles());
		ventaForm.getPasajerosGridComponent().getRemoveLastButton().addClickListener(e-> this.modificarSaldoaPagar());
		ventaForm.getPasajerosGridComponent().getNewPasajero().addClickListener(e-> this.modificarSaldoaPagar());
		ventaForm.getPromocion().addValueChangeListener(e-> this.modificarDenoPromo());
		ventaForm.getPromocion().addValueChangeListener(e-> this.modificarSaldoaPagar());
		ventaForm.getUsoPuntosCheck().addValueChangeListener(e-> this.configSectorPuntos());
		//ventaForm.getPuntosaUsar().addValueChangeListener(e->this.modificarSaldoaPagar());
		ventaForm.getPuntosaUsar().addValueChangeListener(e->this.modificarSaldoPagarPorPuntos());
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

//	private void setComboCiudades() {
//
//		Provincia provincia = ventaForm.getProvincia().getValue();
//		ventaForm.getCiudad().setItems(provincia.getCiudades());
//
//	}
	
	public void setComponentsValues(Venta venta) {

		this.venta = venta;

//		ventaForm.getProvincia().setValue(venta.getViaje().getDestino().getProvincia());
//		ventaForm.getCiudad().setValue(venta.getViaje().getDestino());
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
			ventaForm.getPromocion().setValue(venta.getPromocion());
			ventaForm.getDenoPromocion().setValue(venta.getPromocion().getDoubleValue().toString());
		}
		ventaForm.getPuntosObtenidos().setReadOnly(true);
		ventaForm.getUsoPuntosCheck().setReadOnly(true);
		ventaForm.getPuntosaUsar().setReadOnly(true);
	}	
	
	private void setPuntosDisponibles() {
		if (!ventaForm.getCliente().getFiltro().getValue().isEmpty()){
			Cliente clienteSeleccionado = new Cliente();
			Optional<Cliente> cliente = clienteService.findById(Long.parseLong(ventaForm.getCliente().getFiltro().getValue()));
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
            	
            	this.ventaForm.getPuntosDisponibles().setValue(cantPuntosTotales.toString());
            	this.ventaForm.getPuntosaUsar().setMax(cantPuntosTotales);
			}
		}
	}
	
	private void restarPuntosaLotesDePuntos() {
		Cliente clienteSeleccionado = new Cliente();
		Optional<Cliente> cliente = clienteService.findById(Long.parseLong(ventaForm.getCliente().getFiltro().getValue()));

		if(cliente.isPresent()){
            clienteSeleccionado = cliente.get();
            List<LotePunto> lotesPuntos = lotePuntoService.findAllByCliente(clienteSeleccionado);
 
        	Integer puntoDisponibles = Integer.parseInt(this.ventaForm.getPuntosDisponibles().getValue());
        	this.puntosaUsarVenta = this.ventaForm.getPuntosaUsar().getValue().intValue();
        	List<LotePunto> lotesPuntosModificados = new ArrayList<>();
        	lotesPuntosModificados = lotesPuntos.stream().map(lote-> restarLotePunto(lote)).collect(Collectors.toList());//deja solo los lotes  no vencidos
        	for(LotePunto lotePunto : lotesPuntosModificados) {
        		lotePuntoService.save(lotePunto);
        	}
		}
		
			
	}
	
	private LotePunto restarLotePunto(LotePunto lote) {
		if (!(this.puntosaUsarVenta == 0)) {
			if(lote.getCantidadRestante() > this.puntosaUsarVenta) {
					lote.setCantidadRestante(lote.getCantidadRestante()-this.puntosaUsarVenta);
					this.puntosaUsarVenta = 0 ;
			}
			else if(lote.getCantidadRestante() < this.puntosaUsarVenta) {
				this.puntosaUsarVenta = this.puntosaUsarVenta - lote.getCantidadRestante();
				lote.setCantidadRestante(0);
			}
			else {
				lote.setCantidadRestante(0);
				this.puntosaUsarVenta = 0 ;
			}
			//lotePuntoService.save(lote);
		}
		return lote;
	}
	
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
		ventaForm.getUsoPuntosCheck().setEnabled(isValido);
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
		//validar que se pueda habilitar el boton de finalizar compra.
		this.validarCompra();
	}
	
	private void habilitarFinalizarCompra() {
		Boolean isActivo = Boolean.FALSE;
		if(ventaForm.getPuntosaUsar().getValue() != null) {
			Integer cantPuntosLimite = ventaForm.getSubtotal().getValue().intValue()/Integer.parseInt(this.getPesosPorPunto());
			if (ventaForm.getPuntosaUsar().getValue() >= 0 && ventaForm.getPuntosaUsar().getValue() <= cantPuntosLimite) {
				this.ventaForm.getBtnFinalizarCompra().setEnabled(true);
			}
			else {
				this.ventaForm.getBtnFinalizarCompra().setEnabled(false);
				isActivo = Boolean.FALSE;
			}
		
		}
	
	}

	private void configSectorPuntos() {
		Boolean isValido = this.ventaForm.getUsoPuntosCheck().getValue();
		
		this.ventaForm.getPuntosDisponibles().setEnabled(isValido);
		this.ventaForm.getPuntosaUsar().setEnabled(isValido);
		this.ventaForm.getPuntosaUsar().setMax(ventaForm.getSaldoPagar().getValue().intValue()/10);
		this.ventaForm.getPuntosaUsar().clear();
		
		this.ventaForm.getPasajerosGridComponent().getNewPasajero().setEnabled(!isValido);
		this.ventaForm.getPasajerosGridComponent().getRemoveLastButton().setEnabled(!isValido);
		this.ventaForm.getPromocion().setEnabled(!isValido);
		this.ventaForm.getCliente().setEnabled(!isValido);
		this.ventaForm.getFormaPago().setEnabled(!isValido);
		this.ventaForm.getBtnFinalizarCompra().setEnabled(!isValido);
		if (!isValido) this.modificarSaldoaPagar();
	}
	//Incrementa o decrementa los campos de saldo a pagar y subtotal dependiendo la cant. de pasajeros
	private void modificarSaldoaPagar() {
		if (this.reserva == null) {
			Double precio = viaje.getPrecio();
			ventaForm.getSubtotal().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
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
	 
	private void modificarSaldoPagarPorPuntos() {
		if (this.ventaForm.getUsoPuntosCheck().getValue()) {
			if ( this.ventaForm.getPuntosaUsar().getValue() != null) {
				Integer puntosaUsar = this.ventaForm.getPuntosaUsar().getValue().intValue();
				Integer cantPuntosDisponibles = Integer.parseInt(ventaForm.getPuntosDisponibles().getValue());
				if(puntosaUsar <= cantPuntosDisponibles ) {
					if (puntosaUsar >= 0) {
						Double precio = 1.0;
						if(viaje != null){
							precio = viaje.getPrecio();
							ventaForm.getSubtotal().setValue(precio * ventaForm.getPasajerosGridComponent().getPasajerosList().size());
						}
						
						ventaForm.getSaldoPagar().setValue(ventaForm.getSubtotal().getValue());
						
						Promocion promoDescuento = new Promocion();
						promoDescuento = this.ventaForm.getPromocion().getValue();
						if (promoDescuento != null) {
							if (promoDescuento.getTipoPromocion().equals("Descuento")) this.generarDescuentos();
						}
	
						Integer puntoPorPesos = Integer.parseInt(this.getPuntoPorPesos());
						Double saldoaPagar = this.ventaForm.getSaldoPagar().getValue();
						saldoaPagar = saldoaPagar - (puntosaUsar * puntoPorPesos);
						if (saldoaPagar >= 0) this.ventaForm.getSaldoPagar().setValue(saldoaPagar);					
						
						this.generarPuntos();
						this.habilitarFinalizarCompra();
					}	
				}
				else {
					this.ventaForm.getBtnFinalizarCompra().setEnabled(false);
					Notification.show("AVISO! Puntos a Usar mayores a Puntos Disponibles");
				}
			}
		}
	}

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
			if (promoDescuento.getTipoPromocion().equals("Descuento")) {
				this.ventaForm.getSaldoPagar().setValue(this.ventaForm.getSaldoPagar().getValue() * ((100 - promoDescuento.getDoubleValue().doubleValue())/100) );
			}
		}
		else {
			this.ventaForm.getSaldoPagar().setValue(this.ventaForm.getSubtotal().getValue());
		}
	}
	
	private void newVenta() {
		Venta venta =  setNewVenta();
		ventaService.save(venta);
		AsientoREST.contabilizarNuevaVenta(venta);
		imprimirComprobante(venta);
		//ventaService.save(setNewVenta());
        ventaForm.close();
      //Si viene de una reserva
        if (this.reserva != null) {
        	this.reserva.inactivar();
        	this.reserva.setEstadoTransaccion(EstadoTransaccion.VENDIDA);
            this.reservaService.save(reserva);
        }
        changeHandler.onChange();
        //Notification.show("PasajeVenta comprado. Lote de Puntos Conseguidos");

	}

	private void saveVenta(Venta venta) {

            ventaService.save(venta);
            imprimirComprobante(venta);
            ventaForm.close();
            Notification.show("PasajeVenta Modificado");
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
		
		//puntos obtenidos de la compra
		if(ventaForm.getSaldoPagar().getValue() > 0) {
			LocalDate fechaVencimiento = LocalDate.now().plusYears(Integer.parseInt(this.getCantAniosVencimientoPuntos()));
			
			LotePunto lotePunto = new LotePunto(LocalDate.now(), fechaVencimiento, this.cantPuntosPorVenta , true, this.cantPuntosPorVenta, cliente);
			cliente.agregarPuntos(lotePunto);
			venta.setCliente(cliente);
			clienteService.save(cliente);
		}
		
		String formaPagoPuntos;///mejora siguiente version, que setee la nueva forma de pago
		if(this.ventaForm.getPuntosaUsar().getValue()!=null) {
			if(this.ventaForm.getPuntosaUsar().getValue() > 0.0 ) this.restarPuntosaLotesDePuntos();
			if(ventaForm.getSaldoPagar().getValue() == 0.0) formaPagoPuntos = "Puntos";
			else formaPagoPuntos = formaPago.getDescripcion() + " + Puntos";
			
			formaPago.setDescripcion(formaPagoPuntos);
		}
		
		if (ventaForm.getPuntosaUsar().getValue() != null) this.puntosAUsar = ventaForm.getPuntosaUsar().getValue().intValue();
		else this.puntosAUsar = 0;
		
		Pago pagoVenta = new Pago();
		//PAGOS: Si viene de una reserva
		if (this.reserva !=null) {
			
			Pago pagoReserva = new Pago(venta, null, pagoParcial, LocalDate.now());//le pongo null por ahora sino traer lo q tenia en reserva
			venta.agregarPago(pagoReserva);
						
			pagoVenta = new Pago(venta, formaPago, ventaForm.getSaldoPagar().getValue(),  LocalDate.now());
			pagoVenta.setPuntosUsados(puntosAUsar);
			venta.agregarPago(pagoVenta);
			
			this.viaje = reserva.getViaje();
			
			this.precioFinal = pagoReserva.getImporte() + pagoVenta.getImporte();
			
			venta.setEstadoTransaccion(EstadoTransaccion.VENDIDA);
		}
		else {
			this.precioFinal = ventaForm.getSaldoPagar().getValue();
			
			pagoVenta = new Pago(venta, formaPago, this.precioFinal,  LocalDate.now());
			pagoVenta.setPuntosUsados(puntosAUsar);
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
			
		if(this.ventaForm.getPromocion().getValue() != null) {
			Promocion promocion = new Promocion();
			promocion = this.ventaForm.getPromocion().getValue();
			Integer cantPasajes = ventaForm.getPasajerosGridComponent().getPasajerosList().size();
			if (promocion.getCantidadPasajesRestantes()!= null) if(promocion.getCantidadPasajesRestantes() > 0)	promocion.restarPasajes(cantPasajes);
			venta.setPromocion(promocion);
			promocionService.save(promocion);			
		}
		venta.setSubtotal(ventaForm.getSubtotal().getValue());
		venta.setImporteTotal(this.precioFinal);//si es el caso que viene de reserva le paso igual el importe total, sino faltaria un campo en que diga importeVenta/parcial
		
		return venta;
	}
	
	private void imprimirComprobante(Venta venta) {
		EnviadorDeMail enviadorDeMail = new EnviadorDeMail();
		List<Venta> ventas = new ArrayList<Venta>();
		ventas.add(venta);
		ComprobanteVentaJR comproVenta = new ComprobanteVentaJR(ventas);
		comproVenta.exportarAPdf(venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni());
		enviadorDeMail.enviarConGmail(venta.getCliente().getEmail(),
				"Comprobante de compra- " + venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni(), venta);
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
	
	private String getPuntoPorPesos() {
		return configuracionService.findValueByKey("punto_por_pesos");
	}
}