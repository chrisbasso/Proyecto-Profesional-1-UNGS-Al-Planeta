package com.tp.proyecto1.views.reportes;

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

	private ComboBox<String> comboTipoReporte = new ComboBox<>("Tipo");
	private Component filtroDinamico = new ComboBox("");
	private DatePicker fechaDesde = new DatePicker("Fecha Desde");
	private DatePicker fechaHasta = new DatePicker("Fecha Hasta");
	private Button btnGenerar = new Button("Generar");
	private Component gridDinamico = new Grid();

	AreaChartExample areaChartExample = new AreaChartExample();

	public ReportesView() {

		setComponents();
		setLayout();

	}

	private void setLayout() {
		HorizontalLayout hlCampos = new HorizontalLayout();
		hlCampos.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
		hlCampos.add(comboTipoReporte, filtroDinamico, fechaDesde, fechaHasta, btnGenerar);
		this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		this.add(hlCampos, gridDinamico, areaChartExample);
	}

	private void setComponents() {

		List<String> tiposReporte = new ArrayList<>();
		tiposReporte.add(TipoReporte.LOCAL.name());
		tiposReporte.add(TipoReporte.DESTINO.name());
		tiposReporte.add(TipoReporte.CLIENTE.name());
		tiposReporte.add(TipoReporte.VENDEDOR.name());
		tiposReporte.add(TipoReporte.EGRESOS.name());
		comboTipoReporte.setItems(tiposReporte);

		((Grid)gridDinamico).setSizeFull();

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

	public AreaChartExample getAreaChartExample() {
		return areaChartExample;
	}

	public void setAreaChartExample(AreaChartExample areaChartExample) {
		this.areaChartExample = areaChartExample;
	}
}
