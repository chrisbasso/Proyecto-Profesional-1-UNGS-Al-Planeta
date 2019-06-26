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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.view.JasperViewer;

public class VoucherVentaJR {

    private JasperReport reporte;
    //private JasperViewer reporteViewer;
    private JasperPrint  reporteLleno;
	
	
    private static final Logger log = LoggerFactory.getLogger(VoucherVentaJR.class);
    
    //Recibe la lista de personas para armar el reporte
    public VoucherVentaJR(List<Venta> ventas)
    {
       Inject.Inject(this);
       //Hardcodeado
 
       /* Convert List to JRBeanCollectionDataSource */
       //JRBeanCollectionDataSource pasajerosJRBean = new JRBeanCollectionDataSource(pasajeros);

	   Map<String, Object> parametersMap = new HashMap<String, Object>();
	   parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
	   
       try {
		   InputStream is = getClass().getResourceAsStream("/VoucherVentaJR.jasper");
		   this.reporte = (JasperReport)JRLoader.loadObject(is);
           this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, new JRBeanCollectionDataSource(ventas));
           log.info("Se cargó correctamente el Voucher");
       }
       catch( JRException ex ) 
       {
    	   log.error("Ocurrió un error mientras se cargaba el archivo VoucherVentaJR.Jasper", ex);
       }
    }       
    
    public void exportarAPdf(String nombreVoucher) {
    	 // Exporta el informe a PDF
        try {
			JasperExportManager.exportReportToPdfFile(reporteLleno, "./"+ nombreVoucher + ".pdf");
			log.error("Se exporto correctamente el Voucher");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			log.error("Ocurrió un error mientras se exportaba el archivo VoucherVentaJR.Jasper", e);
			e.printStackTrace();
		}
    }
    /*
    public void mostrar()
    {
                   this.reporteViewer = new JasperViewer(this.reporteLleno,false);
                   this.reporteViewer.setVisible(true);
    }*/
    
   
}              
