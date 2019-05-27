package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Transaccion;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.repository.pasajes.PagoRepository;
import com.tp.proyecto1.services.*;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.utils.TipoReporte;
import com.tp.proyecto1.views.reportes.AreaChart;
import com.tp.proyecto1.views.reportes.DonutChart;
import com.tp.proyecto1.views.reportes.ReportesView;
import com.tp.proyecto1.views.reportes.VerticalChart;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@UIScope
public class ReportesController {

	private ReportesView reportesView;

	@Autowired
	DestinoService destinoService;

	@Autowired
	UserService userService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	TransaccionService transaccionService;

	@Autowired
	PagosService pagosService;


	public ReportesController() {
		Inject.Inject(this);
		this.reportesView = new ReportesView();
		setComponents();
		setListeners();
	}

	private void setListeners() {

		reportesView.getComboTipoReporte().addValueChangeListener(e->setFiltroDinamico());
		reportesView.getBtnGenerar().addClickListener(e-> generarReporte());

	}

	private void generarReporte() {

		LocalDate fechaDesde = reportesView.getFechaDesde().getValue();
		LocalDate fechaHasta = reportesView.getFechaHasta().getValue();
		String tipoReporte = reportesView.getComboTipoReporte().getValue();


		if(tipoReporte.equals(TipoReporte.VENDEDOR.name())){

			User vendedor = ((ComboBox<User>)reportesView.getFiltroDinamico()).getValue();
			Grid<Pago> pagoGrid = new Grid<>(Pago.class);
			setGridPago(pagoGrid);
			reportesView.setGridDinamico(pagoGrid);
			Transaccion transaccionExample = new Transaccion();
			transaccionExample.setVendedor(vendedor);
			Pago pagoExample = new Pago();
			pagoExample.setTransaccion(transaccionExample);
			List<Pago> pagos = pagosService.findPagos(pagoExample, fechaDesde, fechaHasta);
			pagoGrid.setItems(pagos);
			reportesView.setLayout();
			List<Double> listaMensual = new ArrayList<>(12);
			for (int i = 0; i < 12; i++) {
				listaMensual.add(0.0);
			}
			for (Pago pago : pagos) {
				int indiceMes = pago.getFechaDePago().getMonthValue()-1;
				listaMensual.set(indiceMes,listaMensual.get(indiceMes) + pago.getImporte());
			}
			AreaChart areaChart = new AreaChart(listaMensual);
			DonutChart donutChart = new DonutChart(listaMensual);
			VerticalChart verticalChart = new VerticalChart(listaMensual);
			HorizontalLayout hlGraficos = new HorizontalLayout();
			hlGraficos.setSizeFull();
			hlGraficos.add(areaChart,donutChart,verticalChart);
			reportesView.add(hlGraficos);
		}

	}

	private void setGridPago(Grid<Pago> pagoGrid) {

		pagoGrid.setHeight("200px");
		pagoGrid.setColumns("id","fechaDePago", "formaDePago", "transaccion.sucursal.descripcion", "transaccion.vendedor.user","transaccion.cliente.id", "transaccion.cliente", "transaccion.viaje.destino.ciudad.nombre", "importe");
		pagoGrid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);
		pagoGrid.getColumnByKey("transaccion.cliente.id").setWidth("90px").setFlexGrow(0);
		pagoGrid.getColumnByKey("transaccion.sucursal.descripcion").setHeader("Sucursal");
		pagoGrid.getColumnByKey("transaccion.vendedor.user").setHeader("Vendedor");
		pagoGrid.getColumnByKey("transaccion.cliente.id").setHeader("ID Cliente");
		pagoGrid.getColumnByKey("transaccion.cliente").setHeader("Cliente");
		pagoGrid.getColumnByKey("transaccion.viaje.destino.ciudad.nombre").setHeader("Destino");
	}

	private void setFiltroDinamico() {

		if(reportesView.getComboTipoReporte().getValue().equals(TipoReporte.VENDEDOR.name())){
			ComboBox<User> comboVendedor = new ComboBox<>("Vendedor");
			comboVendedor.setItemLabelGenerator(User::getUser);
			comboVendedor.setItems(userService.findAll());
			reportesView.setFiltroDinamico(comboVendedor);
			reportesView.setCampos();
		}
		if(reportesView.getComboTipoReporte().getValue().equals(TipoReporte.CLIENTE.name())){
			BuscadorClientesComponent buscadorClientesComponent = new BuscadorClientesComponent(new Label());
			buscadorClientesComponent.getFiltro().setLabel("Cliente");
			buscadorClientesComponent.getSearchButton().addThemeVariants(ButtonVariant.LUMO_SMALL);
			reportesView.setFiltroDinamico(buscadorClientesComponent);
			reportesView.setCampos();
		}

	}

	private void setComponents() {
	}

	public ReportesView getReportesView() {
		return reportesView;
	}

	public void setReportesView(ReportesView reportesView) {
		this.reportesView = reportesView;
	}
}
