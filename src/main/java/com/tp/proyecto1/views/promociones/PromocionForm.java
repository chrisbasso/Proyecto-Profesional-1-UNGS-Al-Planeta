package com.tp.proyecto1.views.promociones;

import java.util.ArrayList;
import java.util.Collection;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.Viaje;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class PromocionForm extends Dialog
{

	private VerticalLayout mainLayout = new VerticalLayout();
    private FormLayout form = new FormLayout();
    private TextField nombre;
    private TextField cantidadPasajes;
    private ComboBox<String> tipoPromocion;
    private DatePicker fechaVencimiento;
    private TextField nroFloat;
    private TextArea textAreaDescripcion;
    private MultiselectComboBox<Viaje> viajes;
   // private MultiselectComboBox<TagDestino> tagsDestino;
    private MultiselectComboBox<Destino> destinos;
    
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
        tipoPromocion.setPattern("Puntos|Descuento");
        tipoPromocion.setPreventInvalidInput(true);
        nombre = new TextField();
        fechaVencimiento = new DatePicker();
        nroFloat = new TextField();
        nroFloat.setWidth("192px");
        nroFloat.setPreventInvalidInput(true);
        nroFloat.setEnabled(false);
        textAreaDescripcion = new TextArea("Descripción");
        textAreaDescripcion.setHeight("100px");
        textAreaDescripcion.setWidth("770px");
        
        cantidadPasajes = new TextField();
        cantidadPasajes.setPattern("[0-9]*");
        cantidadPasajes.setPreventInvalidInput(true);
       
        destinos = new MultiselectComboBox<>();
        destinos.setLabel("Seleccione destinos");
        destinos.setItemLabelGenerator(Destino::toString);
        viajes = new MultiselectComboBox<>();
        viajes.setLabel("Seleccione viajes");
        viajes.setItemLabelGenerator(Viaje::toString);
       /* tagsDestino = new MultiselectComboBox<>();
        tagsDestino.setLabel("Seleccione tags");
        tagsDestino.setItemLabelGenerator(TagDestino::getDescripcion);*/
        
    }

    private void setForm() {
        form.addFormItem(nombre, "Nombre");
        form.addFormItem(fechaVencimiento, "Fecha Vencimiento");
        form.addFormItem(cantidadPasajes, "Cantidad de pasajes");
        form.addFormItem(tipoPromocion, "Tipo de promocion");
        form.addFormItem(nroFloat, "Bonificador");
        form.addFormItem(destinos, "Destinos afectados");
        form.addFormItem(viajes, "Viajes afectados");
        //form.addFormItem(tagsDestino, "Tags afectados");
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

    public TextField getNroFloat() {
        return nroFloat;
    }

    public void setNroFloat(TextField nroFloat) {
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

	public TextField getCantidadPasajes() {
		return cantidadPasajes;
	}

	public void setCantidadPasajes(TextField cantidadPasajes) {
		this.cantidadPasajes = cantidadPasajes;
	}

	public MultiselectComboBox<Destino> getDestinos() {
		return destinos;
	}

	public void setDestinos(MultiselectComboBox<Destino> destinos) {
		this.destinos = destinos;
	}

	public MultiselectComboBox<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(MultiselectComboBox<Viaje> viajes) {
		this.viajes = viajes;
	}

	/*public MultiselectComboBox<TagDestino> getTagsDestino() {
		return tagsDestino;
	}

	public void setTagsDestino(MultiselectComboBox<TagDestino> tagsDestino) {
		this.tagsDestino = tagsDestino;
	}*/

}
