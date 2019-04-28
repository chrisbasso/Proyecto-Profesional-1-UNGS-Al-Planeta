package com.tp.proyecto1;

import com.tp.proyecto1.controllers.ClientesController;
import com.tp.proyecto1.controllers.LoginController;
import com.tp.proyecto1.controllers.ViajesController;
import com.tp.proyecto1.model.users.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@StyleSheet("styles.css")
public class MainView extends VerticalLayout {

	@Autowired
	private ClientesController clientesController;

	@Autowired
	private LoginController loginController;

	@Autowired
	private ViajesController viajesController;

	private VerticalLayout mainLayout;
	private AppLayout appLayout;
	private AppLayoutMenu menu;

	public MainView() {
		setLayouts();
		setMainPage();
	}

	private void setMainPage() {

		mainLayout.removeAll();

		if(UI.getCurrent().getSession().getAttribute("usuarioLogueado")==null){
			openMain();
		}else if(((User)UI.getCurrent().getSession().getAttribute("usuarioLogueado")).getUser()==null){
			openMain();
		} else{
			openMenu();
		}

	}

	private void openMain() {
		Button btnSignIn = new Button("Ingresar");
		Button btnSignUp = new Button("Registrarse");
		mainLayout.add(btnSignIn, btnSignUp);
		btnSignIn.addClickListener(e->openLoginView());
	}

	private void setLayouts() {

		mainLayout = new VerticalLayout();
		mainLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.add(mainLayout);

	}

	private void openMenu() {
		appLayout = new AppLayout();
		appLayout.setBranding(getLogo());
		this.add(mainLayout);
		mainLayout.add(appLayout);
		menu = appLayout.createMenu();


		menu.addMenuItems(
				new AppLayoutMenuItem(VaadinIcon.AIRPLANE.create(),"Viajes",e->openViajesView()),
				new AppLayoutMenuItem(VaadinIcon.TICKET.create(),"Ventas"),
				new AppLayoutMenuItem(VaadinIcon.CALENDAR_CLOCK.create(),"Reservas"),
				new AppLayoutMenuItem(VaadinIcon.GROUP.create(),"Clientes", e -> openClientesView()),
				new AppLayoutMenuItem(VaadinIcon.BOOK_DOLLAR.create(), "Contabilidad"),
				new AppLayoutMenuItem(VaadinIcon.COGS.create(),"Configuración")
		);

		menu.addMenuItem(new AppLayoutMenuItem(VaadinIcon.USER.create(),
				"Logout " + ((User) UI.getCurrent().getSession().getAttribute("usuarioLogueado")).getUser()),
				e->loginController.logout());

	}

	private HorizontalLayout getLogo() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");

		return new HorizontalLayout(logo, title);
	}

	private void openViajesView() {
		appLayout.setContent(viajesController.getView());
	}



	private void openClientesView() {
		appLayout.setContent(clientesController.getView());
	}

	private void openLoginView(){
		mainLayout.removeAll();
		mainLayout.add(loginController.getLoginView());
		loginController.setChangeHandler(this::openMenu);
	}

}