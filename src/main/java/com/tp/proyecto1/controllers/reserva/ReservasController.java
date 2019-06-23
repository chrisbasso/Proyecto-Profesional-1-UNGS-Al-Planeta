package com.tp.proyecto1.controllers.reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.tp.proyecto1.Proyecto1Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.controllers.venta.VentaFormController;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
import com.tp.proyecto1.utils.EnviadorDeMail;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reportes.ComprobanteReservaJR;
import com.tp.proyecto1.views.reportes.ComprobanteVentaJR;
import com.tp.proyecto1.views.reserva.ComprobanteReserva;
import com.tp.proyecto1.views.reserva.ReservaView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ReservasController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private ViajeService viajeService;
    @Autowired
    private ClienteService clienteService;
    private ReservaFormController reservaFormController;
    private ReservaView reservaView;
    private ChangeHandler changeHandler;

    private VentaFormController ventaFormController;
    
    public ReservasController() {
        Inject.Inject(this);
        this.reservaView = new ReservaView();        
        agregarBotonesEdicion();
        setListeners();
        cargarCiudades();
        listarReservas();
    }

    private void agregarBotonesEdicion() {
        reservaView.agregarColumnaEdicion(this::crearBotonEdicion);
        reservaView.agregarColumnaBorrado(this::crearBotonBorrado);
    }
    
    private Button crearBotonEdicion(Reserva reserva) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> editarReserva(reserva));
    }
    
    private void editarReserva (Reserva reserva){
    	Viaje viaje = reserva.getViaje();
        if(ReservaREST.esReservablePorFecha(viaje)) {
        	reservaFormController = new ReservaFormController(viaje);
        	reservaFormController.setChangeHandler(this::listarReservas);
            reservaFormController.getFormModificacionReserva(reserva).open();    			
		}else {
			Notification.show("Por la política de fechas el viaje selecionado solo se puede comprar.");
		}            
    }
    
    private Button crearBotonBorrado(Reserva reserva) {
        Button btnBorrar = new Button(VaadinIcon.TRASH.create(), clickEvent -> borrarReserva(reserva));
    		if(!reserva.isActivo()){
    			btnBorrar.setEnabled(false);
    		}
    		return btnBorrar;
    }
    
    private void borrarReserva(Reserva reserva) {
    	String mensaje = "¿Realmente desea dar de baja la reserva del cliente " 
    						+ reserva.getCliente()
    						+ ", por "
    						+ reserva.getCantidadPasajes()
    						+ " pasajes?";
		ConfirmationDialog confirmationDialog = new ConfirmationDialog(mensaje);
		confirmationDialog.getConfirmButton().addClickListener(event -> {
			reserva.inactivar();
			Viaje viaje = reserva.getViaje();
			viaje.agregarPasajes(reserva.getCantidadPasajes());			
			reservaService.save(reserva);
			viajeService.save(viaje);
			Notification.show("Reserva dada de baja");
			EnviadorDeMail enviadorDeMail = new EnviadorDeMail();
			enviadorDeMail.enviarMailConInfoVentaCancelacion("Cancelacion de Reserva", reserva);
			changeHandler.onChange();
		});
		confirmationDialog.open();
	}

    private void setListeners() {
    	setChangeHandler(this::listarReservas);
    	reservaView.setIdClienteListener(e->validarCliente());
    	reservaView.setIdViajeListener(e->validarViaje());
    	reservaView.setBtnBuscarListener(e->listarReservas());
    	reservaView.setBtnVenderListener(e-> venderReserva());
    	reservaView.setBtnComprobanteListener(e->imprimirComprobante());
    }
    
    private void validarCliente(){
    	Long id = reservaView.getValueNumeroCliente();  
    	if( id != null) {
    		Optional<Cliente> cliente = clienteService.findById(id);
    		if(!cliente.isPresent()) {
    			Notification.show("No existe un cliente con ese ID");    			
    		}
    	}
    }

    private void validarViaje(){
    	Long id = reservaView.getValueNumeroViaje();  
    	if( id != null) {
    		Optional <Viaje> viaje = viajeService.findById(id);
    		if(!viaje.isPresent()){
    			Notification.show("No existe un viaje con ese ID");    			
    		}
    	}
    }    
            
    private void cargarCiudades() {
    	reservaView.cargarCiudades(viajeService.findAllCiudades());
    }

    private void imprimirComprobante(){
    	Reserva reserva = reservaView.getReservaSeleccionada(); 
    	EnviadorDeMail enviadorDeMail = new EnviadorDeMail();
    	if(reserva != null) {
			/*ComprobanteReserva comprobante = new ComprobanteReserva(reserva);
			comprobante.open();
			UI.getCurrent().getPage().executeJavaScript("setTimeout(function() {" +
					"  print(); self.close();}, 1000);");*/
    		List<Reserva> reservas = new ArrayList<Reserva>();
			reservas.add(reserva);
			ComprobanteReservaJR comproReserva = new ComprobanteReservaJR(reservas);
			comproReserva.exportarAPdf(reserva.getCliente().getNombreyApellido()+ "-"+ reserva.getCliente().getDni());
			enviadorDeMail.enviarConGmail(reserva.getCliente().getEmail(),
					"Comprobante de reserva- " + reserva.getCliente().getNombreyApellido()+ "-"+ reserva.getCliente().getDni(), reserva);
    	}
    	else{
			Notification.show("Seleccione una Reserva.");
		}
	}

    private void listarReservas() {
    	if(existenValoresDeFiltro()) {
    		listarReservasFiltradas();
    	}else {
    		listarTodasLasReservas();
    	}
    }
    
    private boolean existenValoresDeFiltro() {
    	return (reservaView.getValueNumeroViaje()!= null ||
    			reservaView.getValueNumeroCliente()!= null ||    			                 
                reservaView.getValueFecha() != null);
    }
    
    private void listarReservasFiltradas() {
    	Set <Reserva> reservas = new HashSet<Reserva>(); 
    	
    	Long nroViaje = reservaView.getValueNumeroViaje();
    	Long nroCliente = reservaView.getValueNumeroCliente();
    	Ciudad ciudadFiltros = reservaView.getValueCiudad();
        String codTransporte = reservaView.getValueCodTransporte();                 
        LocalDate fechaFiltro = reservaView.getValueFecha();
        
    	if(nroViaje != null &&  nroCliente != null) {
    		reservas.addAll(reservaService.findByIdViajeIdCliente(nroViaje, nroCliente));
    	}else if(nroViaje != null) {
    		reservas.addAll(reservaService.findByIdViaje(nroViaje));
    	}else if(nroCliente != null) {
    		reservas.addAll(reservaService.findByIdCliente(nroCliente));
    	}else if(fechaFiltro != null){
    		reservas.addAll(reservaService.findByFecha(fechaFiltro));
    	}else {
    		Notification.show("Debe ingresar cliente, viaje y/o fecha para realizar una búsqueda");
    		listarTodasLasReservas(); 
    		return;
    	}
    	
    	if(ciudadFiltros != null) {
    		if(codTransporte != null) {
    			for(Reserva reserva : reservas) {
    	    		if(!reserva.getViaje().getDestino().equals(ciudadFiltros)) {
    	    			reservas.remove(reserva);
    	    		}
    	    		if(!reserva.getViaje().getTransporte().getCodTransporte().equals(codTransporte)) {
    	    			reservas.remove(reserva);
    	    		}
    	    	}	
    		}	    	
    	}        
    	
    	reservas.addAll(reservaService.findByCiudad(ciudadFiltros));
    	List <Reserva> reservasList = new ArrayList(reservas);
		if(Proyecto1Application.logUser!=null){
			if(Proyecto1Application.logUser.getRol().getName().equals("CLIENTE")){
				reservasList = reservasList.stream().filter(e->e.getCliente().equals(Proyecto1Application.logUser.getCliente())).collect(Collectors.toList());
			}
		}
    	reservaView.cargarReservas(reservasList);
    }
    
    private void listarTodasLasReservas() {
		List <Reserva> reservasList = reservaService.findAll();
		if(Proyecto1Application.logUser!=null){
			if(Proyecto1Application.logUser.getRol().getName().equals("CLIENTE")){
				reservasList = reservasList.stream().filter(e->e.getCliente().equals(Proyecto1Application.logUser.getCliente())).collect(Collectors.toList());
			}
		}
    	reservaView.cargarReservas(reservasList);
    }

    private void venderReserva() {
    	Reserva reservaSeleccionada = reservaView.getReservaSeleccionada();
    	if (reservaSeleccionada != null) {
    		ventaFormController = new VentaFormController(reservaSeleccionada);
    		ventaFormController.getVentaReservaFormCompra().open();
    		ventaFormController.setChangeHandler(this::listarReservas);
    	}else {
    		Notification.show("Seleccione un reserva");
    	}
    }
    
    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public ReservaView getView(){
        return reservaView;
    }
}