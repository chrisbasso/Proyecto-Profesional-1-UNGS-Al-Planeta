package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.Persona;
import com.tp.proyecto1.services.PersonaService;
import com.tp.proyecto1.views.PersonaView;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonaController {

	private PersonaView personaView;

	private PersonaService personaService;

	private ChangeHandler changeHandler;

	@Autowired
	public PersonaController(PersonaService personaService) {

		this.personaView = new PersonaView();
		this.personaService = personaService;
		setListeners();
		listPersonas();

	}

	private void setListeners() {

		setChangeHandler(this::listPersonas);
		personaView.getAddNewBtn().addClickListener(e-> savePersona());

	}

	private void savePersona() {
		Persona newPersona = new Persona(personaView.getTextName().getValue(), personaView.getTextLastName().getValue());
		personaService.save(newPersona);
		Notification.show("Persona guardada");
		changeHandler.onChange();
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
