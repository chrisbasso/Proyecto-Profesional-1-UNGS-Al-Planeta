package com.tp.proyecto1.controllers.contabilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.contabilidad.TipoPosicion;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.utils.Inject;

public class AsientoREST {

	private static AsientoREST instancia;
	@Autowired
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
	private Double importePagoEfectivo;
	private Double importePagoDebito;
	private Double importePagoTarjeta;
	private Double importePagoCuentaCorriente;

	private AsientoREST() {
		Inject.Inject(this);
		cabecera = new Cabecera();
		posiciones = new ArrayList<Posicion>();
		cuentaReserva = asientoService.findCuentaByNumero(202);
		cuentaVenta = asientoService.findCuentaByNumero(400);
		cuentaPagoEfvo = asientoService.findCuentaByNumero(100);
		cuentaPagoBanco = asientoService.findCuentaByNumero(101);
		cuentaPagoTarjeta = asientoService.findCuentaByNumero(102);
		cuentaCliente = asientoService.findCuentaByNumero(103);
		cuentaReservaVencida= asientoService.findCuentaByNumero(401);
		importePagoEfectivo = 0.0;
		importePagoDebito = 0.0;
		importePagoTarjeta = 0.0;
		importePagoCuentaCorriente= 0.0;
	}

	private static AsientoREST getInstancia() {
		if(instancia != null) {
			return instancia;
		}else {
			instancia = new AsientoREST();
			return instancia;
		}		
	}
	
	public static Long contabilizarNuevaReserva(Reserva reserva) {
		AsientoREST nuevoAsiento = getInstancia();
		nuevoAsiento.setCabeceraAsiento(reserva.getFecha(), reserva.getVendedor(), reserva.getSucursal(), "Contabilización de reserva");
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
		AsientoREST nuevoAsiento = getInstancia();
		nuevoAsiento.setCabeceraAsiento(reserva.getFechaInactivacion(), reserva.getVendedor(), reserva.getSucursal(), "Devolución de reserva");
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
		AsientoREST nuevoAsiento = getInstancia();
		nuevoAsiento.setCabeceraAsiento(LocalDate.now(), usuarioBatch, reserva.getSucursal(), "Reserva vencida");
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
		AsientoREST nuevoAsiento = getInstancia();
		nuevoAsiento.setCabeceraAsiento(venta.getFecha(), venta.getVendedor(), venta.getSucursal(), "Contabilización de venta");
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
	
	private void setCabeceraAsiento(LocalDate fecha,User usuario,Sucursal sucursal, String textoCabecera) {
		cabecera.setFechaRegistro(fecha);
		cabecera.setFechaContabilizacion(fecha);		
		cabecera.setUsuario(usuario);
		cabecera.setTextoCabecera(textoCabecera);
		cabecera.setSucursal(sucursal);
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

	private Long contabilizarAsiento() {
		asiento = new Asiento();
		asiento.setCabecera(cabecera);
		asiento.setPosiciones(posiciones);		
		return asientoService.save(asiento);
	}	
}