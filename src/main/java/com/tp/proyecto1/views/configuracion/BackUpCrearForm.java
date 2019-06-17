package com.tp.proyecto1.views.configuracion;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;

public class BackUpCrearForm extends Dialog{
	
	private TextField nombreArchivo;
	private Button btnAceptar;
	private ProgressBar progressBar;
	
	public BackUpCrearForm() {
		this.setWidth("400px");
		this.setHeight("150px");
		nombreArchivo = new TextField();
		nombreArchivo.setPlaceholder("Ingrese un nombre para el backup, sin extensión");
		nombreArchivo.setLabel("Ejemplo: backup lunes");
		this.add(nombreArchivo);
		btnAceptar = new Button("Crear",VaadinIcon.FILE_ADD.create());
		btnAceptar.setEnabled(false);
		this.add(btnAceptar);
	}
		
	public String getNombreArchivo() {
		String ret = null;
		if(nombreArchivo.getValue() != null &&
				!nombreArchivo.getValue().isEmpty()) {
			ret = nombreArchivo.getValue();
		}
		return ret;
	}
	
	public void habilitarGuardado(Boolean opcion) {
		btnAceptar.setEnabled(opcion);
	}
	
	public void nombreArchivoListener(ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> e) {
		nombreArchivo.addValueChangeListener(e);
	}
	
	public void btnAceptarListener(ComponentEventListener<ClickEvent<Button>> e) {
		btnAceptar.addClickListener(e);
	}
	
	public void cargarProgressBar() {
		progressBar = new ProgressBar();
		progressBar.setIndeterminate(true);	
		this.add(progressBar);
	}
}