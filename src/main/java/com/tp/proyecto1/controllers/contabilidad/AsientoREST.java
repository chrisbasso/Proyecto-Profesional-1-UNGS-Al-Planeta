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
		cabecera = new Cabecera();
		posiciones = new ArrayList<Posicion>();
		cuentaReserva = asientoService.findCuenta(202);
		cuentaVenta = asientoService.findCuenta(400);
		cuentaPagoEfvo = asientoService.findCuenta(100);
		cuentaPagoBanco = asientoService.findCuenta(101);
		cuentaPagoTarjeta = asientoService.findCuenta(102);
		cuentaCliente = asientoService.findCuenta(103);
		cuentaReservaVencida= asientoService.findCuenta(401);
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
		nuevoAsiento.setCabeceraAsientoReserva(reserva.getFecha(), reserva.getVendedor(), reserva.getSucursal());
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
		nuevoAsiento.setCabeceraAsientoReserva(reserva.getFechaInactivacion(), reserva.getVendedor(), reserva.getSucursal());
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
		nuevoAsiento.setCabeceraAsientoReserva(LocalDate.now(), usuarioBatch, reserva.getSucursal());
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
		nuevoAsiento.setCabeceraAsientoReserva(venta.getFecha(), venta.getVendedor(), venta.getSucursal());
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
	
	private void setCabeceraAsientoReserva(LocalDate fecha,User usuario,Sucursal sucursal) {
		cabecera.setFechaRegistro(fecha);
		cabecera.setFechaContabilizacion(fecha);		
		cabecera.setUsuario(usuario);
		cabecera.setTextoCabecera("Contabilización de reserva");
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