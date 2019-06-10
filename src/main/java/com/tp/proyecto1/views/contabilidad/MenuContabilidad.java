package com.tp.proyecto1.views.contabilidad;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class MenuContabilidad extends HorizontalLayout{

	private HorizontalLayout mainLayout;
	private HorizontalLayout botonera;
	private HorizontalLayout etiquetas;
	private VerticalLayout asientos;
	private VerticalLayout cuentas;
	private VerticalLayout reportes;
	private Button agregarAsientos;
	private Button visualizarAsientos;
	private Button agregarCuentas;
	private Button visualizarCuentas;
	private Button reporteCaja;
	private Button reporteCuentas;
	
	public MenuContabilidad() {
		inicializarLayout();
		inicializarComponentes();

	}

	private void inicializarLayout() {		
		botonera = new HorizontalLayout();
		botonera.setWidthFull();
		asientos = new VerticalLayout();
		asientos.getStyle().set("border", "1px solid #9E9E9E");
		asientos.setHeightFull();
		cuentas = new VerticalLayout();
		cuentas.getStyle().set("border", "1px solid #9E9E9E");
		cuentas.setHeightFull();
		reportes = new VerticalLayout();
		reportes.getStyle().set("border", "1px solid #9E9E9E");
		reportes.setHeightFull();
		botonera.add(asientos, cuentas, reportes);
		this.add(botonera);
		
		etiquetas = new HorizontalLayout();
		etiquetas.setWidthFull();
		etiquetas.getStyle().set("border", "1px solid #9E9E9E");
		Label lblEtiquetas = new Label("ETIQUETAS");
		etiquetas.add(lblEtiquetas);
		this.add(etiquetas);
	}

	private void inicializarComponentes() {
		Label lblAsientos = new Label("ASIENTOS");
		agregarAsientos = new Button();
		agregarAsientos.setSizeFull();
		agregarAsientos.setText("Agregar asientos");
		visualizarAsientos = new Button();
		visualizarAsientos.setSizeFull();
		visualizarAsientos.setText("Visualizar asientos");
		asientos.add(lblAsientos, agregarAsientos, visualizarAsientos);
		
		Label lblCuentas = new Label("CUENTAS");
		agregarCuentas = new Button();
		agregarCuentas.setSizeFull();
		agregarCuentas.setText("Agregar cuentas");
		visualizarCuentas = new Button();
		visualizarCuentas.setSizeFull();
		visualizarCuentas.setText("Visualizar cuentas");
		cuentas.add(lblCuentas, agregarCuentas, visualizarCuentas);

		Label lblReportes = new Label("REPORTES");
		reporteCaja = new Button();
		reporteCaja.setSizeFull();
		reporteCaja.setText("Reporte caja");
		reporteCuentas = new Button();
		reporteCuentas.setSizeFull();
		reporteCuentas.setText("Mayores por cuenta");
		reportes.add(lblReportes, reporteCaja, reporteCuentas);
	}
	
	public void borrarEtiquetas() {
		etiquetas.removeAll();
	}

	public void cargarEtiquetas(Map <String, String> nuevasEtiquetas) {
		for(Map.Entry<String, String> entry : nuevasEtiquetas.entrySet()) {
			etiquetas.add(entry.getKey());
			etiquetas.add(entry.getValue());
		}		
	}
	
	public void agregarAsientosListener(ComponentEventListener<ClickEvent<Button>> listener) {
		agregarAsientos.addClickListener(listener);
	}

	public void visualizarAsientosAsientosListener(ComponentEventListener<ClickEvent<Button>> listener) {
		visualizarAsientos.addClickListener(listener);
	}

	public void agregarCuentasListener(ComponentEventListener<ClickEvent<Button>> listener) {
		agregarCuentas.addClickListener(listener);
	}	

	public void visualizarCuentasListener(ComponentEventListener<ClickEvent<Button>> listener) {
		visualizarCuentas.addClickListener(listener);
	}

	public void reporteCajaListener(ComponentEventListener<ClickEvent<Button>> listener) {
		reporteCaja.addClickListener(listener);
	}

	public void reporteCuentasListener(ComponentEventListener<ClickEvent<Button>> listener) {
		reporteCuentas.addClickListener(listener);
	}
}