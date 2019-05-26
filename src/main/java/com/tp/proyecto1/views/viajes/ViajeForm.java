package com.tp.proyecto1.views.viajes;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.timepicker.TimePicker;
import org.vaadin.gatanaso.MultiselectComboBox;

public class ViajeForm extends Dialog {

    private VerticalLayout mainLayout = new VerticalLayout();
    private FormLayout form = new FormLayout();
    private ComboBox<Pais> pais;
    private ComboBox<Ciudad> ciudad;
    private ComboBox<TipoTransporte> transporte;
    private TextField codTransporte;
    private TextField clase;
    private TextField capacidad;
    private DatePicker fechaSalida;
    private TimePicker horaSalida;
    private Button btnNuevoPais = new Button("Nuevo");
    private Button btnNuevaCiudad = new Button("Nueva");
    private Button btnNuevoTag = new Button("Nuevo");
    private TextField compNuevoPais = new TextField();
    private TextField compNuevaCiudad = new TextField();
    private TextField compNuevoTag = new TextField();

    private NumberField precio;
    private NumberField cantidadDias;
    private NumberField cantidadHoras;
    private MultiselectComboBox<TagDestino> tagDestino;
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
        tagDestino = new MultiselectComboBox<>();
        tagDestino.setItemLabelGenerator(TagDestino::getDescripcion);
        tagDestino.setWidth("192px");
        codTransporte = new TextField();
        clase = new TextField();
        capacidad = new TextField();
        capacidad.setPattern("[0-9]*");
        capacidad.setPreventInvalidInput(true);
        ciudad = new ComboBox<>();
        ciudad.setItemLabelGenerator(Ciudad::getNombre);
        pais = new ComboBox<>();
        pais.setItemLabelGenerator(Pais::getNombre);
        fechaSalida = new DatePicker();
        horaSalida = new TimePicker();
        cantidadDias = new NumberField();
        cantidadDias.setMin(0);
        cantidadDias.setHasControls(true);
        cantidadDias.setValue(0.0);
        cantidadHoras = new NumberField();
        cantidadHoras.setValue(1d);
        cantidadHoras.setMin(0);
        cantidadHoras.setValue(0.0);
        cantidadHoras.setMax(23);
        cantidadHoras.setHasControls(true);
        precio = new NumberField();
        textAreaDescripcion = new TextArea("Descripción");
        textAreaRecomendaciones = new TextArea("Recomendaciones");

        textAreaDescripcion.setHeight("80px");
        textAreaDescripcion.setWidth("770px");
        textAreaRecomendaciones.setHeight("80px");
        textAreaRecomendaciones.setWidth("770px");
        precio.setWidth("192px");
        precio.setMin(0);
        precio.setPreventInvalidInput(true);

        precio.setPrefixComponent(new Span("$"));

        btnNuevaCiudad.addThemeVariants(ButtonVariant.LUMO_SMALL);
        btnNuevoPais.addThemeVariants(ButtonVariant.LUMO_SMALL);
        btnNuevoTag.addThemeVariants(ButtonVariant.LUMO_SMALL);

        btnNuevaCiudad.setEnabled(false);

    }

    private void setForm() {

        FormLayout.FormItem paisItem = form.addFormItem(pais,"País");
        HorizontalLayout hlPais = new HorizontalLayout();
        compNuevoPais.setWidth("112px");
        compNuevoPais.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        hlPais.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        hlPais.add(compNuevoPais,btnNuevoPais);
        paisItem.add(hlPais);
        FormLayout.FormItem ciudadItem = form.addFormItem(ciudad,"Ciudad");
        HorizontalLayout hlCiudad = new HorizontalLayout();
        compNuevaCiudad.setWidth("112px");
        compNuevaCiudad.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        hlCiudad.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        hlCiudad.add(compNuevaCiudad,btnNuevaCiudad);
        ciudadItem.add(hlCiudad);
        form.addFormItem(fechaSalida, "Fecha Salida");
        form.addFormItem(horaSalida, "Hora Salida");
        form.addFormItem(cantidadDias, "Días");
        form.addFormItem(cantidadHoras, "Horas");
        form.addFormItem(transporte, "Transporte");
        form.addFormItem(codTransporte, "Cod. Transporte");
        form.addFormItem(clase, "Clase");
        form.addFormItem(capacidad, "Capacidad");
        form.addFormItem(precio, "Precio");
        FormLayout.FormItem tagItem = form.addFormItem(tagDestino,"Tag Destino");
        HorizontalLayout hlTag = new HorizontalLayout();
        compNuevoTag.setWidth("112px");
        compNuevoTag.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        hlTag.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        hlTag.add(compNuevoTag,btnNuevoTag);
        tagItem.add(hlTag);

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


    public ComboBox<Pais> getPais() {
        return pais;
    }

    public void setPais(ComboBox<Pais> pais) {
        this.pais = pais;
    }

    public ComboBox<Ciudad> getCiudad() {
        return ciudad;
    }

    public void setCiudad(ComboBox<Ciudad> ciudad) {
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

    public TextField getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(TextField capacidad) {
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

    public NumberField getCantidadDias() {
        return cantidadDias;
    }

    public Button getBtnNuevoPais() {
        return btnNuevoPais;
    }

    public void setBtnNuevoPais(Button btnNuevoPais) {
        this.btnNuevoPais = btnNuevoPais;
    }

    public Button getBtnNuevaCiudad() {
        return btnNuevaCiudad;
    }

    public void setBtnNuevaCiudad(Button btnNuevaCiudad) {
        this.btnNuevaCiudad = btnNuevaCiudad;
    }

    public Button getBtnNuevoTag() {
        return btnNuevoTag;
    }

    public void setBtnNuevoTag(Button btnNuevoTag) {
        this.btnNuevoTag = btnNuevoTag;
    }

    public TextField getCompNuevoPais() {
        return compNuevoPais;
    }

    public void setCompNuevoPais(TextField compNuevoPais) {
        this.compNuevoPais = compNuevoPais;
    }

    public TextField getCompNuevaCiudad() {
        return compNuevaCiudad;
    }

    public void setCompNuevaCiudad(TextField compNuevaCiudad) {
        this.compNuevaCiudad = compNuevaCiudad;
    }

    public TextField getCompNuevoTag() {
        return compNuevoTag;
    }

    public void setCompNuevoTag(TextField compNuevoTag) {
        this.compNuevoTag = compNuevoTag;
    }

    public void setCantidadDias(NumberField cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public NumberField getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(NumberField cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
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

    public MultiselectComboBox<TagDestino> getTagDestino() {
        return tagDestino;
    }

    public void setTagDestino(MultiselectComboBox<TagDestino> tagDestino) {
        this.tagDestino = tagDestino;
    }

    public TextArea getTextAreaRecomendaciones() {
        return textAreaRecomendaciones;
    }

    public void setTextAreaRecomendaciones(TextArea textAreaRecomendaciones) {
        this.textAreaRecomendaciones = textAreaRecomendaciones;
    }
}
