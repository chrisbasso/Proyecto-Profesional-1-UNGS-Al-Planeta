package com.tp.proyecto1.views.eventos;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

public class RecordatorioForm extends Dialog 
{
	 private VerticalLayout mainLayout = new VerticalLayout();
	 private FormLayout form = new FormLayout();
	 private DatePicker fechaRecordatorio;
	 private TimePicker horaRecordatorio;
	 
	 private Label mensajeEvento = new Label();
	 
	 private Button btnSave;
     private Button btnCancel;
	 
	 
	    public RecordatorioForm() {
	        setComponents();
	        setForm();
	        setLayouts();
	    }

		private void setComponents()
		{
			fechaRecordatorio = new DatePicker();
			horaRecordatorio = new TimePicker();
			btnSave = new Button("Agregar");
			btnCancel = new Button("Cancelar");
		}
		
		private void setForm()
		{
			form.addFormItem(mensajeEvento, "Mensaje del evento");
			form.addFormItem(fechaRecordatorio, "Fecha del recordatorio");
		    form.addFormItem(horaRecordatorio, "Hora del recordatorio");
		}
		
		private void setLayouts()
		{

		     HorizontalLayout actions = new HorizontalLayout();
		        actions.add(btnSave, btnCancel);

		        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
		        mainLayout.add(form, actions);
		        mainLayout.setSizeFull();

		        this.add(mainLayout);
		        this.setHeight("100%");
		        this.setCloseOnOutsideClick(false);
		}

		public DatePicker getFechaRecordatorio()
		{
			return fechaRecordatorio;
		}

		public void setFechaRecordatorio(DatePicker fechaRecordatorio)
		{
			this.fechaRecordatorio = fechaRecordatorio;
		}

		public TimePicker getHoraRecordatorio()
		{
			return horaRecordatorio;
		}

		public void setHoraRecordatorio(TimePicker horaRecordatorio)
		{
			this.horaRecordatorio = horaRecordatorio;
		}

		public Button getBtnSave()
		{
			return btnSave;
		}

		public void setBtnSave(Button btnSave)
		{
			this.btnSave = btnSave;
		}

		public Button getBtnCancel()
		{
			return btnCancel;
		}

		public void setBtnCancel(Button btnCancel)
		{
			this.btnCancel = btnCancel;
		}

		public Label getMensajeEvento()
		{
			return mensajeEvento;
		}

		public void setMensajeEvento(Label mensajeEvento)
		{
			this.mensajeEvento = mensajeEvento;
		}
}
