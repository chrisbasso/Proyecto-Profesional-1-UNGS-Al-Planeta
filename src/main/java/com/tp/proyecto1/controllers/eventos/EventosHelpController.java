package com.tp.proyecto1.controllers.eventos;

import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.EventosHelpView;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class EventosHelpController
{
	
	private EventosHelpView eventosHelpView;
	
	public EventosHelpController()
	{
		Inject.Inject(this);
		this.eventosHelpView = new EventosHelpView();
		setListeners();
	}

	private void setListeners()
	{
		eventosHelpView.getxBtn().addClickListener(e->eventosHelpView.close());
	}

	public Dialog getView()
	{
		return eventosHelpView;
	}

}
