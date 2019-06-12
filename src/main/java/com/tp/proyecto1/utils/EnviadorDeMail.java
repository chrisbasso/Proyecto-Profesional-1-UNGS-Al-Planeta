package com.tp.proyecto1.utils;

import java.io.File;
import java.util.Properties;

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

import com.vaadin.flow.component.dialog.Dialog;

import ch.qos.logback.classic.Logger;

public class EnviadorDeMail {
	
	public EnviadorDeMail() {
		
	}
	
	public static void enviarConGmail(String destinatario, String asunto, String cuerpo) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = "proy.despegue";  //Para la dirección nomcuenta@gmail.com

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    //props.put("mail.smtp.clave", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.password", "Laboratorio1");    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); 
	    
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    Multipart multipart = new MimeMultipart(); 
	    
	    BodyPart parteAdjunto = new MimeBodyPart(); 
	    BodyPart texto = new MimeBodyPart();
	    
	   	File path = new File("C:/Users/User/Desktop/comprobantes/comprobante-venta.pdf"); 
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
}
