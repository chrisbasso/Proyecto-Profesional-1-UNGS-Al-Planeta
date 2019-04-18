package com.tp.proyecto1.views.clientes;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class NewClienteForm extends Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private TextField firstName;
	private TextField lastName;
	private TextField phone;
	private EmailField email;
	private DatePicker birthDate;
	private Button save;
	private Button cancel;

	public NewClienteForm() {

		setComponents();
		setForm();
		setLayouts();

	}

	private void setComponents() {
		firstName = new TextField();
		firstName.setValueChangeMode(ValueChangeMode.EAGER);
		lastName = new TextField();
		lastName.setValueChangeMode(ValueChangeMode.EAGER);
		phone = new TextField();
		phone.setValueChangeMode(ValueChangeMode.EAGER);
		email = new EmailField();
		email.setValueChangeMode(ValueChangeMode.EAGER);
		birthDate = new DatePicker();
		save = new Button("Guardar");
		cancel = new Button("Cancelar");
	}

	private void setForm() {
		form.addFormItem(firstName, "Nombre");
		form.addFormItem(lastName, "Apellido");
		form.addFormItem(birthDate, "Fecha de Nacimiento");
		form.addFormItem(email, "E-mail");
		form.addFormItem(phone, "Teléfono");
	}

	private void setLayouts() {
		HorizontalLayout actions = new HorizontalLayout();
		actions.add(save, cancel);

		mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
		mainLayout.add(form, actions);
		mainLayout.setSizeFull();

		this.add(mainLayout);
		this.setWidth("700px");
		this.setHeight("220px");
	}
	public void clean() {
		firstName.setValue("");
		lastName.setValue("");
		phone.setValue("");
		email.setValue("");
		birthDate.setValue(null);
	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public FormLayout getForm() {
		return form;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public TextField getPhone() {
		return phone;
	}

	public EmailField getEmail() {
		return email;
	}

	public DatePicker getBirthDate() {
		return birthDate;
	}

	public Button getSave() {
		return save;
	}

	public Button getCancel() {
		return cancel;
	}

}
