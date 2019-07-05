package com.tp.proyecto1.controllers.reserva;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Component
public class ReservaREST {
	@Autowired
	private ConfiguracionService configService;
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
	@Autowired
	private ReservaREST() {
		instancia = this;
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

	public static boolean esReservablePorFecha(Viaje viaje) {
		LocalDateTime presente = LocalDate.now().atStartOfDay();
		LocalDateTime fechaViaje = viaje.getFechaSalida().atStartOfDay();                  
		Integer diasAntesDelViaje = Integer.parseInt(instancia.getConfiguracionDiasVencimientoReserva());
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
			Integer diasAntesDelViaje = Integer.parseInt(instancia.getConfiguracionDiasVencimientoPagoParcial());
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
		return Double.valueOf(instancia.getConfiguracionPorcentajeReserva()); 
	}
}