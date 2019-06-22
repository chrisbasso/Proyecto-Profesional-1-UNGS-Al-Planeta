package com.tp.proyecto1.utils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ConfirmationDialog extends Dialog {

	private VerticalLayout content = new VerticalLayout();
	private Label labelMensaje = new Label();

	private Button confirmButton;
	private Button cancelButton;
	private String mensaje;

	public ConfirmationDialog(String mensaje) {
		this.mensaje = mensaje;
		labelMensaje.setText(mensaje);
		this.setCloseOnEsc(false);
		this.setCloseOnOutsideClick(false);
		this.add(content);
		content.setSizeFull();
		content.setAlignItems(FlexComponent.Alignment.CENTER);
		confirmButton = new Button("Aceptar", event -> this.close());
		cancelButton = new Button("Cancelar", event -> this.close());
		content.add(labelMensaje, new HorizontalLayout(confirmButton,cancelButton));
		this.setWidth("400px");
		this.add(content);

	}
	public ConfirmationDialog(String mensaje, TextField textField) {
		this.mensaje = mensaje;
		labelMensaje.setText(mensaje);
		this.setCloseOnEsc(false);
		this.setCloseOnOutsideClick(false);
		this.add(content);
		content.setSizeFull();
		content.setAlignItems(FlexComponent.Alignment.CENTER);
		confirmButton = new Button("Aceptar", event -> this.close());
		cancelButton = new Button("Cancelar", event -> this.close());
		content.add(labelMensaje,textField, new HorizontalLayout(confirmButton,cancelButton));
		this.setWidth("400px");
		this.add(content);

	}

	public Button getConfirmButton() {
		return confirmButton;
	}

	public void setConfirmButton(Button confirmButton) {
		this.confirmButton = confirmButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public VerticalLayout getContent() {
		return content;
	}

	public void setContent(VerticalLayout content) {
		this.content = content;
	}
}
