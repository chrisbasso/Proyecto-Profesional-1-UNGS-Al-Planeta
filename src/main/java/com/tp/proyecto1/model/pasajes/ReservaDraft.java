package com.tp.proyecto1.model.pasajes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.tp.proyecto1.model.users.User;
import com.vaadin.flow.component.UI;

@Entity
public class ReservaDraft{

	@Id
	@GeneratedValue
	private Long id;

	private String usuario;
	private String reserva;
	
	public ReservaDraft() {
	}
	
	public ReservaDraft(Reserva reserva) {
		/*
		 * this.usuario = (User)
		 * UI.getCurrent().getSession().getAttribute("usuarioLogueado"); this.reserva =
		 * reserva;
		 */	}
}
