package com.tp.proyecto1.views.contabilidad;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MenuContabilidad extends HorizontalLayout{

	private HorizontalLayout botonera;
	private VerticalLayout asientos;
	private VerticalLayout cuentas;
	private VerticalLayout caja;
	private Button agregarAsientos;
	private Button visualizarAsientos;
	private Button agregarCuentas;
	private Button visualizarCuentas;
	private Button agregarSalidaCaja;
	private Button visualizarMovimientosCaja;
	
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
		cuentas.setHeightFull();
		caja = new VerticalLayout();
		caja.setHeightFull();
		botonera.add(asientos, cuentas, caja);
		this.add(botonera);
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

		Label lblCaja = new Label("CAJA");
		agregarSalidaCaja = new Button();
		agregarSalidaCaja.setSizeFull();
		agregarSalidaCaja.setText("Registrar salida de caja");
		visualizarMovimientosCaja = new Button();
		visualizarMovimientosCaja.setSizeFull();
		visualizarMovimientosCaja.setText("Visualizar movimientos de caja");
		caja.add(lblCaja, agregarSalidaCaja, visualizarMovimientosCaja);
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

	public void agregarSalidaCajaListener(ComponentEventListener<ClickEvent<Button>> listener) {
		agregarSalidaCaja.addClickListener(listener);
	}

	public void visualizarMovimintosCajaListener(ComponentEventListener<ClickEvent<Button>> listener) {
		visualizarMovimientosCaja.addClickListener(listener);
	}
}