package com.tp.proyecto1.controllers.eventos;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.eventos.Recordatorio;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.EventoService;
import com.tp.proyecto1.services.RecordatorioService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.eventos.RecordatorioForm;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;

public class RecordatorioFormController
{
	
	private RecordatorioForm recordatorioForm;

    @Autowired
    private RecordatorioService recordatorioService;

    private ChangeHandler changeHandler;

    @Autowired
    private UserService userService;

    private Evento evento;
    
    private Recordatorio recordatorio;
	    
    private Binder<Recordatorio> binderRecordatorio = new Binder<>();

    
	public RecordatorioFormController() {
		Inject.Inject(this);
		this.recordatorioForm = new RecordatorioForm();
		setListeners();
		setBinders();
	}

	private void setListeners()
    {
        recordatorioForm.getBtnCancel().addClickListener(e-> recordatorioForm.close());
        recordatorioForm.getBtnSave().addClickListener(e-> saveRecordatorio());
    }
    private void saveRecordatorio()
    {
    	if(recordatorio==null){
    		recordatorio = setNewRecordatorio();
        }

        if (binderRecordatorio.writeBeanIfValid(recordatorio)) {

        	recordatorio.setEvento(evento);
        	recordatorioService.save(recordatorio);
        	recordatorioForm.close();
            Notification.show("Recordatorio agregado.");
            changeHandler.onChange();
        }
    }
    
    
    private Recordatorio setNewRecordatorio()
	{
    	Recordatorio recordatorio = new Recordatorio();
    	recordatorio.setFechaRecordatorio(recordatorioForm.getFechaRecordatorio().getValue());
    	recordatorio.setHoraRecordatorio(recordatorioForm.getHoraRecordatorio().getValue());
		return recordatorio;
	}

	private void setBinders()
    {
		setBinderFechaRecordatorio(recordatorioForm.getFechaRecordatorio(),Recordatorio::getFechaRecordatorio,Recordatorio::setFechaRecordatorio,true);
		setBinderFechaRecordatorioMayorAFechaApertura(recordatorioForm.getFechaRecordatorio(),Recordatorio::getFechaRecordatorio,Recordatorio::setFechaRecordatorio,true);
    	setBinderHorarioRecordatorio(recordatorioForm.getHoraRecordatorio(),Recordatorio::getHoraRecordatorio,Recordatorio::setHoraRecordatorio,true);
    	
    	binderRecordatorio.setBean(recordatorio);
    }
	
	private void setBinderFechaRecordatorio(DatePicker datePicker, ValueProvider<Recordatorio, LocalDate> valueProvider, Setter<Recordatorio, LocalDate> setter, boolean isRequiered){

        SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue() != null;
        Binder.Binding<Recordatorio, LocalDate> binding;
        if(isRequiered){
            binding = binderRecordatorio.forField(datePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderRecordatorio.forField(datePicker).bind(valueProvider, setter);
        }
        recordatorioForm.getBtnSave().addClickListener(event -> binding.validate());
    }
	
	
	private void setBinderFechaRecordatorioMayorAFechaApertura(DatePicker datePicker, ValueProvider<Recordatorio, LocalDate> valueProvider, Setter<Recordatorio, LocalDate> setter, boolean isRequiered){

        SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue().isAfter(evento.getFecha()) || datePicker.getValue().isEqual(evento.getFecha());
        Binder.Binding<Recordatorio, LocalDate> binding;
        if(isRequiered){
            binding = binderRecordatorio.forField(datePicker)
                    .withValidator(predicate, "La fecha elegida debe ser mayor a la fecha de apertura del evento.")
                    .bind(valueProvider, setter);
        }else{
            binding = binderRecordatorio.forField(datePicker).bind(valueProvider, setter);
        }
        recordatorioForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderHorarioRecordatorio(TimePicker timePicker, ValueProvider<Recordatorio, LocalTime> valueProvider, Setter<Recordatorio, LocalTime> setter, boolean isRequiered){

        SerializablePredicate<LocalTime> predicate = value -> timePicker.getValue() != null;
        Binder.Binding<Recordatorio, LocalTime> binding;
        if(isRequiered){
            binding = binderRecordatorio.forField(timePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderRecordatorio.forField(timePicker).bind(valueProvider, setter);
        }
        recordatorioForm.getBtnSave().addClickListener(event -> binding.validate());
    }


	public Dialog getView()
	{
		return recordatorioForm;
	}
	

	public ChangeHandler getChangeHandler() {
		return changeHandler;
	}

	public Evento getEvento()
	{
		return evento;
	}

	public void setEvento(Evento evento)
	{
		this.evento = evento;
	}

	public RecordatorioForm getRecordatorioForm()
	{
		return recordatorioForm;
	}

	public void setRecordatorioForm(RecordatorioForm consultaForm)
	{
		this.recordatorioForm = consultaForm;
	}

}
