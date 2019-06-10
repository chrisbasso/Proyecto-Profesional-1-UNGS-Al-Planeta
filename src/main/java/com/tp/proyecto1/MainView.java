package com.tp.proyecto1;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.controllers.clientes.ClientesController;
import com.tp.proyecto1.controllers.configuracion.ConfiguracionController;
import com.tp.proyecto1.controllers.contabilidad.MenuContabilidadController;
import com.tp.proyecto1.controllers.eventos.EventosController;
import com.tp.proyecto1.controllers.login.LoginController;
import com.tp.proyecto1.controllers.promociones.PromocionesController;
import com.tp.proyecto1.controllers.reportes.ReportesController;
import com.tp.proyecto1.controllers.reserva.ReservasController;
import com.tp.proyecto1.controllers.venta.VentasController;
import com.tp.proyecto1.controllers.viajes.ViajesController;
import com.vaadin.flow.component.Component;
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

@Route
@StyleSheet("styles.css")
public class MainView extends VerticalLayout {

	@Autowired
	private ClientesController clientesController;
	@Autowired
	private LoginController loginController;
	@Autowired
	private ViajesController viajesController;
	@Autowired
	private VentasController ventasController;
	@Autowired
	private EventosController eventosController;
	@Autowired
	private ReservasController reservasController;
	@Autowired
	private PromocionesController promocionesController;
	@Autowired
	private ReportesController reportesController;
	@Autowired
	private ConfiguracionController configuracionController;
	private MenuContabilidadController menuContabilidadController;
	
	private VerticalLayout mainLayout;
	private AppLayout appLayout;
	private AppLayoutMenu menu;
	private AppLayoutMenuItem viajes;
	private AppLayoutMenuItem promociones;
	private AppLayoutMenuItem ventas;
	private AppLayoutMenuItem reservas;
	private AppLayoutMenuItem clientes;
	private AppLayoutMenuItem eventos;
	private AppLayoutMenuItem contabilidad;
	private AppLayoutMenuItem reportes;
	private AppLayoutMenuItem configuraciones;
	private AppLayoutMenuItem logout;
	
	public MainView() {
		menuContabilidadController = new MenuContabilidadController (this);
		setLayouts();
		setMainPage();
	}

	private void setLayouts() {
		mainLayout = new VerticalLayout();
		mainLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.add(mainLayout);
	}

	private void setMainPage() {
		mainLayout.removeAll();
		if(Proyecto1Application.logUser==null){
			openMain();
		} else{
			openMenu();
		}
	}

	private void openMain() {
		Button btnSignIn = new Button("Ingresar");
		Button btnSignUp = new Button("Registrarse");
		mainLayout.add(getLogo(), btnSignIn, btnSignUp);
		btnSignIn.addClickListener(e->openLoginView());
	}

	private void openMenu() {
		appLayout = new AppLayout();
		appLayout.setBranding(getLogo());
		mainLayout.add(appLayout);
		menu = appLayout.createMenu();
		
		viajes = new AppLayoutMenuItem(VaadinIcon.AIRPLANE.create(),"Viajes",e->openViajesView());
		promociones = new AppLayoutMenuItem(VaadinIcon.CALENDAR.create(),"Promociones",e->openPromocionesView());
		ventas = new AppLayoutMenuItem(VaadinIcon.TICKET.create(),"Ventas", e->openVentasView());
		reservas = new AppLayoutMenuItem(VaadinIcon.CALENDAR_CLOCK.create(),"Reservas", e->openReservasView());
		clientes = new AppLayoutMenuItem(VaadinIcon.GROUP.create(),"Clientes", e -> openClientesView());
		eventos = new AppLayoutMenuItem(VaadinIcon.PHONE.create(),"Eventos", e -> openEventosView());
		contabilidad = new AppLayoutMenuItem(VaadinIcon.BOOK_DOLLAR.create(), "Contabilidad", e -> openContabilidadView());
		reportes = new AppLayoutMenuItem(VaadinIcon.CHART_3D.create(), "Reportes", e-> openReportesView());
		configuraciones = new AppLayoutMenuItem(VaadinIcon.COGS.create(),"Configuración", e-> openConfiguracionView());
		logout = new AppLayoutMenuItem(VaadinIcon.USER.create(),
				"Logout " + Proyecto1Application.logUser.getUser(),
				e->loginController.logout());
				
		menu.addMenuItems(viajes, promociones, ventas, reservas, clientes, eventos, contabilidad, reportes, configuraciones, logout);
		//Cargamos la lista de viajes en la página inicial
		openViajesView();
	}

	private HorizontalLayout getLogo() {
		final Image logo = new Image("img/logo-viaje.png", "Al Planeta");
		logo.setHeight("60px");
		H3 title = new H3("AL PLANETA");
		return new HorizontalLayout(logo, title);
	}

	private void openViajesView() {
		actualizarMenuSeleccionado(viajes);
		appLayout.setContent(viajesController.getView());
	}
	
	private void openPromocionesView() {
		actualizarMenuSeleccionado(promociones);
		appLayout.setContent(promocionesController.getView());
	}

	private void openVentasView() {
		actualizarMenuSeleccionado(ventas);
		appLayout.setContent(ventasController.getView());
		ventasController.getChangeHandler().onChange();
	}
	
	private void openReservasView() {
		actualizarMenuSeleccionado(reservas);
		appLayout.setContent(reservasController.getView());
		reservasController.getChangeHandler().onChange();
	}

	private void openClientesView() {
		actualizarMenuSeleccionado(clientes);
		appLayout.setContent(clientesController.getView());
	}

	private void openEventosView() {
		actualizarMenuSeleccionado(eventos);
		appLayout.setContent(eventosController.getEventosView());
//		eventosController.getChangeHandler().onChange();
	}

	private void openContabilidadView(){
		actualizarMenuSeleccionado(contabilidad);
		appLayout.setContent(menuContabilidadController.getMenuView());
	}
	
	private void openReportesView() {
		actualizarMenuSeleccionado(reportes);
		appLayout.setContent(reportesController.getReportesView());
	}

	private void openConfiguracionView(){
		actualizarMenuSeleccionado(configuraciones);
		appLayout.setContent(configuracionController.getConfiguracionView());
	}
	
	private void openLoginView(){
		mainLayout.removeAll();
		mainLayout.add(loginController.getLoginView());
		loginController.setChangeHandler(this::openMenu);
	}
	
	public void actualizarView(Object view) {
		appLayout.removeContent();
		appLayout.setContent((Component) view);
	}
	
	private void actualizarMenuSeleccionado(AppLayoutMenuItem menu) {
		if(viajes.equals(menu)) {
			viajes.setClassName("selected-menu");	
		}else {
			viajes.setClassName("normal-menu");
		}

		if(promociones.equals(menu)) {
			promociones.setClassName("selected-menu");	
		}else {
			promociones.setClassName("normal-menu");
		}

		if(ventas.equals(menu)) {
			ventas.setClassName("selected-menu");	
		}else {
			ventas.setClassName("normal-menu");
		}
		
		if(reservas.equals(menu)) {
			reservas.setClassName("selected-menu");	
		}else {
			reservas.setClassName("normal-menu");
		}
		
		if(clientes.equals(menu)) {
			clientes.setClassName("selected-menu");	
		}else {
			clientes.setClassName("normal-menu");
		}
		
		if(eventos.equals(menu)) {
			eventos.setClassName("selected-menu");	
		}else {
			eventos.setClassName("normal-menu");
		}
		
		if(contabilidad.equals(menu)) {
			contabilidad.setClassName("selected-menu");	
		}else {
			contabilidad.setClassName("normal-menu");
		}
		
		if(reportes.equals(menu)) {
			reportes.setClassName("selected-menu");	
		}else {
			reportes.setClassName("normal-menu");
		}
		
		if(configuraciones.equals(menu)) {
			configuraciones.setClassName("selected-menu");	
		}else {
			configuraciones.setClassName("normal-menu");
		}

		if(logout.equals(menu)) {
			logout.setClassName("selected-menu");	
		}else {
			logout.setClassName("normal-menu");
		}
	}
}