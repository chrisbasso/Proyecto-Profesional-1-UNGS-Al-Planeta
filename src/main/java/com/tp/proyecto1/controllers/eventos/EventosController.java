package com.tp.proyecto1.controllers.eventos;


import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.eventos.Reclamo;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.EventosView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@UIScope
public class EventosController {

	private EventosView eventosView;

	private ChangeHandler changeHandler;

	@Autowired
	EventoService eventoService;

	public EventosController() {
		Inject.Inject(this);
		this.eventosView = new EventosView();
		setListeners();
		setComponents();
		listarEventos();
	}

	private void setComponents() {
		this.eventosView.getGrid().addColumn(this::setTipo).setHeader("Tipo");
		this.eventosView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
		this.eventosView.getGrid().addComponentColumn(this::createCheckButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
	}

	private String setTipo(Evento evento) {
		String tipo;
		if(evento instanceof Reclamo){
			tipo = "Reclamo";
		}else{
			tipo = "Consulta";
		}
		return tipo;
	}


	private Button createCheckButton(Evento evento) {
		Button botonCheck = new Button(VaadinIcon.CHECK.create(), clickEvent -> cerrarEvento(evento));
        if(!evento.isAbierto()){
			botonCheck.setEnabled(false);
        }
		return botonCheck;
	}

	private void cerrarEvento(Evento evento){
		ConfirmationDialog confirmationDialog = new ConfirmationDialog("¿Realmente desea marcar el evento como resuelto?");
		confirmationDialog.open();
		confirmationDialog.getConfirmButton().addClickListener(e->{
			evento.setAbierto(false);
			eventoService.save(evento);
			listarEventos();
		});
	}

	private Button createEditButton(Evento evento) {
		return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
			ConsultaFormController consultaFormController = new ConsultaFormController();
			consultaFormController.setComponentsValues(evento);
			consultaFormController.getView().open();
			consultaFormController.setChangeHandler(this::listarEventos);
		});
	}


	private void setListeners() {
		setChangeHandler(this::listarEventos);
		eventosView.getNewConsultaButton().addClickListener(e->openConsultaForm());
		eventosView.getSearchButton().addClickListener(e->listarEventos());
	}

	private void openConsultaForm() {
		ConsultaFormController consultaFormController = new ConsultaFormController();
		consultaFormController.setChangeHandler(this::listarEventos);
		consultaFormController.getView().open();
	}

	private void listarEventos() {
		Evento eventoConsulta = new Evento();
		eventoConsulta.setAbierto(eventosView.getCheckAbierto().getValue());
        if(checkFiltros()){
            setParametrosBusqueda(eventoConsulta);
            eventosView.getGrid().setItems(eventoService.findEventos(eventoConsulta));
        }else{
            eventosView.getGrid().setItems(eventoService.findAll());
        }
	}

	private void setParametrosBusqueda(Evento eventoBusqueda) {

		eventoBusqueda.setPersona(new Persona());

		if(!eventosView.getIdFilter().isEmpty()){
			eventoBusqueda.setId(eventosView.getIdFilter().getValue().longValue());
		}
		if(!eventosView.getIdClienteFilter().isEmpty()){
			eventoBusqueda.getPersona().setId(eventosView.getIdClienteFilter().getValue().longValue());
		}
		if(!eventosView.getNombreFilter().isEmpty()){
			eventoBusqueda.getPersona().setNombre(eventosView.getNombreFilter().getValue());
		}
		if(!eventosView.getApellidoFilter().isEmpty()){
			eventoBusqueda.getPersona().setApellido(eventosView.getApellidoFilter().getValue());
		}
		if(!eventosView.getFechaFilter().isEmpty()){
			eventoBusqueda.setFecha(eventosView.getFechaFilter().getValue());
		}

	}

	private boolean checkFiltros() {
		return !eventosView.getIdFilter().isEmpty() || !eventosView.getIdClienteFilter().isEmpty() ||
				!eventosView.getNombreFilter().isEmpty() || !eventosView.getApellidoFilter().isEmpty() ||
				eventosView.getFechaFilter().getValue() != null || eventosView.getCheckAbierto().getValue();
	}


	public EventosView getEventosView() {
		return eventosView;
	}

	public void setEventosView(EventosView eventosView) {
		this.eventosView = eventosView;
	}

	private void setChangeHandler(ChangeHandler h) {
		changeHandler = h;
	}

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}


}
