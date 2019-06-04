package com.tp.proyecto1.controllers.reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.ViajeService;

public class ReservaREST {
	@Autowired
	private ConfiguracionService ConfigService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ViajeService viajeService;

	private static ReservaREST instancia;
	private static final String configuracionVencimientoReserva = "reserva_vencimiento_reserva-dias";
	private static final String configuracionVencimientoPagoParcial = "reserva_vencimiento_pago_parcial-dias";
	private static final String configuracionPorcentajePagoParcial = "reserva_porcentaje_pago_parcial-cifra";

	private ReservaREST() {
	}            

	private static ReservaREST getInstancia(){                           
		if(instancia != null){
			return instancia;
		}else{
			instancia = new ReservaREST();
			return instancia;
		}                          
	}

	private String getConfiguracionDiasVencimientoReserva() {
		return ConfigService.findValueByKey(configuracionVencimientoReserva);
	}

	private String getConfiguracionPorcentajeReserva(){
		return ConfigService.findValueByKey(configuracionPorcentajePagoParcial);      
	}

	private String getConfiguracionDiasVencimientoPagoParcial(){
		return ConfigService.findValueByKey(configuracionVencimientoPagoParcial);   
	}

	public static boolean esReservablePorFecha(Viaje viaje) {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();                  
		Integer diasAntesDelViaje = Integer.parseInt(getInstancia().getConfiguracionDiasVencimientoReserva());
		if(diasAntesDelViaje != null) {
			return fechaViaje.minusDays(diasAntesDelViaje).isAfter(presente);
		}
		// Si no hay configuración de fecha maxima no interrumpo la
		// operación comercial.
		return true;
	}

	public static boolean esAnulablePorVencimientoPago(Reserva reserva){
		if(reservaAbonadaCompletamente(reserva)) {
			return false;
		}else {
			LocalDateTime presente = LocalDate.now().atStartOfDay();
			LocalDateTime fechaViaje = reserva.getViaje().getFechaSalida().atStartOfDay();
			Integer diasAntesDelViaje = Integer.parseInt(getInstancia().getConfiguracionDiasVencimientoPagoParcial());
			if(diasAntesDelViaje != null) {
				return fechaViaje.minusDays(diasAntesDelViaje).isAfter(presente);
			}else {
				return false;
			}	
		}				
	}

	private static boolean reservaAbonadaCompletamente(Reserva reserva) {
		return reserva.getImporteTotal() <= reserva.getTotalPagado();
	}
	
	public static boolean esAnulablePorVencimientoFechaReserva(Reserva reserva) {
		if(reservaAbonadaCompletamente(reserva)) {
			return false;
		}else {
			return !esReservablePorFecha(reserva.getViaje()); 	
		}
	}
	
	public static Double getConfiguracionPorcentajePagoReserva() {
		return Double.valueOf(getInstancia().getConfiguracionPorcentajeReserva()); 
	}
}