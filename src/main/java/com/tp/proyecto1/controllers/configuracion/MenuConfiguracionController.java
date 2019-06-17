package com.tp.proyecto1.controllers.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.MainView;
import com.tp.proyecto1.controllers.usuario.UsuarioController;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.configuracion.MenuConfiguracion;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class MenuConfiguracionController {

	@Autowired
	private UsuarioController usuarioController;
	@Autowired
	private ConfiguracionController confController;
	@Autowired
	private DBController DBController;
	
	private MainView observer;
	private MenuConfiguracion menuView;
	
	public MenuConfiguracionController(MainView observer) {
		Inject.Inject(this);
		this.observer = observer; 
		menuView = new MenuConfiguracion();
		agregarListeners();
	}
	
	public MenuConfiguracion getMenuConfiguracionView() {
		return menuView;
	}
	
	private void agregarListeners() {
		menuView.agregarUsuariosListener(e-> agregarUsuario());
		menuView.visualizarUsuariosListener(e-> visualizarUsuario());
		menuView.hacerBckupListener(e-> hacerBckup());
		menuView.tomarBckupListener(e-> tomarBckup());
		menuView.visualizarConfiguracionListener(e-> visualizarConfiguracion());
	}
	
	private void agregarUsuario() {
		//TODO		
		observer.actualizarView(usuarioController.getUsuariosView());
	}
	
	private void visualizarUsuario() {
		//TODO
		observer.actualizarView(usuarioController.getUsuariosView());
	}
	
	private void hacerBckup() {
		//TODO
		DBController.getCrearBackUpView();
	}
	
	private void tomarBckup() {
		//TODO
		DBController.getTomarBackUpView();
	}
	
	private void visualizarConfiguracion() {
		//TODO
		observer.actualizarView(confController.getConfiguracionView());
	}
}
