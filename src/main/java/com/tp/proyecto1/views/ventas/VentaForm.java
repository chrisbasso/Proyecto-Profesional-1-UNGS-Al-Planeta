package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;

public class VentaForm extends Dialog {
		private VerticalLayout mainLayout = new VerticalLayout();
	    private FormLayout form = new FormLayout();
	    private ComboBox<Cliente> cliente;
		private ComboBox<FormaDePago> formaPago;
	    private NumberField cantidadPasaje;
	    private Checkbox usoPuntosCheck;
	    private NumberField saldoPagar;
	    private NumberField subtotal;

	    private Button btnSave;
	    private Button btnCancel;
	    private Button btnFinalizarCompra;

	    public VentaForm(Viaje viaje) {

	        setComponents();
	        setForm();
	        setLayouts();

	    }

	    private void setComponents() {

	    	btnFinalizarCompra = new Button("Finalizar Compra");
	    	btnSave	  = new Button("Guardar");
	    	btnCancel = new Button("Cancelar");
	        formaPago = new ComboBox<>();
	        formaPago.setItemLabelGenerator(FormaDePago::getDescripcion);
	        cliente = new ComboBox<>();
	        cliente.setItemLabelGenerator(Cliente::getIdToString);
	        cantidadPasaje = new NumberField();
	        cantidadPasaje.setMin(1);
	        cantidadPasaje.setMax(6);
	        cantidadPasaje.setValue((double) 1);
	        cantidadPasaje.setHasControls(true);
	        usoPuntosCheck = new Checkbox("Uso de Puntos");
	        usoPuntosCheck.setValue(true);
	        usoPuntosCheck.setMinWidth("135px");
	        saldoPagar = new NumberField();
	        saldoPagar.setPrefixComponent(new Span("$"));
	        saldoPagar.setPreventInvalidInput(true);
	        saldoPagar.setEnabled(false);
	        subtotal = new NumberField();
	        subtotal.setPrefixComponent(new Span("$"));
	        subtotal.setPreventInvalidInput(true);
	        subtotal.setEnabled(false);
	    }

	    private void setForm() {
	    	form.addFormItem(cliente, "Nro de Cliente");
	    	form.addFormItem(cantidadPasaje, "Cantidad de Pasajes");
	    	FormItem totalItem = form.addFormItem(subtotal, "Subtotal");
	    	totalItem.add(usoPuntosCheck);
	    	form.addFormItem(formaPago, "Forma de Pago");
	    	form.addFormItem(saldoPagar, "Saldo a Pagar");
	    }

	    private void setLayouts() {
	        HorizontalLayout actions = new HorizontalLayout();
	        actions.add(btnSave, btnCancel, btnFinalizarCompra);

	        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
	        mainLayout.add(form, actions);
	        mainLayout.setSizeFull();

	        this.add(mainLayout);
	        this.setWidth("800px");
	        this.setHeight("100%");

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

		public ComboBox<Cliente> getCliente() {
			return cliente;
		}
		public void setCliente(ComboBox<Cliente> cliente) {
			this.cliente = cliente;
		}
		
		public NumberField getCantidadPasaje() {
			return cantidadPasaje;
		}
		public void setCantidadPasaje(NumberField cantidadPasaje) {
			this.cantidadPasaje = cantidadPasaje;
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

		public void setBtnFinalizarCompra(Button btnFinalizarCompra) {
			this.btnFinalizarCompra = btnFinalizarCompra;
		}
}