package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.PasajerosGridComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class VentaForm extends Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private FormLayout formPuntos = new FormLayout();
	private BuscadorClientesComponent cliente;
	private Label descripcionCliente;
	private ComboBox<FormaDePago> formaPago;
//	private NumberField cantidadPasaje;
	private Checkbox usoPuntosCheck;
	private TextField puntosDisponibles;
	private NumberField puntosaUsar;
	private NumberField saldoPagar;
	private NumberField subtotal;
	private PasajerosGridComponent pasajerosGridComponent;
	private TextField espacio;

	private TextField id;
	private ComboBox<Provincia> provinciaDestino;
	private ComboBox<Ciudad> ciudadDEstino;
	private ComboBox<Continente> continenteDestino;
	private ComboBox<Pais> paisDestino;
	private ComboBox<Provincia> provinciaOrigen;
	private ComboBox<Ciudad> ciudadOrigen;
	private ComboBox<Continente> continenteOrigen;
	private ComboBox<Pais> paisOrigen;
	private ComboBox<Promocion> promocion;
	private TextField codTransporte;
	private TextField transporte;
	private TextField fechaSalida;
	private TextField horaSalida;
	private TextField puntosObtenidos;
	private TextField denoPromocion;
	private Button btnSave;
	private Button btnCancel;
	private Button btnFinalizarCompra;


	public VentaForm() {

		setComponents();
		setForm();
		setLayouts();

	}


	private void setComponents() {

		id = new TextField();
		ciudadDEstino = new ComboBox<>();
		ciudadDEstino.setItemLabelGenerator(Ciudad::getNombre);
		provinciaDestino = new ComboBox<>();
		provinciaDestino.setItemLabelGenerator(Provincia::getNombre);
		continenteDestino = new ComboBox<>();
		continenteDestino.setItemLabelGenerator(Continente::getNombre);
		paisDestino = new ComboBox<>();
		paisDestino.setItemLabelGenerator(Pais::getNombre);
		ciudadOrigen = new ComboBox<>();
		ciudadOrigen.setItemLabelGenerator(Ciudad::getNombre);
		provinciaOrigen = new ComboBox<>();
		provinciaOrigen.setItemLabelGenerator(Provincia::getNombre);
		continenteOrigen = new ComboBox<>();
		continenteOrigen.setItemLabelGenerator(Continente::getNombre);
		paisOrigen = new ComboBox<>();
		paisOrigen.setItemLabelGenerator(Pais::getNombre);
		codTransporte= new TextField();
		transporte= new TextField();
		fechaSalida= new TextField();
		horaSalida= new TextField();
		puntosObtenidos = new TextField();
		puntosObtenidos.setPattern("[0-9]*");
		denoPromocion = new TextField();

		btnFinalizarCompra = new Button("Finalizar Compra");
		btnSave	  = new Button("Guardar");
		btnCancel = new Button("Cancelar");
		formaPago = new ComboBox<>();
		formaPago.setItemLabelGenerator(FormaDePago::getDescripcion);
		formaPago.setRequired(true);;
		descripcionCliente = new Label();
		cliente = new BuscadorClientesComponent(descripcionCliente);
		cliente.getFiltro().setRequired(true);
		cliente.getFiltro().setHeight("40px");
//		cantidadPasaje = new NumberField();
//		cantidadPasaje.setMin(1);
//		cantidadPasaje.setMax(6);
//		cantidadPasaje.setValue((double) 1);
//		cantidadPasaje.setHasControls(true);
		usoPuntosCheck = new Checkbox();
		usoPuntosCheck.setValue(false);
		usoPuntosCheck.setMinWidth("135px");
		usoPuntosCheck.setEnabled(false);
		saldoPagar = new NumberField();
		saldoPagar.setPrefixComponent(new Span("$"));
		saldoPagar.setPreventInvalidInput(true);
		subtotal = new NumberField();
		subtotal.setPrefixComponent(new Span("$"));
		subtotal.setPreventInvalidInput(true);
		pasajerosGridComponent = new PasajerosGridComponent();
		pasajerosGridComponent.getGrid().setHeight("200px");
		promocion = new ComboBox<>();
		puntosDisponibles = new TextField();
		puntosaUsar = new NumberField();
		puntosaUsar.setHasControls(true);
		puntosaUsar.setMin(0);
		espacio = new TextField();
		espacio.setVisible(false);
		
	}

	private void setForm() {

		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

		form.addFormItem(continenteOrigen, "Continente Origen");
		form.addFormItem(paisOrigen, "Pais Origen");
		form.addFormItem(provinciaOrigen, "Provincia Origen");
		form.addFormItem(ciudadOrigen, "Ciudad Origen");
		form.addFormItem(continenteDestino, "Continente Destino");
		form.addFormItem(paisDestino, "País Destino");
		form.addFormItem(provinciaDestino, "Provincia Destino");
		form.addFormItem(ciudadDEstino, "Ciudad Destino");
		form.addFormItem(codTransporte, "Cod Transporte");
		form.addFormItem(transporte, "Transporte");
		form.addFormItem(fechaSalida, "Fecha Salida");
		form.addFormItem(horaSalida, "Hora Salida");
		form.addFormItem(cliente, "Nro de Cliente (*)");
		form.addFormItem(descripcionCliente, "Descripción");
		form.addFormItem(subtotal, "Subtotal");
		form.addFormItem(formaPago, "Forma de Pago (*)");
		form.addFormItem(saldoPagar, "Saldo a Pagar");
		form.addFormItem(promocion, "Promocion");
		form.addFormItem(puntosObtenidos, "Puntos Conseguidos");
		form.addFormItem(denoPromocion, "Deno. Promocion");
		formPuntos.addFormItem(usoPuntosCheck, "Uso de Puntos");
		formPuntos.addFormItem(espacio, "");
		formPuntos.addFormItem(puntosDisponibles, "Puntos Disponibles");
		formPuntos.addFormItem(puntosaUsar, "Puntos a Usar");
	}

	private void setLayouts() {
		HorizontalLayout actions = new HorizontalLayout();
		actions.add(btnSave, btnCancel, btnFinalizarCompra);

		mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
		mainLayout.add(form, pasajerosGridComponent, formPuntos, actions);
		mainLayout.setSizeFull();

		this.add(mainLayout);
		this.setWidth("1370px");
		this.setHeight("100%");
		this.setCloseOnOutsideClick(false);
	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	public FormLayout getForm() {
		return form;
	}

	public void setForm(FormLayout form) {
		this.form = form;
	}


	public ComboBox<FormaDePago> getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(ComboBox<FormaDePago> formaPago) {
		this.formaPago = formaPago;
	}

	public void setFormaPago(String formaPagoId) {
		this.formaPago.setId(formaPagoId);
	}


	public BuscadorClientesComponent getCliente() {
		return cliente;
	}
	public void setCliente(BuscadorClientesComponent cliente) {
		this.cliente = cliente;
	}

	public Checkbox getUsoPuntosCheck() {
		return usoPuntosCheck;
	}
	public void setUsoPuntosCheck(Checkbox usoPuntosCheck) {
		this.usoPuntosCheck = usoPuntosCheck;
	}
	public NumberField getSaldoPagar() {
		return saldoPagar;
	}
	public void setSaldoPagar(NumberField saldoPagar) {
		this.saldoPagar = saldoPagar;
	}

	public void setSaldoPagarDouble(Double saldoPagar) {
		this.saldoPagar.setValue(saldoPagar);
	}

	public NumberField getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(NumberField subtotal) {
		this.subtotal = subtotal;
	}

	public void setSubtotalDouble(Double subtotal) {
		this.subtotal.setValue(subtotal);
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Button getBtnFinalizarCompra() {
		return btnFinalizarCompra;
	}

	public PasajerosGridComponent getPasajerosGridComponent() {
		return pasajerosGridComponent;
	}

	public void setPasajerosGridComponent(PasajerosGridComponent pasajerosGridComponent) {
		this.pasajerosGridComponent = pasajerosGridComponent;
	}

	public FormLayout getFormPuntos() {
		return formPuntos;
	}

	public void setFormPuntos(FormLayout formPuntos) {
		this.formPuntos = formPuntos;
	}

	public TextField getEspacio() {
		return espacio;
	}

	public void setEspacio(TextField espacio) {
		this.espacio = espacio;
	}


	public void setId(TextField id) {
		this.id = id;
	}

	public ComboBox<Provincia> getProvinciaDestino() {
		return provinciaDestino;
	}

	public void setProvinciaDestino(ComboBox<Provincia> provinciaDestino) {
		this.provinciaDestino = provinciaDestino;
	}

	public ComboBox<Ciudad> getCiudadDEstino() {
		return ciudadDEstino;
	}

	public void setCiudadDEstino(ComboBox<Ciudad> ciudadDEstino) {
		this.ciudadDEstino = ciudadDEstino;
	}

	public ComboBox<Continente> getContinenteDestino() {
		return continenteDestino;
	}

	public void setContinenteDestino(ComboBox<Continente> continenteDestino) {
		this.continenteDestino = continenteDestino;
	}

	public ComboBox<Pais> getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(ComboBox<Pais> paisDestino) {
		this.paisDestino = paisDestino;
	}

	public ComboBox<Provincia> getProvinciaOrigen() {
		return provinciaOrigen;
	}

	public void setProvinciaOrigen(ComboBox<Provincia> provinciaOrigen) {
		this.provinciaOrigen = provinciaOrigen;
	}

	public ComboBox<Ciudad> getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(ComboBox<Ciudad> ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public ComboBox<Continente> getContinenteOrigen() {
		return continenteOrigen;
	}

	public void setContinenteOrigen(ComboBox<Continente> continenteOrigen) {
		this.continenteOrigen = continenteOrigen;
	}

	public ComboBox<Pais> getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(ComboBox<Pais> paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public TextField getCodTransporte() {
		return codTransporte;
	}

	public void setCodTransporte(TextField codTransporte) {
		this.codTransporte = codTransporte;
	}

	public TextField getTransporte() {
		return transporte;
	}

	public void setTransporte(TextField transporte) {
		this.transporte = transporte;
	}

	public TextField getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(TextField fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public TextField getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(TextField horaSalida) {
		this.horaSalida = horaSalida;
	}

	public void setBtnFinalizarCompra(Button btnFinalizarCompra) {
		this.btnFinalizarCompra = btnFinalizarCompra;
	}

	public Label getDescripcionCliente() {
		return descripcionCliente;
	}

	public void setDescripcionCliente(Label descripcionCliente) {
		this.descripcionCliente = descripcionCliente;
	}

	public ComboBox<Promocion> getPromocion() {
		return promocion;
	}

	public void setPromocion(ComboBox<Promocion> promocion) {
		this.promocion = promocion;
	}



	public TextField getPuntosObtenidos() {
		return puntosObtenidos;
	}



	public void setPuntosObtenidos(TextField puntosObtenidos) {
		this.puntosObtenidos = puntosObtenidos;
	}

	public TextField getDenoPromocion() {
		return denoPromocion;
	}



	public void setDenoPromocion(TextField denoPromocion) {
		this.denoPromocion = denoPromocion;
	}



	public TextField getPuntosDisponibles() {
		return puntosDisponibles;
	}



	public NumberField getPuntosaUsar() {
		return puntosaUsar;
	}



	public void setPuntosDisponibles(TextField puntosDisponibles) {
		this.puntosDisponibles = puntosDisponibles;
	}



	public void setPuntosaUsar(NumberField puntosaUsar) {
		this.puntosaUsar = puntosaUsar;
	}
}