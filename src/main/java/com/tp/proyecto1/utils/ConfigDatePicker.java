package com.tp.proyecto1.utils;

import java.util.Arrays;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;

public class ConfigDatePicker {
	
	public DatePicker setearLenguajeEspañol(DatePicker datePicker){
		datePicker.setI18n(       		
        		new DatePickerI18n().setWeek("Semana").setCalendar("Calendario")
			                        .setClear("Limpiar").setToday("Hoy")
			                        .setCancel("Cancelar").setFirstDayOfWeek(1)
			                        .setMonthNames(Arrays.asList("Enero", "Febrero",
		                                "Marzo", "Abril", "Mayo", "Junio",
		                                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
		                                "Diciembre")).setWeekdays(Arrays.asList("Domingo", "Lunes", "Martes", "Miercoles",
			                                "Jueves", "Viernes", "Sabado"))
			                        .setWeekdaysShort(Arrays.asList("Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa")));
		return datePicker;
	}
}
