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
import com.vaadin.flow.component.select.Select;

public class BackUpTomarForm extends Dialog{
	private Label lblPath;
	private Select<String> seleccionArchivo;
	private Button btnTomar;	
	private ProgressBar progressBar;
		
	public BackUpTomarForm() {
		this.setWidth("400px");
		this.setHeight("150px");
		lblPath = new Label();
		lblPath.setText("Este archivo reemplazará la base de datos acutal");
		this.add(lblPath);
		seleccionArchivo = new Select<>();
		this.add(seleccionArchivo);
		btnTomar = new Button("Tomar",VaadinIcon.FILE_PROCESS.create());
		btnTomar.setEnabled(false);
		this.add(btnTomar);
	}
	
	public void cargarArchivos(String[] listOfFiles) {		
		seleccionArchivo.setRequiredIndicatorVisible(true);
		seleccionArchivo.setLabel("Back up");
		seleccionArchivo.setItems(listOfFiles);
	}
		
	public String getNombreArchivo() {
		String ret = null;
		if(seleccionArchivo.getValue() != null &&
				!seleccionArchivo.getValue().isEmpty()) {
			ret = seleccionArchivo.getValue();
		}
		return ret;
	}
	
	public void habilitarTomar(Boolean opcion) {
		btnTomar.setEnabled(opcion);
	}
	
	public void cargarProgressBar() {
		progressBar = new ProgressBar();
		progressBar.setIndeterminate(true);	
		this.add(progressBar);
	}

	public void btnAceptarListener(ComponentEventListener<ClickEvent<Button>> e) {
		btnTomar.addClickListener(e);
	}
	
	public void seleccionListener(ValueChangeListener<? super ComponentValueChangeEvent<Select<String>, String>> e) {
		seleccionArchivo.addValueChangeListener(e);
	}
}