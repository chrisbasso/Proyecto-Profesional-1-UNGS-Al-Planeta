package com.tp.proyecto1.views.usuarios;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LoginView extends VerticalLayout {

	private LoginForm loginComponent;

	public LoginView() {

		setLayout();

	}

	private void setLayout() {
		loginComponent = new LoginForm();
		this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.add(loginComponent);
	}

	public LoginForm getLoginComponent() {
		return loginComponent;
	}
}
