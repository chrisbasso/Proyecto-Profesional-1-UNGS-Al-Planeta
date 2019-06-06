package com.tp.proyecto1.views.eventos;

import com.tp.proyecto1.model.eventos.Evento;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

public class EventosClienteWindow extends Dialog {

	private Grid<Evento> grid = new Grid<>(Evento.class);

	public EventosClienteWindow() {

		setComponents();
		this.add(grid);
		this.setWidth("1020px");
		this.setHeight("400px");

	}

	private void setComponents() {

		grid.setColumns("id", "persona.nombre", "persona.apellido", "fecha", "hora","creadorEvento.user","usuarioAsignado.user", "cerradorEvento.user", "prioridad");
		grid.getColumnByKey("id").setHeader("Nº Evento");
		grid.getColumnByKey("creadorEvento.user").setHeader("Creador por:");
		grid.getColumnByKey("usuarioAsignado.user").setHeader("Asignado a:");
		grid.getColumnByKey("cerradorEvento.user").setHeader("Cerrado por:");
		grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

	}

	public Grid<Evento> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Evento> grid) {
		this.grid = grid;
	}
}
