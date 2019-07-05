package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.PasajerosGridComponent;
import com.vaadin.flow.component.button.Button;
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

public class VentaClienteForm extends Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private FormLayout formPuntos = new FormLayout();
	private BuscadorClientesComponent cliente;
	private Label descripcionCliente;
	private ComboBox<FormaDePago> formaPago;
	private NumberField saldoPagar;
	private NumberField subtotal;
	private PasajerosGridComponent pasajerosGridComponent;

	private TextField provinciaDestino;
	private TextField ciudadDEstino;
	private TextField continenteDestino;
	private TextField paisDestino;
	private TextField provinciaOrigen;
	private TextField ciudadOrigen;
	private TextField continenteOrigen;
	private TextField paisOrigen;
	private TextField promocion;
	private TextField codTransporte;
	private TextField transporte;
	private TextField fechaSalida;
	private TextField horaSalida;
	private TextField puntosObtenidos;
	private TextField denoPromocion;
	private Button btnCancel;

	public VentaClienteForm() {

		setComponents();
		setForm();
		setLayouts();
	}
	
	public TextField getProvinciaDestino()
	{
		return provinciaDestino;
	}

	public void setProvinciaDestino(TextField provinciaDestino)
	{
		this.provinciaDestino = provinciaDestino;
	}

	public TextField getCiudadDEstino()
	{
		return ciudadDEstino;
	}

	public void setCiudadDEstino(TextField ciudadDEstino)
	{
		this.ciudadDEstino = ciudadDEstino;
	}

	public TextField getContinenteDestino()
	{
		return continenteDestino;
	}

	public void setContinenteDestino(TextField continenteDestino)
	{
		this.continenteDestino = continenteDestino;
	}

	public TextField getPaisDestino()
	{
		return paisDestino;
	}

	public void setPaisDestino(TextField paisDestino)
	{
		this.paisDestino = paisDestino;
	}

	public TextField getProvinciaOrigen()
	{
		return provinciaOrigen;
	}

	public void setProvinciaOrigen(TextField provinciaOrigen)
	{
		this.provinciaOrigen = provinciaOrigen;
	}

	public TextField getCiudadOrigen()
	{
		return ciudadOrigen;
	}

	public void setCiudadOrigen(TextField ciudadOrigen)
	{
		this.ciudadOrigen = ciudadOrigen;
	}

	public TextField getContinenteOrigen()
	{
		return continenteOrigen;
	}

	public void setContinenteOrigen(TextField continenteOrigen)
	{
		this.continenteOrigen = continenteOrigen;
	}

	public TextField getPaisOrigen()
	{
		return paisOrigen;
	}

	public void setPaisOrigen(TextField paisOrigen)
	{
		this.paisOrigen = paisOrigen;
	}

	public TextField getPromocion()
	{
		return promocion;
	}

	public void setPromocion(TextField promocion)
	{
		this.promocion = promocion;
	}


	private void setComponents() {
		ciudadDEstino = new TextField();
		provinciaDestino = new TextField();
		continenteDestino = new TextField();
		paisDestino = new TextField();
		ciudadOrigen = new TextField();
		provinciaOrigen = new TextField();
		continenteOrigen = new TextField();
		paisOrigen = new TextField();
		codTransporte= new TextField();
		transporte= new TextField();
		fechaSalida= new TextField();
		horaSalida= new TextField();
		puntosObtenidos = new TextField();
		puntosObtenidos.setPattern("[0-9]*");
		denoPromocion = new TextField();

		btnCancel = new Button("Cerrar");
		formaPago = new ComboBox<>();
		formaPago.setItemLabelGenerator(FormaDePago::getDescripcion);
		formaPago.setRequired(true);;
		descripcionCliente = new Label();
		cliente = new BuscadorClientesComponent(descripcionCliente);
		cliente.getFiltro().setRequired(true);
		cliente.getFiltro().setHeight("40px");
		saldoPagar = new NumberField();
		saldoPagar.setPrefixComponent(new Span("$"));
		saldoPagar.setPreventInvalidInput(true);
		subtotal = new NumberField();
		subtotal.setPrefixComponent(new Span("$"));
		subtotal.setPreventInvalidInput(true);
		pasajerosGridComponent = new PasajerosGridComponent();
		pasajerosGridComponent.getGrid().setHeight("110px");
		promocion = new TextField();
		
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
	}

	private void setLayouts() {
		HorizontalLayout actions = new HorizontalLayout();
		actions.add(btnCancel);

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

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
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

	public Label getDescripcionCliente() {
		return descripcionCliente;
	}

	public void setDescripcionCliente(Label descripcionCliente) {
		this.descripcionCliente = descripcionCliente;
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
}
