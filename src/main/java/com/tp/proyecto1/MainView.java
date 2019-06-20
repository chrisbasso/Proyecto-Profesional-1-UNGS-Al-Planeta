package com.tp.proyecto1;

import com.tp.proyecto1.controllers.usuarios.LoginController;
import com.tp.proyecto1.controllers.usuarios.UsuariosController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.tp.proyecto1.controllers.clientes.ClientesController;
import com.tp.proyecto1.controllers.clientes.EventosClienteWindowController;
import com.tp.proyecto1.controllers.clientes.HistorialPuntosClienteWindowController;
import com.tp.proyecto1.controllers.clientes.PuntosClienteController;
import com.tp.proyecto1.controllers.configuracion.MenuConfiguracionController;
import com.tp.proyecto1.controllers.contabilidad.MovimientosCajaController;
import com.tp.proyecto1.controllers.eventos.EventosController;
import com.tp.proyecto1.controllers.promociones.PromocionesController;
import com.tp.proyecto1.controllers.reportes.ReportesController;
import com.tp.proyecto1.controllers.reserva.ReservasController;
import com.tp.proyecto1.controllers.venta.VentasController;
import com.tp.proyecto1.controllers.viajes.ViajesController;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.repository.eventos.EventoRepository;
import com.tp.proyecto1.services.EventoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route
@StyleSheet("styles.css")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainView extends VerticalLayout{

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

	private MenuConfiguracionController menuConfiguracionController;

	@Autowired
	private MovimientosCajaController movimientosCajaController;
	@Autowired
	private UsuariosController usuariosController;
	
	@Autowired
	private PuntosClienteController puntosClienteController;
	
	@Autowired
	private EventoService eventoService;
	
	private TimerTask eventoTask;
	private Timer timer;
	
	/*@Autowired
	private EventosClienteWindowController eventosClienteController;*/

	private VerticalLayout mainLayout;
	private AppLayout appLayout;
	private AppLayoutMenu menu;
	private AppLayoutMenuItem viajes;
	private AppLayoutMenuItem promociones;
	private AppLayoutMenuItem ventas;
	private AppLayoutMenuItem reservas;
	private AppLayoutMenuItem clientes;
	private AppLayoutMenuItem eventos;
	private AppLayoutMenuItem caja;
	private AppLayoutMenuItem reportes;
	private AppLayoutMenuItem configuraciones;
	private AppLayoutMenuItem usuarios;
	private AppLayoutMenuItem logout;
	
	//AppLayoutMenuItems de Cliente
	//private AppLayoutMenuItem eventosCliente;
	private AppLayoutMenuItem puntosCliente;


	
	public MainView() {
		menuConfiguracionController = new MenuConfiguracionController (this);
		setLayouts();		
		setMainPage();
	}

	private void setLayouts() {
		mainLayout = new VerticalLayout();
		mainLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		mainLayout.getElement().setAttribute("theme", "green");
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
		setMenu();
		openViajesView();
		final UI currentUI = UI.getCurrent();

		timer = new Timer();
		
		eventoTask = new TimerTask() {

			@Override
			public void run()
			{
				currentUI.access(() -> {
					Evento eventoExample = new Evento();
					eventoExample.setAbierto(true);
					for(Evento evento : eventoService.findAll(eventoExample))
					{
						if (evento.getFechaVencimiento()!= null && evento.getHoraVencimiento()!= null)
						{							
							if(evento.getFechaVencimiento().isBefore(LocalDate.now()) ||
										(evento.getFechaVencimiento().isEqual(LocalDate.now()) &&
									LocalTime.now().plusMinutes(15).isAfter(evento.getHoraVencimiento())))
							{
								if (evento.getUsuarioAsignado().equals(Proyecto1Application.logUser))
								{
									Notification.show("El evento numero "+evento.getId() + " de prioridad "+evento.getPrioridad()+" esta por vencer. Mensaje: "+evento.getMensaje());
								}	
							}
						}
					}
				});
			}
		};
		
		timer.schedule(eventoTask, 1000, 600000); //cada 10 minutos
	}

	private void setMenu() {
		menu = appLayout.createMenu();

		viajes = new AppLayoutMenuItem(VaadinIcon.AIRPLANE.create(),"Viajes", e->openViajesView());
		promociones = new AppLayoutMenuItem(VaadinIcon.CALENDAR.create(),"Promociones",e->openPromocionesView());
		ventas = new AppLayoutMenuItem(VaadinIcon.TICKET.create(),"Ventas", e->openVentasView());
		reservas = new AppLayoutMenuItem(VaadinIcon.CALENDAR_CLOCK.create(),"Reservas", e->openReservasView());
		clientes = new AppLayoutMenuItem(VaadinIcon.GROUP.create(),"Clientes", e -> openClientesView());
		eventos = new AppLayoutMenuItem(VaadinIcon.PHONE.create(),"Eventos", e -> openEventosView());
		caja = new AppLayoutMenuItem(VaadinIcon.BOOK_DOLLAR.create(), "Caja", e -> openCajaView());
		reportes = new AppLayoutMenuItem(VaadinIcon.CHART_3D.create(), "Reportes", e-> openReportesView());
		configuraciones = new AppLayoutMenuItem(VaadinIcon.COGS.create(),"Configuración", e-> openConfiguracionView());
		usuarios = new AppLayoutMenuItem(VaadinIcon.USERS.create(),"Usuarios", e-> openUsuariosView());
		
		//eventosCliente = new AppLayoutMenuItem(VaadinIcon.PHONE.create(),"Eventos", e -> openEventosClienteView());
		puntosCliente = new AppLayoutMenuItem(VaadinIcon.CALENDAR.create(),"Mis puntos",e->openPuntosClienteView());
		
		logout = new AppLayoutMenuItem(VaadinIcon.USER.create(),
				"Logout " + Proyecto1Application.logUser.getUser(),
				e-> {eventoTask.cancel();
						timer.cancel();
					loginController.logout();});

		viajes.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		promociones.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		ventas.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		reservas.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		clientes.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		eventos.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		caja.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		reportes.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		configuraciones.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		usuarios.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		puntosCliente.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		logout.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		

		menu.addMenuItems(viajes, promociones, ventas, reservas, clientes, eventos, puntosCliente, caja, reportes, configuraciones,usuarios, logout);

		setPerfiles();

	}

	private void setPerfiles() {

		String role = Proyecto1Application.logUser.getRol().getName();

		viajes.setVisible(false);
		promociones.setVisible(false);
		ventas.setVisible(false);
		reservas.setVisible(false);
		clientes.setVisible(false);
		eventos.setVisible(false);
		caja.setVisible(false);
		reportes.setVisible(false);
		configuraciones.setVisible(false);
		usuarios.setVisible(false);
	//	eventosCliente.setVisible(false);
		puntosCliente.setVisible(false);

		if(role.equals("ADMINISTRADOR")){
			viajes.setVisible(true);
			promociones.setVisible(true);
			ventas.setVisible(true);
			reservas.setVisible(true);
			clientes.setVisible(true);
			eventos.setVisible(true);
			caja.setVisible(true);
			reportes.setVisible(true);
			configuraciones.setVisible(true);
			usuarios.setVisible(true);
		}
		if(role.equals("VENDEDOR")){
			viajes.setVisible(true);
			promociones.setVisible(true);
			ventas.setVisible(true);
			reservas.setVisible(true);
			clientes.setVisible(true);
			eventos.setVisible(true);
		}
		if(role.equals("SUPERVISOR")){
			viajes.setVisible(true);
			promociones.setVisible(true);
			ventas.setVisible(true);
			reservas.setVisible(true);
			clientes.setVisible(true);
			eventos.setVisible(true);
			reportes.setVisible(true);
		}
		if(role.equals("CONTADOR")){
			caja.setVisible(true);
			reportes.setVisible(true);
		}
		if(role.equals("CLIENTE")){
//			viajes.setVisible(true);
//			ventas.setVisible(true);
		//	eventosCliente.setVisible(true);
//			puntosCliente.setVisible(true);
		}
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

	private void openUsuariosView() {
		actualizarMenuSeleccionado(usuarios);
		appLayout.setContent(usuariosController.getUsuariosView());
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
		eventosController.getChangeHandler().onChange();
	}

	private void openCajaView(){
		actualizarMenuSeleccionado(caja);
		appLayout.setContent(movimientosCajaController.getMovimientosView());
	}
	
	private void openReportesView() {
		actualizarMenuSeleccionado(reportes);
		appLayout.setContent(reportesController.getReportesView());
		reportesController.setComponents();
	}

	private void openConfiguracionView(){
		actualizarMenuSeleccionado(configuraciones);
		appLayout.setContent(menuConfiguracionController.getMenuConfiguracionView());
	}
	
	/*private void openEventosClienteView()
	{
		actualizarMenuSeleccionado(eventosCliente);
		appLayout.setContent(eventosClienteController.getView());
		
	}*/
	
	private void openPuntosClienteView()
	{
		actualizarMenuSeleccionado(puntosCliente);
		appLayout.setContent(puntosClienteController.getView());
		
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
		
		if(caja.equals(menu)) {
			caja.setClassName("selected-menu");	
		}else {
			caja.setClassName("normal-menu");
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

		if(usuarios.equals(menu)) {
			usuarios.setClassName("selected-menu");
		}else {
			usuarios.setClassName("normal-menu");
		}
		
	/*	if(eventosCliente.equals(menu))
		{
			eventosCliente.setClassName("selected-menu");
		}
		else
		{
			eventosCliente.setClassName("normal-menu");
		}*/
		
		if(puntosCliente.equals(menu))
		{
			puntosCliente.setClassName("selected-menu");
		}
		else
		{
			puntosCliente.setClassName("normal-menu");
		}

		if(logout.equals(menu)) {
			logout.setClassName("selected-menu");	
		}else {
			logout.setClassName("normal-menu");
		}
	}

}