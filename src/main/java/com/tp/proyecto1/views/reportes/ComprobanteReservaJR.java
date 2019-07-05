package com.tp.proyecto1.views.reportes;

import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.utils.Inject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
//import net.sf.jasperreports.view.JasperViewer;

public class ComprobanteReservaJR {

    private JasperReport reporte;
    //private JasperViewer reporteViewer;
    private JasperPrint  reporteLleno;
	@Autowired
	private ConfiguracionService configuracionService;
	
	private String precioRestante;
	private String pasajeros;
	private String politicasCancelacion;
	private String pagos;
	
	
	private static final String configuracionVencimientoReserva = "reserva_vencimiento_reserva-dias";
	private static final String configuracionVencimientoPagoParcial = "reserva_vencimiento_pago_parcial-dias";
	private static final String configuracionPorcentajePagoParcial = "reserva_porcentaje_pago_parcial-cifra";
	
    private static final Logger log = LoggerFactory.getLogger(ComprobanteReservaJR.class);
    
    
    //Recibe la lista de personas para armar el reporte
    public ComprobanteReservaJR(List<Reserva> reservas)
    {
       Inject.Inject(this);
       //Hardcodeado
       Double precioRestanteNum = reservas.get(reservas.size()-1).getImporteTotal() - this.getSumaDePagos(reservas.get(reservas.size()-1));
       precioRestante = precioRestanteNum.toString();
       Integer pasajerosNum = reservas.get(reservas.size()-1).getCantidadPasajes();
       pasajeros = pasajerosNum.toString();
       Double pagosNum = this.getSumaDePagos(reservas.get(reservas.size()-1));
       pagos = pagosNum.toString();
       LocalDate fechaVencReserva =  reservas.get(reservas.size()-1).getViaje().getFechaSalida().minusDays(Long.parseLong(this.getConfiguracionDiasVencimientoPagoParcial()));
       politicasCancelacion = "Debe pagar al menos el " + this.getConfiguracionPorcentajeReserva() + "% del valor total antes que finalice la fecha de la reserva (" + fechaVencReserva.toString() + ")."
				+  " El resto se debe pagar a lo sumo " + this.getConfiguracionDiasVencimientoReserva() + " días antes del viaje. De no cumplirse cualquier de los dos casos se cancelará la reserva automáticamente.";
       
       String nroComprobante = reservas.get(reservas.size()-1).getId().toString(); 


	   Map<String, Object> parametersMap = new HashMap<String, Object>();
	   parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date())); 
	   parametersMap.put("Politica", politicasCancelacion);   
	   parametersMap.put("PrecioRestante", precioRestante);   
	   parametersMap.put("Pasajeros", pasajeros);
	   parametersMap.put("Pagos", pagos);
	   parametersMap.put("NroComprobante", nroComprobante);
		parametersMap.put("logo", getClass().getResourceAsStream("/logo-viaje.png"));
		parametersMap.put("qr", getClass().getResourceAsStream("/code-qr.png"));
	   
       try {

		   InputStream is = getClass().getResourceAsStream("/ComprobanteReservaJR.jasper");
		   this.reporte = (JasperReport)JRLoader.loadObject(is);
           this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(reservas));
           log.info("Se cargó correctamente el comprobante");
       }
       catch( JRException ex ) 
       {
    	   log.error("Ocurrió un error mientras se cargaba el archivo ComprobanteReservaJR.Jasper", ex);
       }
    }       
    
    public void exportarAPdf(String nombreComprobante) {
    	 // Exporta el informe a PDF
        try {
			JasperExportManager.exportReportToPdfFile(reporteLleno, "./"+ nombreComprobante + ".pdf");
			log.error("Se exporto correctamente el comprobante");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			log.error("Ocurrió un error mientras se exportaba el archivo ComprobanteReservaJR.Jasper", e);
			e.printStackTrace();
		}
    }
    /*
    public void mostrar()
    {
                   this.reporteViewer = new JasperViewer(this.reporteLleno,false);
                   this.reporteViewer.setVisible(true);
    }*/
    
    private Double getSumaDePagos(Reserva reserva) {
		Double sumaDePagos = 0.0;
		if (reserva.getPagos() != null) {
			if (reserva.getPagos().size() > 0) {
				Iterator<Pago> pagosIterator = reserva.getPagos().iterator();
				while(pagosIterator.hasNext()){
					Pago elemento = pagosIterator.next();
					sumaDePagos = sumaDePagos + elemento.getImporte();
				}
			}
		}
		return sumaDePagos;
	}

    private String getConfiguracionDiasVencimientoReserva() {
		return configuracionService.findValueByKey(configuracionVencimientoReserva);
	}

	private String getConfiguracionPorcentajeReserva(){
		return configuracionService.findValueByKey(configuracionPorcentajePagoParcial);      
	}

	private String getConfiguracionDiasVencimientoPagoParcial(){
		return configuracionService.findValueByKey(configuracionVencimientoPagoParcial);   
	}
}              
