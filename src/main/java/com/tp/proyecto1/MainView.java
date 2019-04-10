package com.tp.proyecto1;

import com.tp.proyecto1.controllers.PersonaController;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
@StyleSheet("styles.css")
public class MainView extends VerticalLayout {

	private PersonaController personaController;

	private VerticalLayout mainLayout;
	private AppLayout appLayout;
	private AppLayoutMenu menu;

	public MainView(PersonaController personaController) {

		this.personaController = personaController;

		setLayouts();
		setMenu();
		openPersonaView();

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

		menu.addMenuItems(new AppLayoutMenuItem("Viajes", e -> openPersonaView()),
				new AppLayoutMenuItem("Pagina 2"),
				new AppLayoutMenuItem("Pagina 3"),
				new AppLayoutMenuItem("Pagina 4"));
	}

	private HorizontalLayout getLogo() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");

		return new HorizontalLayout(logo, title);
	}

	private void openPersonaView() {
		appLayout.setContent(personaController.getView());
	}
}