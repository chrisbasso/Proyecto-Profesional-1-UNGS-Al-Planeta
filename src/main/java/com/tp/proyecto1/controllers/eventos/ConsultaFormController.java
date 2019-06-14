package com.tp.proyecto1.controllers.eventos;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.clientes.Interesado;
import com.tp.proyecto1.model.eventos.Consulta;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.eventos.Reclamo;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.ConsultaForm;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@UIScope
public class ConsultaFormController {

    private ConsultaForm consultaForm;

    @Autowired
    private EventoService eventoService;

    private ChangeHandler changeHandler;

    @Autowired
    private UserService userService;

    private Evento evento;
    
    private Binder<Evento> binderEvento = new Binder<>();

    public ConsultaFormController() {
        Inject.Inject(this);
        this.consultaForm = new ConsultaForm();
        setListeners();
        setComponents();
        setBinders();
    }

    private void setComponents() {
        consultaForm.getApellido().setEnabled(false);
        consultaForm.getNombre().setEnabled(false);
        consultaForm.getEmail().setEnabled(false);
        consultaForm.getTelefono().setEnabled(false);
        List<String> tipos = new ArrayList<>();
        tipos.add("Consulta");
        tipos.add("Reclamo");
        consultaForm.getComboTipo().setItems(tipos);
        consultaForm.getComboUsuarios().setItems(userService.findAll());
        consultaForm.getComboUsuarios().setItemLabelGenerator(User::getUser);
        consultaForm.getComboUsuarios().setEnabled(false);
    }

    private void setListeners() {

        consultaForm.getCheckInteresado().addValueChangeListener(e-> cambiarAinteresado());
        consultaForm.getCancel().addClickListener(e-> consultaForm.close());
        consultaForm.getSave().addClickListener(e-> saveConsulta());
    }

    private void saveConsulta() {

        if(evento==null)
        {
            if(consultaForm.getComboTipo().getValue().equals("Consulta"))
            {
                evento = new Consulta();
            }
            else
            {
                evento = new Reclamo();
            }
            evento.setAbierto(true);
            if(consultaForm.getCheckInteresado().getValue())
            {
                Interesado interesado = new Interesado();
                interesado.setApellido(consultaForm.getApellido().getValue());
                interesado.setNombre(consultaForm.getNombre().getValue());
                interesado.setEmail(consultaForm.getEmail().getValue());
                interesado.setTelefono(consultaForm.getTelefono().getValue());
                evento.setPersona(interesado);  
            }
            else
            {
                Cliente cliente = consultaForm.getBuscadorClientes().getCliente();
                evento.setPersona(cliente);
            }
            evento.setFecha(LocalDate.now());
            evento.setHora(LocalTime.now());
            evento.setFechaVencimiento(consultaForm.getFechaVencimiento().getValue());
            evento.setHoraVencimiento(consultaForm.getHoraVencimiento().getValue());
            evento.setCreadorEvento(Proyecto1Application.logUser);
            evento.setUsuarioAsignado(Proyecto1Application.logUser);
        }
        else
        {
            evento.setUsuarioAsignado(consultaForm.getComboUsuarios().getValue());
        }
        evento.setMensaje(consultaForm.getTextAreaDescripcion().getValue());
        evento.setPrioridad(consultaForm.getComboPrioridad().getValue());
       
        eventoService.save(this.evento);
        changeHandler.onChange();
        consultaForm.close();
    }

    private void cambiarAinteresado() {

        if(consultaForm.getCheckInteresado().getValue()){
            consultaForm.getApellido().setEnabled(true);
            consultaForm.getNombre().setEnabled(true);
            consultaForm.getEmail().setEnabled(true);
            consultaForm.getTelefono().setEnabled(true);
            consultaForm.getBuscadorClientes().setEnabled(false);
        }else{
            consultaForm.getApellido().setEnabled(false);
            consultaForm.getNombre().setEnabled(false);
            consultaForm.getEmail().setEnabled(false);
            consultaForm.getTelefono().setEnabled(false);
            consultaForm.getBuscadorClientes().setEnabled(true);
        }

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public ConsultaForm getView() {
        return consultaForm;
    }

    public void setComponentsValues(Evento evento) {

        this.evento = evento;

        if(evento instanceof Reclamo){
            consultaForm.getComboTipo().setValue("Reclamo");
        }else{
            consultaForm.getComboTipo().setValue("Consulta");
        }

        if(evento.getPersona() instanceof Interesado){
            consultaForm.getNombre().setValue(evento.getPersona().getNombre());
            consultaForm.getApellido().setValue(evento.getPersona().getApellido());
            consultaForm.getEmail().setValue(evento.getPersona().getEmail());
            consultaForm.getTelefono().setValue(evento.getPersona().getTelefono());
            consultaForm.getCheckInteresado().setValue(true);
        }else{
            consultaForm.getBuscadorClientes().getFiltro().setValue(evento.getPersona().getId().toString());
        }
        consultaForm.getComboUsuarios().setValue(evento.getUsuarioAsignado());
        consultaForm.getTextAreaDescripcion().setValue(evento.getMensaje());
        consultaForm.getComboPrioridad().setValue(evento.getPrioridad());
        consultaForm.getCheckInteresado().setEnabled(false);
        consultaForm.getBuscadorClientes().setEnabled(false);
        consultaForm.getComboTipo().setEnabled(false);
        consultaForm.getComboUsuarios().setEnabled(true);
        consultaForm.getFechaVencimiento().setValue(evento.getFechaVencimiento());
        consultaForm.getHoraVencimiento().setValue(evento.getHoraVencimiento());
    }
    
    private void setBinders()
    {
    	
    	
    	
    	binderEvento.setBean(evento);
    }
    
    
    private void setBinderFechaVencimiento(DatePicker datePicker, ValueProvider<Evento, LocalDate> valueProvider, Setter<Evento, LocalDate> setter, boolean isRequiered){

        SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue() != null;
        Binder.Binding<Evento, LocalDate> binding;
        if(isRequiered){
            binding = binderEvento.forField(datePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderEvento.forField(datePicker).bind(valueProvider, setter);
        }
        consultaForm.getSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderHoraVencimiento(TimePicker timePicker, ValueProvider<Evento, LocalTime> valueProvider, Setter<Evento, LocalTime> setter, boolean isRequiered){

        SerializablePredicate<LocalTime> predicate = value -> timePicker.getValue() != null;
        Binder.Binding<Evento, LocalTime> binding;
        if(isRequiered){
            binding = binderEvento.forField(timePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderEvento.forField(timePicker).bind(valueProvider, setter);
        }
        consultaForm.getSave().addClickListener(event -> binding.validate());
    }
    
}