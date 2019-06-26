package com.tp.proyecto1.views.reportes;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.LotePuntoService;
import com.tp.proyecto1.utils.Inject;
import com.vaadin.flow.component.html.Span;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.view.JasperViewer;

public class ComprobanteVentaJR {

    private JasperReport reporte;
    //private JasperViewer reporteViewer;
    private JasperPrint  reporteLleno;
    @Autowired
	private ClienteService clienteService;
	@Autowired
	private LotePuntoService lotePuntoService;
	@Autowired
	private ConfiguracionService configuracionService;
	
	private String puntosTotales;
	private String puntosObtenidos;
	private String puntosUsados;
	private String valorPromocion;
	private String denoPromocion;
	
	
    private static final Logger log = LoggerFactory.getLogger(ComprobanteVentaJR.class);
    
    //Recibe la lista de personas para armar el reporte
    public ComprobanteVentaJR(List<Venta> ventas)
    {
       Inject.Inject(this);
       //Hardcodeado
       this.setPuntosDisponibles(ventas.get(ventas.size()-1));
       this.setDatosPromocion(ventas.get(ventas.size()-1));
       List<Pasajero> pasajeros = ventas.get(ventas.size()-1).getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
       String nroComprobante = ventas.get(ventas.size()-1).getId().toString(); 
       
       /* Convert List to JRBeanCollectionDataSource */
       //JRBeanCollectionDataSource pasajerosJRBean = new JRBeanCollectionDataSource(pasajeros);

	   Map<String, Object> parametersMap = new HashMap<String, Object>();
	   parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
	   parametersMap.put("PuntosTotales", puntosTotales);   
	   parametersMap.put("PuntosObtenidos", puntosObtenidos);   
	   parametersMap.put("PuntosUsados", puntosUsados);   
	   parametersMap.put("ValorPromo", valorPromocion);   
	   parametersMap.put("DenoPromo", denoPromocion);   
	   parametersMap.put("Pasajeros", pasajeros);
	   parametersMap.put("NroComprobante", nroComprobante);
	   parametersMap.put("logo", getClass().getResourceAsStream("/logo-viaje.png"));
	   parametersMap.put("qr", getClass().getResourceAsStream("/code-qr.png"));

       try {
		   InputStream is = getClass().getResourceAsStream("/ComprobanteVentaJR.jasper");
           this.reporte = (JasperReport)JRLoader.loadObject(is);
           this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(ventas));
           log.info("Se cargó correctamente el comprobante");
       }
       catch( JRException ex ) 
       {
    	   log.error("Ocurrió un error mientras se cargaba el archivo ComprobanteVentaJR.Jasper", ex);
       }
    }       
    
    public void exportarAPdf(String nombreComprobante) {
    	 // Exporta el informe a PDF
        try {
			JasperExportManager.exportReportToPdfFile(reporteLleno, "./"+ nombreComprobante + ".pdf");
			log.error("Se exporto correctamente el comprobante");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			log.error("Ocurrió un error mientras se exportaba el archivo ComprobanteVentaJR.Jasper", e);
			e.printStackTrace();
		}
    }
    /*
    public void mostrar()
    {
                   this.reporteViewer = new JasperViewer(this.reporteLleno,false);
                   this.reporteViewer.setVisible(true);
    }*/
    
    private void setPuntosDisponibles(Venta venta) {

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
    		puntosTotales = cantPuntosTotales.toString();
    		puntosUsados = venta.getPagos().get(cantPagos).getPuntosUsados().toString();
    		puntosObtenidos = puntos.toString();
		}
	}
    private String getPesosPorPunto() {
		return configuracionService.findValueByKey("pesos_por_punto");
	}
    
    private void setDatosPromocion(Venta venta) {
    	
		if ( venta.getPromocion() != null) {
			if(venta.getPromocion().getTipoPromocion().equals("Puntos")) {
				this.valorPromocion = "X"+venta.getPromocion().getDoubleValue().toString();
			}
			else if(venta.getPromocion().getTipoPromocion().equals("Descuento")) {
				this.valorPromocion = venta.getPromocion().getDoubleValue().toString() + "%";
			}
			this.denoPromocion = venta.getPromocion().getNombrePromocion();

		}
		else {
			this.valorPromocion = "-";
			this.denoPromocion ="-";
		}

	}
   
}              
