package com.tp.proyecto1.utils;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class CreadorPDF {

	static PDDocument nuevoDocumento;
	static float anchoDocumento;
	static float altoDocumento;
	static PDPage primeraPagina;
	static PDRectangle mediaBoxPagina;
	static PDPageContentStream contentStream;
	static PDImageXObject logo;
	static String nombreArchivo = "test.pdf";
	
	public static void generarPdf(String cuerpo, String footer) {
		inicializar();
		cargarContenido(cuerpo, footer);
		cerrarYGuardar();		
	}

	private static void inicializar() {
		nuevoDocumento = new PDDocument();	       
		primeraPagina = new PDPage(PDRectangle.A4);
		mediaBoxPagina = primeraPagina.getMediaBox();
		altoDocumento = primeraPagina.getMediaBox().getHeight();
		anchoDocumento = primeraPagina.getMediaBox().getWidth();
		nuevoDocumento.addPage(primeraPagina);
	}

	private static void cargarContenido(String detalle, String footer) {
		try {
			contentStream = new PDPageContentStream(nuevoDocumento, primeraPagina);
			cargarHeader("Generado el: " + LocalDate.now().toString());
			cargarBody(detalle);
			cargarFooter(footer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cargarHeader(String fecha) {
		try {
			// Triangulo cabecera
			PDRectangle mediaBoxPagina = primeraPagina.getMediaBox();
			contentStream.setNonStrokingColor(Color.CYAN);
			contentStream.addRect(0, mediaBoxPagina.getHeight()-100, mediaBoxPagina.getWidth(), mediaBoxPagina.getHeight());
			contentStream.fill();
			
			//Agregar el logo
			logo = PDImageXObject.createFromFile("logo-viaje.png", nuevoDocumento);
			float coordXLogo = 5;
			float coordYLogo = mediaBoxPagina.getHeight()- (logo.getHeight()/2);
			float scale = 0.3f; // alter this value to set the image size
			contentStream.drawImage(logo, coordXLogo, coordYLogo, logo.getWidth()*scale, logo.getHeight()*scale);

			//Agregar el titulo
			String titulo = "Al Planeta - Agencia de viajes";
			PDFont fuenteTitulo = PDType1Font.HELVETICA_OBLIQUE;			
			int marginTop = 10;
			int tamanoFuente = 16;
			float anchoTitulo = fuenteTitulo.getStringWidth(titulo) / 1000 * tamanoFuente;
			float altoTitulo = fuenteTitulo.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * tamanoFuente;
			float coordXTitulo = (mediaBoxPagina.getWidth() - anchoTitulo) / 2;
			float coordYTitulo = mediaBoxPagina.getHeight() - marginTop - altoTitulo;
			contentStream.beginText();
			contentStream.setFont(fuenteTitulo, tamanoFuente);
			contentStream.setNonStrokingColor(Color.BLUE);	
			contentStream.newLineAtOffset(coordXTitulo, coordYTitulo);
			contentStream.showText(titulo);
			contentStream.endText();			
			 
			//Agregar fecha y otros datos			
			String otros = fecha;
			PDFont fuenteOtros = PDType1Font.HELVETICA_OBLIQUE;
			int marginTopOtros = 10;
			int tamanoFuenteOtros = 12;
			float anchoOtros = fuenteOtros.getStringWidth(otros) / 1000 * tamanoFuente;
			float altoOtros = fuenteOtros.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * tamanoFuenteOtros;
			float coordXOtros = mediaBoxPagina.getWidth() - anchoOtros;
			float coordYOtros = coordYTitulo - altoOtros;
			
			contentStream.beginText();
			contentStream.setFont(fuenteOtros, tamanoFuenteOtros);				
			contentStream.newLineAtOffset(coordXOtros, coordYOtros);
			contentStream.showText(otros);
			contentStream.endText();			

			contentStream.setNonStrokingColor(Color.BLACK);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cargarBody(String texto) {

		try {
			PDFont pdfFont = PDType1Font.HELVETICA;
			float fontSize = 10;
			float leading = 1.5f * fontSize;

			float margin = 50;
			float width = anchoDocumento - 2*margin;
			float startX = mediaBoxPagina.getLowerLeftX() + margin;
			float startY = mediaBoxPagina.getUpperRightY() - margin;

			List<String> lineas = new ArrayList<String>();
			int ultimoEspacio = -1;
			while (texto.length() > 0){
				int indiceEspacio = texto.indexOf(' ', ultimoEspacio + 1);
				if (indiceEspacio < 0)
					indiceEspacio = texto.length();
				String subString = texto.substring(0, indiceEspacio);
				float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
				if (size > width){
					if (ultimoEspacio < 0)
						ultimoEspacio = indiceEspacio;
					subString = texto.substring(0, ultimoEspacio);
					lineas.add(subString);
					texto = texto.substring(ultimoEspacio).trim();
					ultimoEspacio = -1;
				}else if (indiceEspacio == texto.length()){
					lineas.add(texto);
					texto = "";
				}else{
					ultimoEspacio = indiceEspacio;
				}
			}

			contentStream.beginText();
			contentStream.setFont(pdfFont, fontSize);
			contentStream.setLeading(14.5f);
			contentStream.newLineAtOffset(startX, startY);            
			for (String line: lineas){			    
				contentStream.showText(line);
				contentStream.newLine();
			}
			contentStream.endText();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cargarFooter(String text) {
		try {
			//Begin the Content stream 
			contentStream.beginText(); 
			//Setting the font to the Content stream  
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			//Setting the position for the line 
			contentStream.newLineAtOffset(25, 25);
			//Adding text in the form of string 
			contentStream.showText(text);      
			//Ending the content stream
			contentStream.endText();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cerrarYGuardar() {
		try {
			contentStream.close();
			nuevoDocumento.save(nombreArchivo);
			nuevoDocumento.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
