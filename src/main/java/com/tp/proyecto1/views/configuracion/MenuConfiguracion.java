package com.tp.proyecto1.views.configuracion;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MenuConfiguracion extends HorizontalLayout{

	private HorizontalLayout botonera;
	private VerticalLayout adminUsuarios;
	private VerticalLayout adminDB;
	private VerticalLayout adminConfiguraciones;
	private Button agregarUsuario;
	private Button visualizarUsuarios;
	private Button hacerBckup;
	private Button tomarBckup;
	private Button visualizarConfiguracion;
	
	public MenuConfiguracion() {
		inicializarLayout();
		inicializarComponentes();
	}

	private void inicializarLayout() {		
		botonera = new HorizontalLayout();
		botonera.setWidthFull();
		adminUsuarios = new VerticalLayout();
		adminUsuarios.setHeightFull();
		adminDB = new VerticalLayout();
		adminDB.setHeightFull();
		adminConfiguraciones = new VerticalLayout();
		adminConfiguraciones.setHeightFull();
		botonera.add(adminUsuarios, adminDB, adminConfiguraciones);
		this.add(botonera);
		this.addClassName("logo-background");
	}

	private void inicializarComponentes() {
		Label lblUsuarios = new Label("USUARIOS");
		agregarUsuario = new Button();
		agregarUsuario.setSizeFull();
		agregarUsuario.setText("Agregar usuarios");
		visualizarUsuarios = new Button();
		visualizarUsuarios.setSizeFull();
		visualizarUsuarios.setText("Visualizar usuarios");
		adminUsuarios.add(lblUsuarios, agregarUsuario, visualizarUsuarios);
		
		Label lblDB = new Label("BASE DE DATOS");
		hacerBckup = new Button();
		hacerBckup.setSizeFull();
		hacerBckup.setText("Realizar un back up");
		tomarBckup = new Button();
		tomarBckup.setSizeFull();
		tomarBckup.setText("Recuperar un back up");
		adminDB.add(lblDB, hacerBckup, tomarBckup);

		Label lblConfig = new Label("CONFIGURACION");
		visualizarConfiguracion = new Button();
		visualizarConfiguracion.setSizeFull();
		visualizarConfiguracion.setText("Visualizar configuraciones");
		adminConfiguraciones.add(lblConfig, visualizarConfiguracion);
	}

	public void agregarUsuariosListener(ComponentEventListener<ClickEvent<Button>> listener) {
		agregarUsuario.addClickListener(listener);
	}

	public void visualizarUsuariosListener(ComponentEventListener<ClickEvent<Button>> listener) {
		visualizarUsuarios.addClickListener(listener);
	}

	public void hacerBckupListener(ComponentEventListener<ClickEvent<Button>> listener) {
		hacerBckup.addClickListener(listener);
	}	

	public void tomarBckupListener(ComponentEventListener<ClickEvent<Button>> listener) {
		tomarBckup.addClickListener(listener);
	}

	public void visualizarConfiguracionListener(ComponentEventListener<ClickEvent<Button>> listener) {
		visualizarConfiguracion.addClickListener(listener);
	}
}