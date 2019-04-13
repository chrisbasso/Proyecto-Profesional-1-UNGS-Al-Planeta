package com.tp.proyecto1.controllers;

import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.views.LoginView;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class LoginController{

	private LoginView loginView;

	private UserService userService;

	private ChangeHandler changeHandler;

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
				changeHandler.onChange();
//				navigateToMainPage();
			} else {
				loginView.getLoginComponent().setError(true);
			}
		});

	}

	private boolean authenticate(AbstractLogin.LoginEvent e) {
		return true;
	}

	public LoginView getLoginView() {
		return loginView;
	}

	public interface ChangeHandler {
		void onChange();
	}
	public void setChangeHandler(LoginController.ChangeHandler h) {
		changeHandler = h;
	}
}
