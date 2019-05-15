package com.tp.proyecto1.views.eventos;

import java.util.ArrayList;
import java.util.List;

import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.PrioridadEvento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ConsultaForm extends Dialog {

    private VerticalLayout mainLayout = new VerticalLayout();
    private FormLayout form = new FormLayout();
    private Label clienteDescripcion = new Label();
    private BuscadorClientesComponent buscadorClientes = new BuscadorClientesComponent(clienteDescripcion);
    private TextField nombre = new TextField();
    private TextField apellido = new TextField();
    private TextField telefono = new TextField();
    private EmailField email = new EmailField();
    private ComboBox<String> comboPrioridad = new ComboBox<>();
    private Checkbox checkInteresado = new Checkbox("Interesado");
    private Button save = new Button("Guardar");
    private Button cancel = new Button("Cancelar");
    private TextArea textAreaDescripcion = new TextArea("Descripciòn");

    public ConsultaForm() {

        setComponents();
        setForm();
        setLayouts();

    }

    private void setComponents() {

        List<String> listaPrioridades = new ArrayList<>();
        listaPrioridades.add(PrioridadEvento.URGENTE.name());
        listaPrioridades.add(PrioridadEvento.ALTA.name());
        listaPrioridades.add(PrioridadEvento.MEDIA.name());
        listaPrioridades.add(PrioridadEvento.BAJA.name());
        comboPrioridad.setItems(listaPrioridades);

        textAreaDescripcion.setHeight("100px");
        textAreaDescripcion.setWidth("770px");

    }

    private void setForm() {
        form.addFormItem(buscadorClientes, "Nº Cliente");
        form.addFormItem(clienteDescripcion, "Descripción");
        form.addFormItem(nombre, "Nombre");
        form.addFormItem(apellido, "Apellido");
        form.addFormItem(email, "E-mail");
        form.addFormItem(telefono, "Teléfono");
        form.addFormItem(comboPrioridad, "Prioridad");
        form.addFormItem(checkInteresado,"");

    }

    private void setLayouts() {
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);

        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.add(form, textAreaDescripcion, actions);
        mainLayout.setSizeFull();

        this.add(mainLayout);
        this.setWidth("800px");
        this.setHeight("100%");
        this.setCloseOnOutsideClick(false);

    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public FormLayout getForm() {
        return form;
    }

    public TextField getNombre() {
        return nombre;
    }

    public TextField getApellido() {
        return apellido;
    }

    public TextField getTelefono() {
        return telefono;
    }

    public EmailField getEmail() {
        return email;
    }

    public Button getSave() {
        return save;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setForm(FormLayout form) {
        this.form = form;
    }

    public Label getClienteDescripcion() {
        return clienteDescripcion;
    }

    public void setClienteDescripcion(Label clienteDescripcion) {
        this.clienteDescripcion = clienteDescripcion;
    }

    public BuscadorClientesComponent getBuscadorClientes() {
        return buscadorClientes;
    }

    public void setBuscadorClientes(BuscadorClientesComponent buscadorClientes) {
        this.buscadorClientes = buscadorClientes;
    }

    public void setNombre(TextField nombre) {
        this.nombre = nombre;
    }

    public void setApellido(TextField apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(TextField telefono) {
        this.telefono = telefono;
    }

    public void setEmail(EmailField email) {
        this.email = email;
    }

    public ComboBox<String> getComboPrioridad() {
        return comboPrioridad;
    }

    public void setComboPrioridad(ComboBox<String> comboPrioridad) {
        this.comboPrioridad = comboPrioridad;
    }

    public Checkbox getCheckInteresado() {
        return checkInteresado;
    }

    public void setCheckInteresado(Checkbox checkInteresado) {
        this.checkInteresado = checkInteresado;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public TextArea getTextAreaDescripcion() {
        return textAreaDescripcion;
    }

    public void setTextAreaDescripcion(TextArea textAreaDescripcion) {
        this.textAreaDescripcion = textAreaDescripcion;
    }
}
