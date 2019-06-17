package com.tp.proyecto1.views.usuarios;

import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.Role;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UsuarioForm extends Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private FormLayout form = new FormLayout();
	private TextField txtUsuario = new TextField();
	private TextField txtPassword = new TextField();
	private ComboBox<Sucursal> comboSucursal = new ComboBox<>();
	private ComboBox<Role> comboRoles = new ComboBox<>();
	private Button btnGuardar = new Button("Guardar");
	private Button btnCancelar = new Button("Cancelar");

	public UsuarioForm() {

		setComponents();
		setForm();
		setLayouts();

	}

	private void setComponents() {

		comboSucursal.setItemLabelGenerator(Sucursal::getDescripcion);
		comboRoles.setItemLabelGenerator(Role::getName);

	}

	private void setForm() {
		form.addFormItem(txtUsuario, "Usuario");
		form.addFormItem(txtPassword, "Password");
		form.addFormItem(comboSucursal, "Sucursal");
		form.addFormItem(comboRoles, "Rol");
	}

	private void setLayouts() {
		HorizontalLayout actions = new HorizontalLayout();
		actions.add(btnGuardar, btnCancelar);

		mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
		mainLayout.add(form, actions);
		mainLayout.setSizeFull();

		this.add(mainLayout);
		this.setWidth("800px");
		this.setHeight("100%");

		this.setCloseOnOutsideClick(true);
	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public FormLayout getForm() {
		return form;
	}

	public void setForm(FormLayout form) {
		this.form = form;
	}


	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	public TextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(TextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public TextField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(TextField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public ComboBox<Sucursal> getComboSucursal() {
		return comboSucursal;
	}

	public void setComboSucursal(ComboBox<Sucursal> comboSucursal) {
		this.comboSucursal = comboSucursal;
	}

	public ComboBox<Role> getComboRoles() {
		return comboRoles;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public void setComboRoles(ComboBox<Role> comboRoles) {
		this.comboRoles = comboRoles;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}
}

