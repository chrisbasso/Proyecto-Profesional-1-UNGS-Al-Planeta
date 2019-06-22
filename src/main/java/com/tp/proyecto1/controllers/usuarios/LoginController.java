package com.tp.proyecto1.controllers.usuarios;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
import com.tp.proyecto1.utils.EnviadorDeMail;
import com.tp.proyecto1.utils.GenericDialog;
import com.tp.proyecto1.views.usuarios.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@UIScope
public class LoginController{

	private LoginView loginView;

	private UserService userService;

	private ChangeHandler changeHandler;

	private User loginUser;

	@Autowired
	public LoginController(UserService userService) {
		this.loginView = new LoginView();
		this.userService = userService;
		setLoginForm();

	}
	private void setLoginForm() {

		loginView.getLoginComponent().addLoginListener(e -> {
			boolean isAuthenticated = authenticate(e);
			if (isAuthenticated) {
				loginUser = userService.getUserByName(e.getUsername());
				UI.getCurrent().getSession().setAttribute("usuarioLogueado", loginUser);
				Proyecto1Application.logUser=loginUser;
				Proyecto1Application.sucursal=loginUser.getSucursal();
				changeHandler.onChange();
			} else {
				loginView.getLoginComponent().setError(true);
			}
		});
		loginView.getLoginComponent().setI18n(createSpanishI18n());
		loginView.getLoginComponent().addForgotPasswordListener(e->openRecoverDialog());

	}

	private void openRecoverDialog() {
		TextField userNametxt = new TextField("Nombre de Usuario");
		userNametxt.setWidth("300px");
		ConfirmationDialog confirmationDialog = new ConfirmationDialog("Se enviará la contraseña al mail registrado. ¿Desea Continuar?", userNametxt);
		confirmationDialog.open();
		confirmationDialog.getConfirmButton().addClickListener(e2-> {
			User user = userService.getUserByName(userNametxt.getValue());
			if(user==null){
				GenericDialog genericDialog = new GenericDialog("Usuario no registrado");
			}else{
				EnviadorDeMail enviadorDeMail = new EnviadorDeMail();
				enviadorDeMail.enviarMail(user.getEmail(),"Al Planeta - Recupero de Contraseña", "Su contraseña es: " + user.getPassword());
			}
		});

	}

	private LoginI18n createSpanishI18n() {
		final LoginI18n i18n = LoginI18n.createDefault();

		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("Al Planeta");
		i18n.getHeader().setDescription("Reserva de vuelos");
		i18n.getForm().setUsername("Usuario");
		i18n.getForm().setTitle("Acceso a su cuenta");
		i18n.getForm().setSubmit("Entrar");
		i18n.getForm().setPassword("Contraseña");
		i18n.getForm().setForgotPassword("¿olvidó su contraseña?");
		i18n.getErrorMessage().setTitle("Usuario/Contraseña inválidos");
		i18n.getErrorMessage().setMessage("Confirme su usuario y contraseña e intentelo nuevamente.");

		return i18n;
	}

	private boolean authenticate(AbstractLogin.LoginEvent e) {
		String userName = e.getUsername();
		String pass = e.getPassword();
		User user = new User(userName,pass);
		if(userService.valideUser(user)){
			return true;
		}
		return false;
	}

	public void logout(){

		Proyecto1Application.logUser=null;
		UI.getCurrent().getPage().reload();

	}

	public LoginView getLoginView() {
		return loginView;
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public User getLoginUser() {
		return loginUser;
	}
}
