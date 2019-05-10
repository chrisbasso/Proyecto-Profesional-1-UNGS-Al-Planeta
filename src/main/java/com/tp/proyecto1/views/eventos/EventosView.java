package com.tp.proyecto1.views.eventos;

import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class EventosView extends FilterGridLayout<Evento> implements View {



	public EventosView() {
		super(Evento.class);
		setComponents();
		setLayout();
		setGrid();
	}

	@Override
	public void setComponents() {

	}

	@Override
	public void setLayout() {

		HorizontalLayout hlSpace = new HorizontalLayout();
		hlSpace.setWidthFull();
		this.hlActions.add(hlSpace);
	}

	@Override
	public void setGrid() {

		grid.setColumns("id", "cliente.id", "cliente.nombre", "cliente.apellido", "fecha", "hora");
		grid.getColumnByKey("id").setHeader("Nº Reclamo");
		grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
	}
}
