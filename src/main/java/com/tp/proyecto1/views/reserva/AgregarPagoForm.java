package com.tp.proyecto1.views.reserva;

import java.util.List;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class AgregarPagoForm extends Dialog{

	private VerticalLayout mainLayout;
	private NumberField pago;     
	private ComboBox<FormaDePago> cmbFormaPago;	
    private Text saldo;
    private Text sumaDePagos;    
    private Grid pagosAnteriores;
    private FormLayout form;
    private Button btnGuardar;
    private Button btnCancelar;
    private Button btnAgregar;
    private HorizontalLayout actions;
	private Double importeMaximo;
	
	/*
	 * Iniciar con el pago máximo para validar en el form que la 
	 * suma de pagos no se exceda. 	
	 */
    public AgregarPagoForm(Double importeMaximo) {
    	this.importeMaximo = importeMaximo;
		iniciliazarCampos();
    	inicializarForm();
    	inicializarActions();
    	inicializarGridPagos();
    	inicializarMainLayout();    	
    }
    /*
     * Inicializar el combo y el field para pagos.
     */
	private void iniciliazarCampos() {		
		cmbFormaPago = new ComboBox<FormaDePago>();
		pago = new NumberField();
		pago.setPrefixComponent(new Span("$"));
		pago.setMin(0.0);
		pago.setMax(importeMaximo);
		saldo = new Text("Por pagar: " + importeMaximo.toString() + "\n");
		sumaDePagos = new Text("");
	}
	/*
	 * Form para pago y combo de pagos. 
	 */
	private void inicializarForm() {
		form = new FormLayout();    	
    	form.addFormItem(pago, "Importe");
    	form.addFormItem(cmbFormaPago, "Forma de Pago");
    	form.addFormItem(saldo,"");
    	form.addFormItem(sumaDePagos,"");
    	btnAgregar = new Button("Agregar este pago", VaadinIcon.PLUS.create());
    	btnAgregar.setEnabled(false);
    	form.addFormItem(btnAgregar,"");    	
	}
	/*
	 * Cargar botones.
	 */
	private void inicializarActions() {
		btnGuardar= new Button("Guardar",VaadinIcon.CHECK_CIRCLE.create());
		btnGuardar.setEnabled(false);
		btnCancelar= new Button("Cancelar", VaadinIcon.CLOSE_CIRCLE.create());
    	actions = new HorizontalLayout();
        actions.add(btnGuardar, btnCancelar);
	}
	/*
	 * Cargar grid con detalle de pagos.
	 */
	private void inicializarGridPagos() {		
		pagosAnteriores = new Grid<>(Pago.class);
		pagosAnteriores.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
		pagosAnteriores.setSelectionMode(Grid.SelectionMode.NONE);
		pagosAnteriores.setHeightByRows(true);
		pagosAnteriores.setColumns("transaccion", "formaDePago", "importe", "fechaDePago");
		pagosAnteriores.getColumnByKey("transaccion").setVisible(false);
		pagosAnteriores.getColumnByKey("formaDePago").setHeader("Medio");		
	}
	/*
	 * Mostrar la etiqueta de suma de pagos.
	 */
	public void actualizarEtiquetaSumaDePagos(Double suma) {		
		sumaDePagos.setText("Pagado: " + suma.toString());		
	}
	/*
	 * Ocultar la etiqueta de suma de pagos.
	 */	
	public void ocultarEtiquetaSumaDePagos() {
		sumaDePagos.setText("");
	}
	/*
	 * Contenedor principal de todos los elementos
	 */
	private void inicializarMainLayout() {		
		mainLayout = new VerticalLayout();
    	mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form,saldo,sumaDePagos,pagosAnteriores,actions);
        mainLayout.setSizeFull();
    	this.add(mainLayout);
        this.setWidth("800px");
        this.setHeight("80%");
	}
	/*
	 * Cargar valores en el combo de formas de pago..
	 */
	public void cargarFormasDePago(List <FormaDePago> fdp) {
		cmbFormaPago.setItems(fdp);
	}
	/*
	 * Cargar valores en el grid de pagos anteriores..
	 */	
	public void cargarPagosAnteriores(List <Pago> pagos) {
		pagosAnteriores.setItems(pagos);		
	}
	/*
	 * Captar forma de pago seleccionada.
	 */
	public FormaDePago getFormaPagoSeleccionada() {
		return cmbFormaPago.getValue();
	}
	/*
	 * Captar importe de pago ingresado.
	 */	
	public Double getPagoIngresado() {
		if(pago.getValue() == null) {
			return 0.00;
		}else {
			return pago.getValue();
		}
	}
	/*
	 * Actualizar el importe máximo para el siguiente pago.
	 */
	public void actualizarPagoMaximo(Double nuevoImporte) {
		pago.clear();
		pago.setMax(nuevoImporte);
	}
	/*
	 * Habilitar el botón guardar.
	 */
	public void activarBtnGuardar() {
		btnGuardar.setEnabled(true);		
	}
	/*
	 * Deshabilitar el botón guardar.
	 */	
	public void desactivarBtnGuardar() {
		btnGuardar.setEnabled(false);
	}
	/*
	 * Habilitar el botón agregar.
	 */
	public void activarBtnAgregar() {
		btnAgregar.setEnabled(true);		
	}
	/*
	 * Deshabilitar el botón agregar.
	 */	
	public void desactivarBtnAgregar() {
		btnAgregar.setEnabled(false);
	}
	
	public void setListenerImporte(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
		pago.addValueChangeListener(e);
	}
	
	public void setListenerFDP(ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<FormaDePago>, FormaDePago>> e) {
		cmbFormaPago.addValueChangeListener(e);
	}
	
	public void setListenerBtnGuardar(ComponentEventListener<ClickEvent<Button>> e){
		btnGuardar.addClickListener(e);
	}
	
	public void setListenerBtnCancelar(ComponentEventListener<ClickEvent<Button>> e){
		btnCancelar.addClickListener(e);
	}
	
	public void setListenerBtnAgregar(ComponentEventListener<ClickEvent<Button>> e){
		btnAgregar.addClickListener(e);
	}
}