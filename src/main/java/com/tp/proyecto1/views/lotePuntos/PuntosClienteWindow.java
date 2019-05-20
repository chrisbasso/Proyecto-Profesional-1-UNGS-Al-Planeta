package com.tp.proyecto1.views.lotePuntos;

import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

public class PuntosClienteWindow extends Dialog {

	private Grid<LotePunto> grid = new Grid<>(LotePunto.class);
    private Button btnClose;
	
	public PuntosClienteWindow() {

		setComponents();
		this.add(grid);
		this.setWidth("950px");
		this.setHeight("400px");

	}


	private void setComponents() {

		btnClose = new Button ("Cerrar");
		grid.setColumns("id", "fechaAlta", "fechaVencimiento", "cantidadPuntos","cantidadRestante");
		grid.getColumnByKey("id").setHeader("Nº Puntos");
		grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
		grid.getColumnByKey("fechaAlta").setHeader("Fecha de obtención");
		grid.getColumnByKey("fechaVencimiento").setHeader("Fecha de vencimiento");
		grid.getColumnByKey("cantidadPuntos").setHeader("Cantidad obtenida");
		grid.getColumnByKey("cantidadRestante").setHeader("Cantidad restante");

	}

	public Grid<LotePunto> getGrid() {
		return grid;
	}

	public void setGrid(Grid<LotePunto> grid) {
		this.grid = grid;
	}

	public Button getBtnClose() {
		return btnClose;
	}

	public void setBtnClose(Button btnClose) {
		this.btnClose = btnClose;
	}
}
