package com.tp.proyecto1.controllers.usuarios;

import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.Role;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.SucursalService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.GenericDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.usuarios.UsuarioForm;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@UIScope
public class UsuarioFormController {

	private UsuarioForm usuarioForm;

	private ChangeHandler changeHandler;

	@Autowired
	private UserService userService;
	@Autowired
	private SucursalService sucursalService;

	private User user;

	private Binder<User> binderUser = new Binder<>();

	public UsuarioFormController(User user) {
		Inject.Inject(this);
		this.usuarioForm = new UsuarioForm();
		this.user = user;
		setListeners();
		setComponents();
		setBinders();
	}

	private void setComponents() {


		List<Role> roles = userService.getRoles();
		usuarioForm.getComboRoles().setItems(roles);

		usuarioForm.getComboSucursal().setItems(sucursalService.findAll());

		if(user!=null){
			if(user.getCliente()!=null){
				usuarioForm.getBuscadorClientes().getFiltro().setValue(user.getCliente().getId().toString());
			}
		}

	}

	private void setListeners() {
		usuarioForm.getBtnGuardar().addClickListener(e-> saveUser());
		usuarioForm.getBtnCancelar().addClickListener(e-> usuarioForm.close());
		usuarioForm.getComboRoles().addValueChangeListener(e-> verificarRol());
	}

	private void verificarRol() {

		if(usuarioForm.getComboRoles().getValue().getName().equals("CLIENTE")){
			usuarioForm.getBuscadorClientes().setEnabled(true);
		}else{
			usuarioForm.getBuscadorClientes().setEnabled(false);
			usuarioForm.getBuscadorClientes().getFiltro().setValue("");
			usuarioForm.getDescripcionCliente().setText("");
		}
	}

	private void saveUser() {

		boolean esNuevo = false;
		if(user==null){
			user = new User();
			esNuevo = true;
		}

		if(esNuevo){
			if (binderUser.writeBeanIfValid(user)) {
				if(userService.valideUser(user)){
					GenericDialog dialog = new GenericDialog("El usuario ya existe");
					return;
				}
			}
		}
		if (binderUser.writeBeanIfValid(user)) {
				user.setCliente(usuarioForm.getBuscadorClientes().getCliente());
				userService.save(user);
				usuarioForm.close();
				changeHandler.onChange();
				Notification.show("Usuario Guardado");
		}
	}

	public void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public UsuarioForm getView() {
		return usuarioForm;
	}

	private void setBinders() {
		setBinderField(usuarioForm.getTxtUsuario(), User::getUser, User::setUser, true);
		setBinderField(usuarioForm.getEmailfield(), User::getEmail, User::setEmail, true);
		setBinderField(usuarioForm.getTxtPassword(), User::getPassword, User::setPassword, true);
		setBinderFieldComboRol(usuarioForm.getComboRoles(), User::getRol, User::setRol, true);
		setBinderFieldComboSucursal(usuarioForm.getComboSucursal(), User::getSucursal, User::setSucursal, true);
		binderUser.setBean(user);
	}

	private void setBinderField(AbstractField field, ValueProvider<User, String> valueProvider, Setter<User, String> setter, boolean isRequiered){

		SerializablePredicate<String> predicate = value -> !field.isEmpty();
		Binder.Binding<User, String> binding;
		if(isRequiered){
			binding = binderUser.forField(field)
					.withValidator(predicate, "El campo es obligatorio")
					.bind(valueProvider, setter);
		}else{
			binding = binderUser.forField(field).bind(valueProvider, setter);
		}
		usuarioForm.getBtnGuardar().addClickListener(event -> binding.validate());
	}

	private void setBinderFieldComboSucursal(ComboBox combo, ValueProvider<User, Sucursal> valueProvider, Setter<User, Sucursal> setter, boolean isRequiered){

		SerializablePredicate<Sucursal> predicate = value -> combo.getValue() != null;
		Binder.Binding<User, Sucursal> binding;

		if(isRequiered){
			binding = binderUser.forField(combo)
					.withValidator(predicate, "El campo es obligatorio")
					.bind(valueProvider, setter);
		}else{
			binding = binderUser.forField(combo).bind(valueProvider, setter);
		}
		usuarioForm.getBtnGuardar().addClickListener(event -> binding.validate());
	}

	private void setBinderFieldComboRol(ComboBox combo, ValueProvider<User, Role> valueProvider, Setter<User, Role> setter, boolean isRequiered){

		SerializablePredicate<Role> predicate = value -> combo.getValue() != null;
		Binder.Binding<User, Role> binding;

		if(isRequiered){
			binding = binderUser.forField(combo)
					.withValidator(predicate, "El campo es obligatorio")
					.bind(valueProvider, setter);
		}else{
			binding = binderUser.forField(combo).bind(valueProvider, setter);
		}
		usuarioForm.getBtnGuardar().addClickListener(event -> binding.validate());
	}

	public UsuarioForm getUsuarioForm() {
		return usuarioForm;
	}

	public void setUsuarioForm(UsuarioForm usuarioForm) {
		this.usuarioForm = usuarioForm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
