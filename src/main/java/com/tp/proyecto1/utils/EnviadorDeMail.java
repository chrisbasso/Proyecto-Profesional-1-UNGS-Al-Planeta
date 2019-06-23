package com.tp.proyecto1.utils;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Pasajero;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Transaccion;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.LotePuntoService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;

import ch.qos.logback.classic.Logger;

public class EnviadorDeMail {
	@Autowired
	private  ClienteService clienteService;
	@Autowired
	private  LotePuntoService lotePuntoService;
	@Autowired
	private  ConfiguracionService configuracionService;
	
	private static String puntosUsadosCadena;
	private static String cantPuntosTotalesCadena;
	private static String puntosConseguidosCadena;
	private static String promo; 
	
	private static final String configuracionVencimientoReserva = "reserva_vencimiento_reserva-dias";
	private static final String configuracionVencimientoPagoParcial = "reserva_vencimiento_pago_parcial-dias";
	private static final String configuracionPorcentajePagoParcial = "reserva_porcentaje_pago_parcial-cifra";
	private static final String configuracionMailRemitenteGmail = "mail_remitente_gmail";
	private static final String configuracionMailRemitentePassGmail = "mail_remitente_pass_gmail";
	private static final String configuracionPuertoSeguroGoolge = "puerto_seguro_google";
			
	public EnviadorDeMail() {
		Inject.Inject(this);
	}
	
	public void enviarConGmail(String destinatario, String asunto, Venta venta) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
		String remitente = this.getConfiguracionMailRemitenteGmail();  //Para la dirección nomcuenta@gmail.com
	    String pass = this.getConfiguracionMailRemitentePassGmail();
	    Properties props = System.getProperties();
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		String pasajerosVenta = retornarPasajeros(pasajeros);
	    String cuerpo = "Estimado Cliente "+ venta.getCliente().getNombreyApellido() + ",\nSe le informa que su compra del viaje con salida desde la ciudad de " + venta.getViaje().getOrigen()
				+ " el día " + venta.getViaje().getFechaSalida().toString() + " a las " + venta.getViaje().getHoraSalida().toString() + " con destino a "
				+ venta.getViaje().getDestino().toString()+ " se encuentra aprobada.\n\nPasajeros y DNI: " + pasajerosVenta 
				+ "\n\n\nGracias por confiar en nosotros. \n\nBuen Viaje le desea Al Planeta.";//info del comproventa
	    
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    //props.put("mail.smtp.clave", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.password", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", this.getConfiguracionPuertoSeguroGoolge()); //El puerto SMTP seguro de Google
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 
	    
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    Multipart multipart = new MimeMultipart(); 
	    
	    BodyPart parteAdjunto = new MimeBodyPart(); 
	    BodyPart texto = new MimeBodyPart();
	    
	   	File path = new File("./"+venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni()+".pdf"); 
	   	if (path.exists()) System.out.println("existe la ruta del archivo");
	    
	    DataSource arch = new FileDataSource(path);   
	   
	    try {
	    	texto.setText(cuerpo);

	    	parteAdjunto.setDataHandler(new DataHandler(arch));
	    	parteAdjunto.setFileName(path.getName());

	    	//agregando texto y archivo adjunto
	        multipart.addBodyPart(texto	);
	        multipart.addBodyPart(parteAdjunto); 
	        
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setContent(multipart); 
	        //message.setText(cuerpo); //para enviar sin correo adjunto
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, pass);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (AddressException ae) {
            ae.printStackTrace();
        }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	    
	}
	
	
	public void enviarConGmail(String destinatario, String asunto, Reserva reserva) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = this.getConfiguracionMailRemitenteGmail();  //Para la dirección nomcuenta@gmail.com
	    String pass = this.getConfiguracionMailRemitentePassGmail();
	    Properties props = System.getProperties();
	
	    String cuerpo = "Estimado Cliente "+ reserva.getCliente().getNombreyApellido() + ",\nSe le informa que su reserva del viaje con salida desde la ciudad de " + reserva.getViaje().getOrigen()
				+ " el día " + reserva.getViaje().getFechaSalida().toString() + " a las " + reserva.getViaje().getHoraSalida().toString() + " con destino a "
				+ reserva.getViaje().getDestino().toString()+ " se encuentra aprobada.\n\n"
				+ "\n\n\nGracias por confiar en nosotros. \n\nBuen Viaje le desea Al Planeta.";//info del comproventa
	    
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    //props.put("mail.smtp.clave", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.password", pass);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", this.getConfiguracionPuertoSeguroGoolge()); //El puerto SMTP seguro de Google
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 
	    
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    Multipart multipart = new MimeMultipart(); 
	    
	    BodyPart parteAdjunto = new MimeBodyPart(); 
	    BodyPart texto = new MimeBodyPart();
	    
	   	File path = new File("./"+reserva.getCliente().getNombreyApellido()+ "-"+ reserva.getCliente().getDni()+".pdf"); 
	   	if (path.exists()) System.out.println("existe la ruta del archivo");
	    
	    DataSource arch = new FileDataSource(path);   
	   
	    try {
	    	texto.setText(cuerpo);

	    	parteAdjunto.setDataHandler(new DataHandler(arch));
	    	parteAdjunto.setFileName(path.getName());

	    	//agregando texto y archivo adjunto
	        multipart.addBodyPart(texto	);
	        multipart.addBodyPart(parteAdjunto); 
	        
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setContent(multipart); 
	        //message.setText(cuerpo); //para enviar sin correo adjunto
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, "Laboratorio1");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (AddressException ae) {
            ae.printStackTrace();
        }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	    
	}
	
	public void enviarConGmailVoucher(String destinatario, String asunto, Venta venta) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
		String remitente = this.getConfiguracionMailRemitenteGmail();  //Para la dirección nomcuenta@gmail.com
	    String pass = this.getConfiguracionMailRemitentePassGmail();
	    Properties props = System.getProperties();
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		String pasajerosVenta = retornarPasajeros(pasajeros);
	    String cuerpo = "Estimado Cliente "+ venta.getCliente().getNombreyApellido() + ",\nSe le informa que su viaje con salida desde la ciudad de " + venta.getViaje().getOrigen()
				+ " el día " + venta.getViaje().getFechaSalida().toString() + " a las " + venta.getViaje().getHoraSalida().toString() + " con destino a "
				+ venta.getViaje().getDestino().toString()+ " se encuentra muy pronto a salirt.\n\nPasajeros y DNI: " + pasajerosVenta 
				+ "\n\n\nGracias por confiar en nosotros. \n\nBuen Viaje le desea Al Planeta.";//info del comproventa
	    
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    //props.put("mail.smtp.clave", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.password", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", this.getConfiguracionPuertoSeguroGoolge()); //El puerto SMTP seguro de Google
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 
	    
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    Multipart multipart = new MimeMultipart(); 
	    
	    BodyPart parteAdjunto = new MimeBodyPart(); 
	    BodyPart texto = new MimeBodyPart();
	    
	   	File path = new File("./"+venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni()+".pdf"); 
	   	if (path.exists()) System.out.println("existe la ruta del archivo");
	    
	    DataSource arch = new FileDataSource(path);   
	   
	    try {
	    	texto.setText(cuerpo);

	    	parteAdjunto.setDataHandler(new DataHandler(arch));
	    	parteAdjunto.setFileName(path.getName());

	    	//agregando texto y archivo adjunto
	        multipart.addBodyPart(texto	);
	        multipart.addBodyPart(parteAdjunto); 
	        
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setContent(multipart); 
	        //message.setText(cuerpo); //para enviar sin correo adjunto
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, pass);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (AddressException ae) {
            ae.printStackTrace();
        }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	    
	}
	
	public void enviarMailConInfoVenta(String asunto, Venta venta) {
		String destinatario = venta.getCliente().getEmail();
		List<Pasajero> pasajeros = venta.getPasajes().stream().map(e-> e.getPasajero()).collect(Collectors.toList());
		String pasajerosVenta = retornarPasajeros(pasajeros);
	    setPuntosDisponibles(venta);
	    String promocionCadena = "";
	    setDatosPromocion(venta);
		if (promo != null) promocionCadena = promo + ".\n"; else promocionCadena = "\n";
		
		String cuerpo = "Estimado Cliente "+ venta.getCliente().getNombreyApellido() + ",\nSe le informa que su compra del viaje con salida desde la ciudad de " + venta.getViaje().getOrigen()
				+ " el día " + venta.getViaje().getFechaSalida().toString() + " a las " + venta.getViaje().getHoraSalida().toString() + " con destino a "
				+ venta.getViaje().getDestino().toString()+ " se encuentra aprobada.\n\nEl " + venta.getViaje().getTransporte().getTipo().getDescripcion() 
				+ " con Código " + venta.getViaje().getTransporte().getCodTransporte() + " será su transporte.\n\nPasajeros y DNI: " + pasajerosVenta 
				+ "\n\nResumen de Puntos: \n\tPuntos Disponibles: "+ cantPuntosTotalesCadena +"\n\tPuntos Usados: "+ puntosUsadosCadena + "\n\tPuntos Obtenidos: " + puntosConseguidosCadena 
				+ "\n\n"+ promocionCadena 
				+ "\nDesglose del Costo del viaje: \n\tPrecio Unitario: " + venta.getViaje().getPrecio().toString() + "\n\tPrecio Subtotal: " + venta.getSubtotal().toString()
				+ "\n\tPrecio Total: " +venta.getImporteTotal().toString()
				+ "\n\nOperador: " + venta.getVendedor().getUser()
				+ "\n\n\nPolíticas de Cancelación: \n\t\"Hasta cinco días antes de la salida del viaje se podrá cancelar y obtener el 100% de reintegro. Luego se retendrá un 20% del valor del pasaje por día, hasta llegar al día de salida, que ya no se podrá cancelar.\""
				+ "\n\nGracias por confiar en nosotros. \n\nBuen Viaje le desea Al Planeta.";//info del comproventa
		
		this.enviarMail(destinatario, asunto, cuerpo);
	}
	
	public void enviarMailConInfoVentaCancelacion(String asunto, Venta venta) {
		String destinatario = venta.getCliente().getEmail();
		
		String cuerpo = "Estimado Cliente "+ venta.getCliente().getNombreyApellido() + ",\nSe le informa que su compra del viaje con salida desde la ciudad de " + venta.getViaje().getOrigen()
				+ " el día " + venta.getViaje().getFechaSalida().toString() + " a las " + venta.getViaje().getHoraSalida().toString() + " con destino a "
				+ venta.getViaje().getDestino().toString()+ " se encuentra cancelada.\n"
				+ "\n\nOperador: " + venta.getVendedor().getUser()
				+ "\n\n\nPolíticas de Cancelación: \n\t\"Hasta cinco días antes de la salida del viaje se podrá cancelar y obtener el 100% de reintegro. Luego se retendrá un 20% del valor del pasaje por día, hasta llegar al día de salida, que ya no se podrá cancelar.\""
				+ "\n\nGracias por confiar en nosotros. \n\nSaludos, Al Planeta.";//info del comproventa
		
		this.enviarMail(destinatario, asunto, cuerpo);
	}

	public void enviarMailConInfoReserva(String asunto, Reserva reserva) {
		String destinatario = reserva.getCliente().getEmail();
		Integer cantPasajeros = reserva.getCantidadPasajes();
		String pasajerosVenta = cantPasajeros.toString();
		Double sumaDePagos = this.getSumaDePagos(reserva);
		LocalDate fechaVencReserva = reserva.getViaje().getFechaSalida().minusDays(Long.parseLong(this.getConfiguracionDiasVencimientoPagoParcial()));
		
		String cuerpo = "Estimado Cliente "+ reserva.getCliente().getNombreyApellido() + ",\nSe le informa que su reserva del viaje con salida desde la ciudad de " + reserva.getViaje().getOrigen()
				+ " el día " + reserva.getViaje().getFechaSalida().toString() + " a las " + reserva.getViaje().getHoraSalida().toString() + " con destino a "
				+ reserva.getViaje().getDestino().toString()+ " se encuentra en curso.\n\nEl " + reserva.getViaje().getTransporte().getTipo().getDescripcion() 
				+ " con Código " + reserva.getViaje().getTransporte().getCodTransporte() + " será su transporte.\n\nCantidad de Pasajeros: " + pasajerosVenta  
				+ "\n\nDesglose del Costo del viaje: \n\tPrecio Unitario: " + reserva.getViaje().getPrecio().toString() + "\n\tPago Realizado: " + sumaDePagos.toString()
				+ "\n\tPrecio Total: " + reserva.getImporteTotal().toString() + "\n\tPrecio Restante a Pagar: " + (reserva.getImporteTotal() - sumaDePagos)
				+ "\n\nOperador: " + reserva.getVendedor().getUser()
				+ "\n\n\nPolíticas de Cancelación: \n\t\"Debe pagar al menos el " + this.getConfiguracionPorcentajeReserva() + "% del valor total antes que finalice la fecha de la reserva (" + fechaVencReserva.toString() + ")."
				+  " El resto se debe pagar a lo sumo " + this.getConfiguracionDiasVencimientoReserva() + " días antes del viaje. De no cumplirse cualquier de los dos casos se cancelará la reserva automáticamente.\""
				+ "\n\nGracias por confiar en nosotros. \n\nBuen Viaje le desea Al Planeta.";//info del comproreserva

		this.enviarMail(destinatario, asunto, cuerpo);
		
	}
	
	public void enviarMail(String destinatario, String asunto, String cuerpo) {
		  // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = this.getConfiguracionMailRemitenteGmail();  //Para la dirección nomcuenta@gmail.com
	    String pass = this.getConfiguracionMailRemitentePassGmail();
	    
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    //props.put("mail.smtp.clave", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.password", pass);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", this.getConfiguracionPuertoSeguroGoolge()); //El puerto SMTP seguro de Google
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 
	    
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	   // Multipart multipart = new MimeMultipart(); 
	    
	  //  BodyPart parteAdjunto = new MimeBodyPart(); 
	   // BodyPart texto = new MimeBodyPart();
	    
	   	//File path = new File("C:/Users/User/Desktop/comprobantes/comprobante-venta.pdf"); 
	   	//if (path.exists()) System.out.println("existe la ruta del archivo");
	    
	    //DataSource arch = new FileDataSource(path);   
	   
	    try {
	    	//texto.setText(cuerpo);
	
	    //	parteAdjunto.setDataHandler(new DataHandler(arch));
	    //	parteAdjunto.setFileName(path.getName());
	
	    	//agregando texto y archivo adjunto
	      //  multipart.addBodyPart(texto	);
	        //multipart.addBodyPart(parteAdjunto); 
	        
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	       // message.setContent(multipart); 
	        message.setText(cuerpo); //para enviar sin correo adjunto
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, pass);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (AddressException ae) {
            ae.printStackTrace();
        }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
		
	}

	private String retornarPasajeros(List<Pasajero> pasajeros) {
		String cadenaPasajeros = "";
		Iterator<Pasajero> pasajerosIterator = pasajeros.iterator();
		while(pasajerosIterator.hasNext()){
			Pasajero elemento = pasajerosIterator.next();
			if(cadenaPasajeros == "") cadenaPasajeros = elemento.getNombreCompleto() + " - " + elemento.getDni();
			else if (pasajerosIterator.hasNext() == false) cadenaPasajeros = cadenaPasajeros +" y "+elemento.getNombreCompleto() + " - " + elemento.getDni() + ".";
			else cadenaPasajeros = cadenaPasajeros +", "+elemento.getNombreCompleto() + " - " + elemento.getDni();
		}
		return cadenaPasajeros;
	}
	
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
        	Integer pesosPorPunto = Integer.parseInt(getPesosPorPunto());
    		Integer puntosUsados = venta.getPagos().get(cantPagos).getPuntosUsados();
    		Integer puntosConseguidos = new Double(venta.getImporteTotal()/pesosPorPunto).intValue();
        	
    		cantPuntosTotalesCadena = cantPuntosTotales.toString();
        	puntosUsadosCadena = puntosUsados.toString();
        	puntosConseguidosCadena =  puntosConseguidos.toString();
		}
	}
	
	private String getPesosPorPunto() {
		return configuracionService.findValueByKey("pesos_por_punto");
	}
	
	
	private void setDatosPromocion(Venta venta) {
		if ( venta.getPromocion() != null) {
			String tipoPromo = "";
			if(venta.getPromocion().getTipoPromocion().equals("Puntos")) {
				tipoPromo = " - multiplica puntos X "+ venta.getPromocion().getDoubleValue().toString();;
				promo = "Promoción Puntos: ";
			}
			else if(venta.getPromocion().getTipoPromocion().equals("Descuento")) {
				tipoPromo = " - descuento del "+ venta.getPromocion().getDoubleValue().toString() + "%";
				promo = "Promoción Descuento: ";
			}
			promo =  promo + venta.getPromocion().getNombrePromocion() + tipoPromo;
			
		}

	}
	
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
	
	private String getConfiguracionMailRemitenteGmail() {
		return configuracionService.findValueByKey(configuracionMailRemitenteGmail);
	}

	private String getConfiguracionMailRemitentePassGmail(){
		return configuracionService.findValueByKey(configuracionMailRemitentePassGmail);      
	}

	private String getConfiguracionPuertoSeguroGoolge(){
		return configuracionService.findValueByKey(configuracionPuertoSeguroGoolge);   
	}

	public void enviarMailConInfoVentaCancelacion(String asunto, Reserva reserva) {
		String destinatario = reserva.getCliente().getEmail();
				
				String cuerpo = "Estimado Cliente "+ reserva.getCliente().getNombreyApellido() + ",\nSe le informa que su reserva del viaje con salida desde la ciudad de " + reserva.getViaje().getOrigen()
						+ " el día " + reserva.getViaje().getFechaSalida().toString() + " a las " + reserva.getViaje().getHoraSalida().toString() + " con destino a "
						+ reserva.getViaje().getDestino().toString()+ " se encuentra cancelada.\n"
						+ "\n\nOperador: " + reserva.getVendedor().getUser()
						+ "\n\n\nPolíticas de Cancelación: \n\t\"Hasta cinco días antes de la salida del viaje se podrá cancelar y obtener el 100% de reintegro. Luego se retendrá un 20% del valor del pasaje por día, hasta llegar al día de salida, que ya no se podrá cancelar.\""
						+ "\n\nGracias por confiar en nosotros. \n\nSaludos, Al Planeta.";//info del comproventa
				
				this.enviarMail(destinatario, asunto, cuerpo);
				
			}
}
