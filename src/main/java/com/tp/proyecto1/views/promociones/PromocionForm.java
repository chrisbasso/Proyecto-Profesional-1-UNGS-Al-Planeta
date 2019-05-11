package com.tp.proyecto1.views.promociones;

import java.util.ArrayList;
import java.util.Collection;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class PromocionForm extends Dialog
{

	private VerticalLayout mainLayout = new VerticalLayout();
    private FormLayout form = new FormLayout();
    private TextField nombre;
    private NumberField cantidadPasajes;
    private ComboBox<String> tipoPromocion;
    private DatePicker fechaVencimiento;
    private NumberField nroFloat;
    private TextArea textAreaDescripcion;
    private ComboBox<Destino> destinos;
    private ComboBox<Viaje> viajes;
    private ComboBox<TagDestino> tagsDestino;
    
    private Button btnSave;
    private Button btnCancel;

    public PromocionForm() {

        setComponents();
        setForm();
        setLayouts();

    }

    private void setComponents() {

        btnSave = new Button("Guardar");
        btnCancel = new Button("Cancelar");
       
        tipoPromocion = new ComboBox<>();
        Collection<String> items = new ArrayList<String>();
        items.add("Descuento");
        items.add("Puntos");
        tipoPromocion.setItems(items);
       // tipoPromocion.setItemLabelGenerator(TipoTransporte::getDescripcion);
        nombre = new TextField();
        fechaVencimiento = new DatePicker();
        nroFloat = new NumberField();
        nroFloat.setWidth("192px");
        nroFloat.setMin(0);
        nroFloat.setPreventInvalidInput(true);
        //nroFloat.setPrefixComponent(new Span("$"));
        textAreaDescripcion = new TextArea("Descripción");
        textAreaDescripcion.setHeight("100px");
        textAreaDescripcion.setWidth("770px");
        
        cantidadPasajes = new NumberField();
        cantidadPasajes.setPattern("[0-9]*");
        cantidadPasajes.setMin(0);
        cantidadPasajes.setPreventInvalidInput(true);
       
        destinos = new ComboBox<>();
        destinos.setItemLabelGenerator(Destino::toString);
        viajes = new ComboBox<>();
        viajes.setItemLabelGenerator(Viaje::toString);
        tagsDestino = new ComboBox<>();
        tagsDestino.setItemLabelGenerator(TagDestino::getDescripcion);
        
    }

    private void setForm() {
        form.addFormItem(nombre, "Nombre");
        form.addFormItem(fechaVencimiento, "Fecha Vencimiento");
        form.addFormItem(cantidadPasajes, "Cantidad de pasajes");
        form.addFormItem(tipoPromocion, "Tipo de promocion");
        form.addFormItem(nroFloat, "Bonificador");
        form.addFormItem(destinos, "Destinos afectados");
        form.addFormItem(viajes, "Viajes afectados");
        form.addFormItem(tagsDestino, "Tags afectados");
    }

    private void setLayouts() {
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(btnSave, btnCancel);

        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form,textAreaDescripcion, actions);
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

    public TextField getNombre() {
        return nombre;
    }

    public void setNombre(TextField nombre) {
        this.nombre = nombre;
    }


    public ComboBox<String> getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(ComboBox<String> tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public DatePicker getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(DatePicker fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public NumberField getNroFloat() {
        return nroFloat;
    }

    public void setNroFloat(NumberField nroFloat) {
        this.nroFloat = nroFloat;
    }

    public TextArea getTextAreaDescripcion() {
        return textAreaDescripcion;
    }

    public void setTextAreaDescripcion(TextArea textAreaDescripcion) {
        this.textAreaDescripcion = textAreaDescripcion;
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

	public NumberField getCantidadPasajes() {
		return cantidadPasajes;
	}

	public void setCantidadPasajes(NumberField cantidadPasajes) {
		this.cantidadPasajes = cantidadPasajes;
	}

	public ComboBox<Destino> getDestinos() {
		return destinos;
	}

	public void setDestinos(ComboBox<Destino> destinos) {
		this.destinos = destinos;
	}

	public ComboBox<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(ComboBox<Viaje> viajes) {
		this.viajes = viajes;
	}

	public ComboBox<TagDestino> getTagsDestino() {
		return tagsDestino;
	}

	public void setTagsDestino(ComboBox<TagDestino> tagsDestino) {
		this.tagsDestino = tagsDestino;
	}

}
