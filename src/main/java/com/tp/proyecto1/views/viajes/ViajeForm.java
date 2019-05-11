package com.tp.proyecto1.views.viajes;

import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.TipoTransporte;
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
import com.vaadin.flow.component.timepicker.TimePicker;

public class ViajeForm extends Dialog {

    private VerticalLayout mainLayout = new VerticalLayout();
    private FormLayout form = new FormLayout();
    private TextField pais;
    private TextField ciudad;
    private ComboBox<TipoTransporte> transporte;
    private TextField codTransporte;
    private TextField clase;
    private NumberField capacidad;
    private DatePicker fechaSalida;
    private TimePicker horaSalida;
    private DatePicker fechaLlegada;
    private TimePicker horaLlegada;
    private NumberField precio;
    private ComboBox<TagDestino> tagDestino;
    private TextArea textAreaDescripcion;
    private TextArea textAreaRecomendaciones;

    private Button btnSave;
    private Button btnCancel;

    public ViajeForm() {

        setComponents();
        setForm();
        setLayouts();

    }

    private void setComponents() {

        btnSave = new Button("Guardar");
        btnCancel = new Button("Cancelar");
        transporte = new ComboBox<>();
        transporte.setItemLabelGenerator(TipoTransporte::getDescripcion);
        tagDestino = new ComboBox<>();
        tagDestino.setItemLabelGenerator(TagDestino::getDescripcion);
        codTransporte = new TextField();
        clase = new TextField();
        capacidad = new NumberField();
        capacidad.setPattern("[0-9]*");
        capacidad.setPreventInvalidInput(true);
        ciudad = new TextField();
        pais = new TextField();
        fechaSalida = new DatePicker();
        horaSalida = new TimePicker();
        fechaLlegada = new DatePicker();
        horaLlegada = new TimePicker();
        precio = new NumberField();
        textAreaDescripcion = new TextArea("Descripción");
        textAreaRecomendaciones = new TextArea("Recomendaciones");

        textAreaDescripcion.setHeight("100px");
        textAreaDescripcion.setWidth("770px");
        textAreaRecomendaciones.setHeight("100px");
        textAreaRecomendaciones.setWidth("770px");
        precio.setWidth("192px");
        precio.setMin(0);
        precio.setPreventInvalidInput(true);

        precio.setPrefixComponent(new Span("$"));

    }

    private void setForm() {
        form.addFormItem(pais, "País");
        form.addFormItem(ciudad, "Ciudad");
        form.addFormItem(fechaSalida, "Fecha Salida");
        form.addFormItem(horaSalida, "Hora Salida");
        form.addFormItem(fechaLlegada, "Fecha Llegada");
        form.addFormItem(horaLlegada, "Hora Llegada");
        form.addFormItem(transporte, "Transporte");
        form.addFormItem(codTransporte, "Cod. Transporte");
        form.addFormItem(clase, "Clase");
        form.addFormItem(capacidad, "Capacidad");
        form.addFormItem(precio, "Precio");
        form.addFormItem(tagDestino, "Tag Destino");

    }

    private void setLayouts() {
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(btnSave, btnCancel);

        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form,textAreaDescripcion, textAreaRecomendaciones, actions);
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

    public TextField getPais() {
        return pais;
    }

    public void setPais(TextField pais) {
        this.pais = pais;
    }

    public TextField getCiudad() {
        return ciudad;
    }

    public void setCiudad(TextField ciudad) {
        this.ciudad = ciudad;
    }

    public ComboBox<TipoTransporte> getTransporte() {
        return transporte;
    }

    public void setTransporte(ComboBox<TipoTransporte> transporte) {
        this.transporte = transporte;
    }

    public TextField getCodTransporte() {
        return codTransporte;
    }

    public void setCodTransporte(TextField codTransporte) {
        this.codTransporte = codTransporte;
    }

    public TextField getClase() {
        return clase;
    }

    public void setClase(TextField clase) {
        this.clase = clase;
    }

    public NumberField getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(NumberField capacidad) {
        this.capacidad = capacidad;
    }

    public DatePicker getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(DatePicker fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public TimePicker getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(TimePicker horaSalida) {
        this.horaSalida = horaSalida;
    }

    public DatePicker getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(DatePicker fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public TimePicker getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(TimePicker horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public NumberField getPrecio() {
        return precio;
    }

    public void setPrecio(NumberField precio) {
        this.precio = precio;
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

    public ComboBox<TagDestino> getTagDestino() {
        return tagDestino;
    }

    public void setTagDestino(ComboBox<TagDestino> tagDestino) {
        this.tagDestino = tagDestino;
    }

    public TextArea getTextAreaRecomendaciones() {
        return textAreaRecomendaciones;
    }

    public void setTextAreaRecomendaciones(TextArea textAreaRecomendaciones) {
        this.textAreaRecomendaciones = textAreaRecomendaciones;
    }
}
