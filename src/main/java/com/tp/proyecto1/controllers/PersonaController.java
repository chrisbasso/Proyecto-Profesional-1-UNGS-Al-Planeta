package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.Persona;
import com.tp.proyecto1.services.PersonaService;
import com.tp.proyecto1.views.PersonaView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class PersonaController {

	private PersonaView personaView;

	private PersonaService personaService;

	private ChangeHandler changeHandler;

	@Autowired
	public PersonaController(PersonaService personaService) {
		this.personaView = new PersonaView();
		this.personaService = personaService;
		setListeners();
		setComponents();
		listPersonas();
	}

	private void setComponents() {
		this.personaView.getGrid().addComponentColumn(this::createRemoveButton).setHeader("").setTextAlign(ColumnTextAlign.END);
	}

	private void setListeners() {
		setChangeHandler(this::listPersonas);
		personaView.getAddNewBtn().addClickListener(e-> savePersona());
	}

	private void savePersona() {
		String name = personaView.getTextName().getValue();
		String lastName = personaView.getTextLastName().getValue();
		String dni = personaView.getTextDni().getValue();
		String age = personaView.getTextAge().getValue();
		String email = "asd";
		Persona newPersona = new Persona(name, lastName, dni, age, email);
		personaService.save(newPersona);
		Notification.show("Persona Guardada");
		changeHandler.onChange();
	}

	private void deletePersona(Persona persona) {
		personaService.delete(persona);
		Notification.show("Persona Eliminada");
		changeHandler.onChange();
	}

	private Button createRemoveButton(Persona persona) {
		return new Button(VaadinIcon.TRASH.create(), clickEvent -> deletePersona(persona));
	}

	private void listPersonas() {
		personaView.getGrid().setItems(personaService.findAll());
	}
	public interface ChangeHandler {
		void onChange();
	}
	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}
	public PersonaView getView(){
		return personaView;
	}

}
