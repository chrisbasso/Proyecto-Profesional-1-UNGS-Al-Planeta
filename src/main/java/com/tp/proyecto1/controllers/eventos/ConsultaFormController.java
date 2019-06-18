package com.tp.proyecto1.controllers.eventos;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.clientes.Interesado;
import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.eventos.Consulta;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.eventos.Reclamo;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.ConsultaForm;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
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
    
    private Binder<Evento> binderPersona = new Binder<>();

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
        consultaForm.getBtnSave().addClickListener(e-> saveConsulta());
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
            evento.setCreadorEvento(Proyecto1Application.logUser);
            evento.setUsuarioAsignado(Proyecto1Application.logUser);
        }
        else
        {
          //  evento.setUsuarioAsignado(consultaForm.getComboUsuarios().getValue());
        }
        if (consultaForm.getFechaVencimiento().getValue() != null &&
        			consultaForm.getHoraVencimiento().getValue() != null)
        {
        	if ((LocalDate.now().isEqual(consultaForm.getFechaVencimiento().getValue())
        				&& LocalTime.now().isBefore(consultaForm.getHoraVencimiento().getValue()))
        				|| LocalDate.now().isBefore(consultaForm.getFechaVencimiento().getValue()))
        	{
        		evento.setFechaVencimiento(consultaForm.getFechaVencimiento().getValue());
                evento.setHoraVencimiento(consultaForm.getHoraVencimiento().getValue());
        	}
        	else
        	{
        		Notification.show("La fecha de vencimiento no es valida.");
        		evento = null;
        		throw new IllegalArgumentException("La fecha de vencimiento no es valida.");
        	}
        }
        evento.setMensaje(consultaForm.getTextAreaDescripcion().getValue());
        evento.setPrioridad(consultaForm.getComboPrioridad().getValue());
       
        if (binderEvento.writeBeanIfValid(evento)) {

        	userService.save(Proyecto1Application.logUser);
        	eventoService.save(this.evento);
        	this.evento = null;
            changeHandler.onChange();
            Notification.show("Evento guardado.");
            consultaForm.close();
        }
        else
        {
        	 Notification.show("No se pudo guardar el evento. Revise los campos.");
        }
       
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
    	setBinderFieldDescripcion(consultaForm.getTextAreaDescripcion(), Evento::getMensaje, Evento::setMensaje, true);
    	setBinderFieldNombre(consultaForm.getNombre(), Persona::getNombre, Persona::setNombre, true);
    	setBinderFieldApellido(consultaForm.getApellido(), Persona::getApellido, Persona::setApellido, true);
    	setBinderFieldMail(consultaForm.getEmail(), Persona::getEmail, Persona::setEmail, true);
    	setBinderFieldTelefono(consultaForm.getTelefono(), Persona::getTelefono, Persona::setTelefono, true);
    	binderEvento.setBean(evento);
    }
    
    private void setBinderFieldDescripcion(AbstractField field, ValueProvider<Evento, String> valueProvider, Setter<Evento, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Evento, String> binding;
        if(isRequiered){
           binding = binderEvento.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderEvento.forField(field).bind(valueProvider, setter);
        }
        consultaForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderFieldNombre(AbstractField field, ValueProvider<Persona, String> valueProvider, Setter<Persona, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Persona, String> binding;
        if(isRequiered){
           binding = binderPersona.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPersona.forField(field).bind(valueProvider, setter);
        }
        consultaForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderFieldApellido(AbstractField field, ValueProvider<Persona, String> valueProvider, Setter<Persona, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Persona, String> binding;
        if(isRequiered){
           binding = binderPersona.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPersona.forField(field).bind(valueProvider, setter);
        }
        consultaForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    private void setBinderFieldTelefono(AbstractField field, ValueProvider<Persona, String> valueProvider, Setter<Persona, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Persona, String> binding;
        if(isRequiered){
           binding = binderPersona.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPersona.forField(field).bind(valueProvider, setter);
        }
        consultaForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderFieldMail(AbstractField field, ValueProvider<Persona, String> valueProvider, Setter<Persona, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty() ;
        Binder.Binding<Persona, String> binding;
        if(isRequiered){
           binding = binderPersona.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPersona.forField(field).bind(valueProvider, setter);
        }
        consultaForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
}