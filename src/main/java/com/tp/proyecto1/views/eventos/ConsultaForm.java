package com.tp.proyecto1.views.eventos;

import java.util.ArrayList;
import java.util.List;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.ConfigDatePicker;
import com.tp.proyecto1.utils.PrioridadEvento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;

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
    private TextArea textAreaDescripcion = new TextArea("Descripción");
    private ComboBox<User> comboUsuarios = new ComboBox<>();
    private ComboBox<String> comboTipo = new ComboBox<>();
    private DatePicker fechaVencimiento = new DatePicker();
    private TimePicker horaVencimiento = new TimePicker();
    
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

        textAreaDescripcion.setRequired(true);
        comboTipo.setRequired(true);
        buscadorClientes.getFiltro().setRequired(true);
        nombre.setRequired(true);
        apellido.setRequired(true);
        telefono.setRequired(true);
        comboPrioridad.setRequired(true);
		ConfigDatePicker configDatePicker = new ConfigDatePicker();
		configDatePicker.setearLenguajeEspañol(fechaVencimiento);

		setPerfiles();

    }

    private void setForm() {
        form.addFormItem(comboTipo, "Tipo");
        form.addFormItem(comboUsuarios, "Usuario");
        form.addFormItem(buscadorClientes, "Nº Cliente");
        form.addFormItem(clienteDescripcion, "Descripción");
        form.addFormItem(nombre, "Nombre");
        form.addFormItem(apellido, "Apellido");
        form.addFormItem(email, "E-mail");
        form.addFormItem(telefono, "Teléfono");
        form.addFormItem(comboPrioridad, "Prioridad");
        form.addFormItem(checkInteresado,"");
        form.addFormItem(fechaVencimiento, "Vence el dia");
        form.addFormItem(horaVencimiento, "A las");
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

    private void setPerfiles() {

        if(Proyecto1Application.logUser.getRol().getName().equals("VENDEDOR")){
            comboUsuarios.setVisible(false);
        }

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

    public Button getBtnSave() {
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

    public void setMainLayout(VerticalLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public ComboBox<User> getComboUsuarios() {
        return comboUsuarios;
    }

    public void setComboUsuarios(ComboBox<User> comboUsuarios) {
        this.comboUsuarios = comboUsuarios;
    }

    public ComboBox<String> getComboTipo() {
        return comboTipo;
    }

    public void setComboTipo(ComboBox<String> comboTipo) {
        this.comboTipo = comboTipo;
    }

    public void setTextAreaDescripcion(TextArea textAreaDescripcion) {
        this.textAreaDescripcion = textAreaDescripcion;
    }

	public DatePicker getFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public void setFechaVencimiento(DatePicker fechaVencimiento)
	{
		this.fechaVencimiento = fechaVencimiento;
	}

	public TimePicker getHoraVencimiento()
	{
		return horaVencimiento;
	}

	public void setHoraVencimiento(TimePicker horaVencimiento)
	{
		this.horaVencimiento = horaVencimiento;
	}
}
