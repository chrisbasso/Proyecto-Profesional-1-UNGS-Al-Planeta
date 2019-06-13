package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Modulo;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.contabilidad.TipoPosicion;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.utils.Inject;
@Component
public class AsientoREST {
	private static AsientoREST instancia;
	private AsientoService asientoService;
	private Asiento asiento;
	private Cabecera cabecera;
	private List<Posicion> posiciones;
	private Cuenta cuentaReserva;
	private Cuenta cuentaVenta;
	private Cuenta cuentaPagoEfvo;
	private Cuenta cuentaPagoBanco;
	private Cuenta cuentaPagoTarjeta;
	private Cuenta cuentaCliente;	
	private Cuenta cuentaReservaVencida;
	private Cuenta cuentaSalidaCaja;
	private Double importePagoEfectivo;
	private Double importePagoDebito;
	private Double importePagoTarjeta;
	private Double importePagoCuentaCorriente;
	
	@Autowired
	private AsientoREST(AsientoService asientoService) {
		this.asientoService = asientoService;
		cabecera = new Cabecera();
		posiciones = new ArrayList<Posicion>();
		cuentaReserva = asientoService.findCuentaByNumero(202);
		cuentaVenta = asientoService.findCuentaByNumero(400);
		cuentaPagoEfvo = asientoService.findCuentaByNumero(100);
		cuentaPagoBanco = asientoService.findCuentaByNumero(101);
		cuentaPagoTarjeta = asientoService.findCuentaByNumero(102);
		cuentaCliente = asientoService.findCuentaByNumero(103);
		cuentaReservaVencida= asientoService.findCuentaByNumero(401);
		cuentaSalidaCaja= asientoService.findCuentaByNumero(100);
		importePagoEfectivo = 0.0;
		importePagoDebito = 0.0;
		importePagoTarjeta = 0.0;
		importePagoCuentaCorriente= 0.0;
		instancia = this;
	}
	
	public static Long contabilizarNuevaReserva(Reserva reserva) {
		AsientoREST nuevoAsiento = instancia;
		nuevoAsiento.setCabeceraAsiento(reserva.getFecha(), reserva.getVendedor(), reserva.getSucursal(), "Contabilización de reserva", Modulo.RESERVAS);
		Double sumaDePagos = 0.0;
		if(reserva.getPagos().size()>0) {
			sumaDePagos = nuevoAsiento.tratarNuevosPagos(reserva.getPagos());
		}
		if(sumaDePagos > 0.0) {
			nuevoAsiento.cerrarAsientoReserva(sumaDePagos);
			return nuevoAsiento.contabilizarAsiento();
		}
		return 0L;
	}
	
	public static Long contabilizarDevolucionReserva(Reserva reserva) {
		AsientoREST nuevoAsiento = instancia;
		nuevoAsiento.setCabeceraAsiento(reserva.getFechaInactivacion(), reserva.getVendedor(), reserva.getSucursal(), "Devolución de reserva", Modulo.RESERVAS);
		Double sumaDePagos = 0.0;
		if(reserva.getPagos().size()>0) {
			sumaDePagos = nuevoAsiento.tratarDevolucionPagos(reserva.getPagos());
		}
		if(sumaDePagos > 0.0) {
			nuevoAsiento.cerrarAsientoDevolucionReserva(sumaDePagos);
			return nuevoAsiento.contabilizarAsiento();
		}
		return 0L;
	}
	
	public static Long contabilizarReservaVencida(Reserva reserva, User usuarioBatch) {
		AsientoREST nuevoAsiento = instancia;
		nuevoAsiento.setCabeceraAsiento(LocalDate.now(), usuarioBatch, reserva.getSucursal(), "Reserva vencida", Modulo.RESERVAS);
		Double sumaDePagos = 0.0;
		if(reserva.getPagos().size()>0) {
			sumaDePagos = nuevoAsiento.tratarGananciaPagos(reserva.getPagos());
		}
		if(sumaDePagos > 0.0) {
			nuevoAsiento.cerrarAsientoReservaVencida(sumaDePagos);
			return nuevoAsiento.contabilizarAsiento();
		}
		return 0L;
	}
	
	public static Long contabilizarNuevaVenta(Venta venta) {
		AsientoREST nuevoAsiento = instancia;
		nuevoAsiento.setCabeceraAsiento(venta.getFecha(), venta.getVendedor(), venta.getSucursal(), "Contabilización de venta", Modulo.VENTAS);
		Double sumaDePagos = 0.0;
		if(venta.getPagos().size()>0) {
			sumaDePagos = nuevoAsiento.tratarNuevosPagos(venta.getPagos());
		}
		if(sumaDePagos > 0.0) {
			nuevoAsiento.cerrarAsientoVenta(sumaDePagos);
			return nuevoAsiento.contabilizarAsiento();
		}
		return 0L;
	}
	
	public static Long contabilizarSalidaCaja(LocalDate fecha, String txtCab, Sucursal suc,
			Cuenta cta, Double impte, User usuario) {
		
		AsientoREST nuevoAsiento = instancia;
		nuevoAsiento.setCabeceraAsiento(fecha, usuario, suc, txtCab, Modulo.TESORERIA);
		nuevoAsiento.agregarPosicion(TipoPosicion.DEBE, cta, impte);
		nuevoAsiento.cerrarAsientoSalidaPago(impte);
		return nuevoAsiento.contabilizarAsiento();
	}
	
	public static Long anularAsiento(Asiento asientoPorAnular, User usuario){
		AsientoREST nuevoAsiento = instancia;
		nuevoAsiento.setCabeceraAsiento(LocalDate.now(), usuario, asientoPorAnular.getSucursal(),"Anular asiento: " + asientoPorAnular.getId(), asientoPorAnular.getCabecera().getModulo());
		
		for(Posicion posicion : asientoPorAnular.getPosiciones()){
			Posicion posicionRevertida = Posicion.revertirPosicion(posicion);
			nuevoAsiento.agregarPosicion(posicionRevertida);
		}
		
		Long idAnulacion = nuevoAsiento.contabilizarAsiento();
		nuevoAsiento.anular(asientoPorAnular, usuario);
		return idAnulacion;
	}
	
	private void anular(Asiento asientoPorAnular, User usuario) {
		asientoPorAnular.setAnulado(usuario);
		asientoService.save(asientoPorAnular);		
	}

	private void setCabeceraAsiento(LocalDate fecha,User usuario,Sucursal sucursal, String textoCabecera, Modulo modulo) {
		cabecera.setFechaRegistro(fecha);
		cabecera.setFechaContabilizacion(fecha);		
		cabecera.setUsuario(usuario);
		cabecera.setTextoCabecera(textoCabecera);
		cabecera.setSucursal(sucursal);
		cabecera.setModulo(modulo);
	}
	
	private Double tratarNuevosPagos(List <Pago> pagos) {
		if(pagos.size() == 0) {
			return 0.0;
		}				
		sumarizarPagos(pagos);		
		crearPosicionesPagos(TipoPosicion.DEBE);		
		return importePagoEfectivo + importePagoDebito + importePagoTarjeta;
	}

	private Double tratarDevolucionPagos(List <Pago> pagos) {
		if(pagos.size() == 0) {
			return 0.0;
		}				
		sumarizarPagos(pagos);		
		crearPosicionesPagos(TipoPosicion.HABER);		
		return importePagoEfectivo + importePagoDebito + importePagoTarjeta;
	}

	private Double tratarGananciaPagos(List <Pago> pagos) {
		if(pagos.size() == 0) {
			return 0.0;
		}				
		sumarizarPagos(pagos);				
		return importePagoEfectivo + importePagoDebito + importePagoTarjeta;
	}

	private void sumarizarPagos(List<Pago> pagos) {
		for(Pago pago : pagos) {
			switch (pago.getFormaDePago().getDescripcion()) {
			case "Efectivo":
				importePagoEfectivo += pago.getImporte();
				break;
			case "Débito":
				importePagoDebito += pago.getImporte();
				break;
			case "Crédito":
				importePagoTarjeta += pago.getImporte();
				break;
			case "Cuenta Corriente":
				importePagoCuentaCorriente += pago.getImporte();
				break;
			default:
				break;
			} 
		}
	}
	
	private void crearPosicionesPagos(TipoPosicion tipoPosicion) {
		if(importePagoEfectivo >0.0) {
			agregarPosicion(tipoPosicion, cuentaPagoEfvo, importePagoEfectivo);
		}
		if(importePagoDebito >0.0) {
			agregarPosicion(tipoPosicion, cuentaPagoBanco, importePagoDebito);
		}
		if(importePagoTarjeta >0.0) {
			agregarPosicion(tipoPosicion, cuentaPagoTarjeta, importePagoTarjeta);
		}
		if(importePagoCuentaCorriente >0.0) {
			agregarPosicion(tipoPosicion, cuentaCliente, importePagoCuentaCorriente);
		}
	}

	private void agregarPosicion(TipoPosicion debeHaber, Cuenta cuenta, Double importe) {
		posiciones.add(new Posicion(debeHaber, cuenta, importe));
	}
	
	private void agregarPosicion(Posicion posicion){
		posiciones.add(posicion);
	}
	
	private void cerrarAsientoReserva(Double sumaDePagos) {
		agregarPosicion(TipoPosicion.HABER, cuentaReserva, sumaDePagos);
	}
	
	private void cerrarAsientoDevolucionReserva(Double sumaDePagos) {
		agregarPosicion(TipoPosicion.DEBE, cuentaReserva, sumaDePagos);
	}
	
	private void cerrarAsientoReservaVencida(Double sumaDePagos) {
		agregarPosicion(TipoPosicion.DEBE, cuentaReserva, sumaDePagos);
		agregarPosicion(TipoPosicion.HABER, cuentaReservaVencida, sumaDePagos);
	}
	
	private void cerrarAsientoVenta(Double sumaDePagos) {
		agregarPosicion(TipoPosicion.HABER, cuentaVenta, sumaDePagos);
	}
	
	private void cerrarAsientoSalidaPago(Double impte) {
		agregarPosicion(TipoPosicion.HABER, cuentaSalidaCaja, impte);
	}
	
	private Long contabilizarAsiento() {
		asiento = new Asiento();
		asiento.setCabecera(cabecera);
		asiento.setPosiciones(posiciones);		
		return asientoService.save(asiento);
	}	
}