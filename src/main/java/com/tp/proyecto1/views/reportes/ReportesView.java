package com.tp.proyecto1.views.reportes;

import com.tp.proyecto1.utils.ConfigDatePicker;
import com.tp.proyecto1.utils.TipoReporte;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class ReportesView extends VerticalLayout {

	private ComboBox<String> comboTipoReporte = new ComboBox<>("Tipo Reporte");
	private ComboBox<String> comboTipoGrafico = new ComboBox<>("Tipo Gráfico");
	private ComboBox<Integer> comboBoxMeses = new ComboBox<>("Mes");
	private Component filtroDinamico = new ComboBox("");
	private DatePicker fechaDesde = new DatePicker("Fecha Desde");
	private DatePicker fechaHasta = new DatePicker("Fecha Hasta");
	private Button btnGenerar = new Button("Generar");
	private Component gridDinamico = new Grid();
	private HorizontalLayout hlCampos = new HorizontalLayout();

	private HorizontalLayout hlGraficos = new HorizontalLayout();

	public ReportesView() {

		setComponents();
		setLayout();

	}

	public void setLayout() {

		this.removeAll();
		hlCampos.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
		setCampos();
		this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.add(hlCampos,hlGraficos, gridDinamico);
		hlGraficos.setSizeFull();
	}

	public void setCampos() {
		hlCampos.removeAll();
		hlCampos.add(comboTipoReporte, filtroDinamico, fechaDesde, fechaHasta, comboTipoGrafico, comboBoxMeses, btnGenerar);
	}


	private void setComponents() {

		List<String> tiposReporte = new ArrayList<>();
		tiposReporte.add(TipoReporte.LOCAL.name());
		tiposReporte.add(TipoReporte.DESTINO.name());
		tiposReporte.add(TipoReporte.CLIENTE.name());
		tiposReporte.add(TipoReporte.VENDEDOR.name());
		tiposReporte.add(TipoReporte.EGRESOS.name());
		comboTipoReporte.setItems(tiposReporte);

		List<String> tiposGrafico = new ArrayList<>();
		tiposGrafico.add("Anual");
		tiposGrafico.add("Mensual");
		comboTipoGrafico.setItems(tiposGrafico);

		List<Integer> meses = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			meses.add(i+1);
		}
		comboBoxMeses.setItems(meses);

		((Grid)gridDinamico).setSizeFull();
		
		ConfigDatePicker configDatePickerFechaDesde = new ConfigDatePicker();
		ConfigDatePicker configDatePickerFechaHasta = new ConfigDatePicker();
		configDatePickerFechaHasta.setearLenguajeEspañol(fechaDesde);
		configDatePickerFechaHasta.setearLenguajeEspañol(fechaHasta);
	}

	public ComboBox<String> getComboTipoReporte() {
		return comboTipoReporte;
	}

	public void setComboTipoReporte(ComboBox<String> comboTipoReporte) {
		this.comboTipoReporte = comboTipoReporte;
	}

	public Component getFiltroDinamico() {
		return filtroDinamico;
	}

	public void setFiltroDinamico(Component filtroDinamico) {
		this.filtroDinamico = filtroDinamico;
	}

	public DatePicker getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(DatePicker fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public DatePicker getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(DatePicker fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Button getBtnGenerar() {
		return btnGenerar;
	}

	public void setBtnGenerar(Button btnGenerar) {
		this.btnGenerar = btnGenerar;
	}

	public Component getGridDinamico() {
		return gridDinamico;
	}

	public void setGridDinamico(Component gridDinamico) {
		this.gridDinamico = gridDinamico;
	}

	public ComboBox<String> getComboTipoGrafico() {
		return comboTipoGrafico;
	}

	public void setComboTipoGrafico(ComboBox<String> comboTipoGrafico) {
		this.comboTipoGrafico = comboTipoGrafico;
	}

	public ComboBox<Integer> getComboBoxMeses() {
		return comboBoxMeses;
	}

	public void setComboBoxMeses(ComboBox<Integer> comboBoxMeses) {
		this.comboBoxMeses = comboBoxMeses;
	}

	public HorizontalLayout getHlCampos() {
		return hlCampos;
	}

	public void setHlCampos(HorizontalLayout hlCampos) {
		this.hlCampos = hlCampos;
	}

	public HorizontalLayout getHlGraficos() {
		return hlGraficos;
	}

	public void setHlGraficos(HorizontalLayout hlGraficos) {
		this.hlGraficos = hlGraficos;
	}
}
