package com.tp.proyecto1.controllers.usuarios;

import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.usuarios.UsuariosView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class UsuariosController {

	private UsuariosView usuariosView;

	private ChangeHandler changeHandler;

	@Autowired
	UserService userService;

	public UsuariosController() {
		Inject.Inject(this);
		this.usuariosView = new UsuariosView();
		setListeners();
		setComponents();
		listarUsuarios();
	}

	private void listarUsuarios() {
		User userBusqueda = new User();
		if(checkFiltros()){
			setParametrosBusqueda(userBusqueda);
			usuariosView.getGrid().setItems(userService.findUsers(userBusqueda));
		}else{
			usuariosView.getGrid().setItems(userService.findAll());
		}
	}

	private void setParametrosBusqueda(User userBusqueda) {
		if(!usuariosView.getIdFilter().isEmpty()){
			userBusqueda.setId(usuariosView.getIdFilter().getValue().longValue());
		}
		if (!usuariosView.getNombreUsuarioFilter().isEmpty()) {
			userBusqueda.setUser(usuariosView.getNombreUsuarioFilter().getValue());
		}
		userBusqueda.setEnabled(usuariosView.getCheckActivo().getValue());
	}

	private boolean checkFiltros() {
		return !usuariosView.getIdFilter().isEmpty() || !usuariosView.getNombreUsuarioFilter().isEmpty() ||
				usuariosView.getCheckActivo().getValue();
	}

	private void setComponents() {
		this.usuariosView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
	}

	private Button createEditButton(User user) {
		return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
			UsuarioFormController usuarioFormController = new UsuarioFormController(user);
			usuarioFormController.getView().open();
			usuarioFormController.setChangeHandler(this::listarUsuarios);
		});
	}

	private void setListeners() {
		setChangeHandler(this::listarUsuarios);
		usuariosView.getNewUserButton().addClickListener(e->openNewUserForm(null));
		usuariosView.getSearchButton().addClickListener(e->listarUsuarios());
	}

	private void openNewUserForm(User user) {
		UsuarioFormController usuarioFormController = new UsuarioFormController(user);
		usuarioFormController.getView().open();
		usuarioFormController.setChangeHandler(this::listarUsuarios);
	}

	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public UsuariosView getUsuariosView() {
		return usuariosView;
	}

	public void setUsuariosView(UsuariosView usuariosView) {
		this.usuariosView = usuariosView;
	}
}
