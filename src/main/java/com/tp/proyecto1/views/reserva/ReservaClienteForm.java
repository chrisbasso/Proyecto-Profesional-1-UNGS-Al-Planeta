package com.tp.proyecto1.views.reserva;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class ReservaClienteForm extends Dialog
{
	
	private VerticalLayout mainLayout;
    private FormLayout form;
    private HorizontalLayout actions;
    private Viaje viaje;
    private TextField id;
	private TextField provinciaDestino;
	private TextField ciudadDestino;
	private TextField continenteDestino;
	private TextField paisDestino;
	private TextField provinciaOrigen;
	private TextField ciudadOrigen;
	private TextField continenteOrigen;
	private TextField paisOrigen;
	private TextField codTransporte;
	private TextField transporte;
	private TextField fecha;
	private TextField hora;
	private NumberField precioUnitario;
	private NumberField precioTotal;
	private NumberField cantidadPasajes;
	private NumberField sumaDePagos;
	private NumberField saldoPagar;
    private Button btnCancel;
    
    public ReservaClienteForm(Viaje viaje) {
    	if(viaje != null) {
    		this.viaje = viaje;
    		iniciliazarCampos();
    		cargarValores();
        	setReadOnly(); 
        	inicializarForm();
        	inicializarActions();        
        	inicializarMainLayout();	
    	}else {
    		// Prevenir que invoquen el form sin un viaje
    		Notification.show("Error al invocar ReservaForm.");
    	}		
    }
    
    private void iniciliazarCampos() {
		id = new TextField();  
		provinciaDestino = new TextField();
		ciudadDestino = new TextField();
		continenteDestino = new TextField();
		paisDestino = new TextField();
		provinciaOrigen = new TextField();
		ciudadOrigen = new TextField();
		continenteOrigen = new TextField();
		paisOrigen = new TextField();
		codTransporte= new TextField();
		transporte= new TextField();
		fecha = new TextField();
		hora = new TextField();
		precioUnitario= new NumberField();
		precioUnitario.setClassName(".v-numberfield");
		precioUnitario.setPrefixComponent(new Span("$"));
		cantidadPasajes= new NumberField();		
		cantidadPasajes.setValue(1d);
		cantidadPasajes.setMin(1);
		cantidadPasajes.setMax(viaje.getPasajesRestantes());
		cantidadPasajes.setHasControls(false);
		precioTotal= new NumberField();
		precioTotal.setPrefixComponent(new Span("$"));
		sumaDePagos = new NumberField();		
		sumaDePagos.setPrefixComponent(new Span("$"));
		sumaDePagos.setMin(0.0);
		saldoPagar = new NumberField();
		saldoPagar.setPrefixComponent(new Span("$"));
		saldoPagar.setMin(0.0);
	}

	private void cargarValores() {
		id.setValue(viaje.getId().toString());
    	provinciaDestino.setValue(viaje.getDestino().getProvincia().getNombre());
    	ciudadDestino.setValue(viaje.getDestino().getNombre());
    	paisDestino.setValue(viaje.getDestino().getProvincia().getPais().getNombre());
    	continenteDestino.setValue(viaje.getDestino().getProvincia().getPais().getContinente().getNombre());
		provinciaOrigen.setValue(viaje.getOrigen().getProvincia().getNombre());
		ciudadOrigen.setValue(viaje.getOrigen().getNombre());
		paisOrigen.setValue(viaje.getOrigen().getProvincia().getPais().getNombre());
		continenteOrigen.setValue(viaje.getOrigen().getProvincia().getPais().getContinente().getNombre());
    	codTransporte.setValue(viaje.getTransporte().getCodTransporte());
    	transporte.setValue(viaje.getTransporte().getTipo().getDescripcion());
    	fecha.setValue(viaje.getFechaSalida().toString());
    	hora.setValue(viaje.getHoraSalida().toString());
    	precioUnitario.setValue(viaje.getPrecio());
    	precioTotal.setValue(viaje.getPrecio());
    	saldoPagar.setValue(viaje.getPrecio());
	}
	
	 private void setReadOnly() {
	    	id.setReadOnly(true);  
			provinciaDestino.setReadOnly(true);
			ciudadDestino.setReadOnly(true);
			paisDestino.setReadOnly(true);
			continenteDestino.setReadOnly(true);
			provinciaOrigen.setReadOnly(true);
			ciudadOrigen.setReadOnly(true);
			paisOrigen.setReadOnly(true);
			continenteOrigen.setReadOnly(true);
			codTransporte.setReadOnly(true);  
			transporte.setReadOnly(true);  
			fecha.setReadOnly(true);
			hora.setReadOnly(true);
			precioUnitario.setReadOnly(true);
			precioTotal.setReadOnly(true);
			sumaDePagos.setReadOnly(true);
			saldoPagar.setReadOnly(true);
			cantidadPasajes.setReadOnly(true);
	    }
	 
	 private void inicializarForm() {
			HorizontalLayout pagos = new HorizontalLayout();
			pagos.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
			pagos.add(sumaDePagos);
			
			form = new FormLayout();
			form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
			form.addFormItem(continenteOrigen, "Continente Origen");
			form.addFormItem(paisOrigen, "Pais Origen");
			form.addFormItem(provinciaOrigen, "Provincia Origen");
			form.addFormItem(ciudadOrigen, "Ciudad Origen");
			form.addFormItem(continenteDestino, "Continente Destino");
			form.addFormItem(paisDestino, "País Destino");
			form.addFormItem(provinciaDestino, "Provincia Destino");
			form.addFormItem(ciudadDestino, "Ciudad Destino");
	    	form.addFormItem(codTransporte, "Cod Transporte");
	    	form.addFormItem(transporte, "Transporte");
	    	form.addFormItem(fecha, "Fecha");
	    	form.addFormItem(hora, "Hora");
	    	form.addFormItem(cantidadPasajes, "Cantidad de pasajes");
	    	form.addFormItem(precioUnitario, "Precio unitario");
	    	form.addFormItem(precioTotal, "Precio Total");
	    	form.addFormItem(saldoPagar, "Saldo");
	    	form.addFormItem(pagos, "Pagado");    	
		}

		private void inicializarActions() {
			btnCancel= new Button("Cerrar");
	    	actions = new HorizontalLayout();
	        actions.add(btnCancel/*, btnComprar*/);//comente comprar ya que va a estar por ahora en la view de reservas
		}

		private void inicializarMainLayout() {
			mainLayout = new VerticalLayout();
	    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
	        mainLayout.add(form,actions);
	        mainLayout.setSizeFull();
	    	this.add(mainLayout);
	        this.setWidth("1370px");
	        this.setHeight("100%");
		}
		
		
		public double getPrecioTotal() {
			return precioTotal.getValue();
		}
		
		public Integer cantidadPasajesSeleccionados() {
			return cantidadPasajes.getValue().intValue();
		}
		
		public void actualizarPrecioTotal(double nuevoPrecTotal) {
			precioTotal.setReadOnly(false); 
			precioTotal.setValue(nuevoPrecTotal); 
			precioTotal.setReadOnly(true);
		}
		
		public void actualizarPagos(double nuevoPagos) {
			sumaDePagos.setReadOnly(false); 
			sumaDePagos.setValue(nuevoPagos); 
			sumaDePagos.setReadOnly(true);
		}	
		
		public void actualizarSaldo(double saldo) {
			saldoPagar.setReadOnly(false);
			saldoPagar.setValue(saldo);
			saldoPagar.setReadOnly(true);
		}
		
		public void setModoModificacion(double pasajes, Cliente cliente, double pago) {
			cantidadPasajes.setValue(pasajes);	
			precioTotal.setValue(pasajes * viaje.getPrecio());
			actualizarPagos(pago);
		}
		
		public void setListenerCantPasajes(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
			cantidadPasajes.addValueChangeListener(e);
		}

		public void setListenerBtnCancel(ComponentEventListener<ClickEvent<Button>> e) {
			btnCancel.addClickListener(e);
		}	
}
