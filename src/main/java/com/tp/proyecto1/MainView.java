package com.tp.proyecto1;

import com.tp.proyecto1.controllers.ClientesController;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
@StyleSheet("styles.css")
public class MainView extends VerticalLayout {

	private ClientesController clientesController;

	private VerticalLayout mainLayout;
	private AppLayout appLayout;
	private AppLayoutMenu menu;

	public MainView(ClientesController clientesController) {

		this.clientesController = clientesController;

		setLayouts();
		setMenu();
		openClientesView();

	}

	private void setLayouts() {
		mainLayout = new VerticalLayout();
		appLayout = new AppLayout();
		appLayout.setBranding(getLogo());
		this.add(mainLayout);
		mainLayout.add(appLayout);
	}

	private void setMenu() {
		menu = appLayout.createMenu();

		menu.addMenuItems(
				new AppLayoutMenuItem(VaadinIcon.AIRPLANE.create(),"Destinos"),
				new AppLayoutMenuItem(VaadinIcon.TICKET.create(),"Pasajes"),
				new AppLayoutMenuItem(VaadinIcon.GROUP.create(),"Clientes", e -> openClientesView()),
				new AppLayoutMenuItem(VaadinIcon.BOOK_DOLLAR.create(), "Contabilidad"),
				new AppLayoutMenuItem(VaadinIcon.COGS.create(),"Configuración")
		);
	}

	private HorizontalLayout getLogo() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");

		return new HorizontalLayout(logo, title);
	}

	private void openClientesView() {
		appLayout.setContent(clientesController.getView());
	}
}