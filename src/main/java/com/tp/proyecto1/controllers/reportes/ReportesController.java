package com.tp.proyecto1.controllers.reportes;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.contabilidad.Egreso;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Transaccion;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.services.*;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.utils.TipoReporte;
import com.tp.proyecto1.utils.excelExporter.Exporter;
import com.tp.proyecto1.views.reportes.AreaChart;
import com.tp.proyecto1.views.reportes.DonutChart;
import com.tp.proyecto1.views.reportes.ReportesView;
import com.tp.proyecto1.views.reportes.VerticalChart;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
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
	private UserService userService;

	@Autowired
	private PagosService pagosService;

	@Autowired
	private ViajeService viajeService;

	@Autowired
	private SucursalService sucursalService;
	
	@Autowired
	private AsientoService asientoService;

	public ReportesController() {
		Inject.Inject(this);
		this.reportesView = new ReportesView();
		setListeners();
		setComponents();
	}

	private void setListeners() {

		reportesView.getComboTipoReporte().addValueChangeListener(e->setFiltroDinamico());
		reportesView.getBtnGenerar().addClickListener(e-> generarReporte());
		reportesView.getComboTipoGrafico().addValueChangeListener(e->deshabilitarComboMeses());

	}

	private void deshabilitarComboMeses() {

		if(reportesView.getComboTipoGrafico().getValue().equals("Anual")){
			reportesView.getComboBoxMeses().setEnabled(false);
		}else{
			reportesView.getComboBoxMeses().setEnabled(true);
		}
	}

	private void generarReporte() {

		LocalDate fechaDesde = reportesView.getFechaDesde().getValue();
		LocalDate fechaHasta = reportesView.getFechaHasta().getValue();
		String tipoReporte = reportesView.getComboTipoReporte().getValue();

		if(tipoReporte.equals(TipoReporte.VENDEDOR.name())){
			generarReportePorVendedor(fechaDesde, fechaHasta);
		}
		if(tipoReporte.equals(TipoReporte.CLIENTE.name())){
			generarReportePorCliente(fechaDesde, fechaHasta);
		}
		if(tipoReporte.equals(TipoReporte.DESTINO.name())){
			generarReportePorDestinos(fechaDesde, fechaHasta);
		}
		if(tipoReporte.equals(TipoReporte.LOCAL.name())){
			generarReportePorLocal(fechaDesde, fechaHasta);
		}
		if(tipoReporte.equals(TipoReporte.EGRESOS.name())){
			generarReportePorEgresos(fechaDesde, fechaHasta);
		}
	}

	private void generarReportePorEgresos(LocalDate fechaDesde, LocalDate fechaHasta) {
		Sucursal sucursal = ((ComboBox<Sucursal>)reportesView.getFiltroDinamico()).getValue();
		setReporteEgresos(fechaDesde, fechaHasta, sucursal);
	}

	private void generarReportePorLocal(LocalDate fechaDesde, LocalDate fechaHasta) {
		Sucursal sucursal = ((ComboBox<Sucursal>)reportesView.getFiltroDinamico()).getValue();
		setReporteIngresos(fechaDesde, fechaHasta, sucursal);
	}

	private void generarReportePorDestinos(LocalDate fechaDesde, LocalDate fechaHasta) {

		Ciudad ciudad = ((ComboBox<Ciudad>)reportesView.getFiltroDinamico()).getValue();
		setReporteIngresos(fechaDesde, fechaHasta, ciudad);

	}

	private void generarReportePorVendedor(LocalDate fechaDesde, LocalDate fechaHasta) {
		User vendedor = ((ComboBox<User>)reportesView.getFiltroDinamico()).getValue();
		setReporteIngresos(fechaDesde, fechaHasta, vendedor);
	}

	private void generarReportePorCliente(LocalDate fechaDesde, LocalDate fechaHasta) {
		Cliente cliente = ((BuscadorClientesComponent)reportesView.getFiltroDinamico()).getCliente();
		setReporteIngresos(fechaDesde, fechaHasta, cliente);
	}

	private void setReporteEgresos(LocalDate fechaDesde, LocalDate fechaHasta, Sucursal sucursal) {

		String nombreArchivo;
		Grid<Egreso> egresoGrid = new Grid<>(Egreso.class);
		setGridEgreso(egresoGrid);
		reportesView.setGridDinamico(egresoGrid);

		List<Egreso> egresos = asientoService.findEgresos(fechaDesde, fechaHasta);
		egresos = egresos.stream().filter(e-> e.getIdSucursal().equals(sucursal.getId())).collect(Collectors.toList());

		nombreArchivo = "Reporte Egresos " +  sucursal.getDescripcion() +".xls";

		double totalImporte = 0;
		for (Egreso egreso : egresos) {
			totalImporte += egreso.getPosicion().getImporte();
		}
		egresoGrid.setItems(egresos);
		egresoGrid.getColumnByKey("posicion.importe").setFooter("Total: $ " + totalImporte);
		reportesView.setLayout();
		reportesView.getHlCampos().add(new Anchor(new StreamResource(nombreArchivo, Exporter.exportAsExcel(egresoGrid)), "Exportar"));

		if(reportesView.getComboTipoGrafico().getValue().equals("Anual")){
			List<Double> listaMensual = new ArrayList<>(12);
			for (int i = 0; i < 12; i++) {
				listaMensual.add(0.0);
			}
			for (Egreso egreso : egresos) {
				int indiceMes = egreso.getCabecera().getFechaContabilizacion().getMonthValue()-1;
				listaMensual.set(indiceMes,listaMensual.get(indiceMes) + egreso.getPosicion().getImporte());
			}
			setGraficosAnual(listaMensual);
		}else{
			List<Egreso> egresosMesElegido = egresos.stream().filter(e-> e.getCabecera().getFechaContabilizacion().getMonthValue()==reportesView.getComboBoxMeses().getValue()).collect(Collectors.toList());
			List<Double> listaDias = new ArrayList<>(31);
			for (int i = 0; i < 31; i++) {
				listaDias.add(0.0);
			}
			for (Egreso egreso : egresosMesElegido) {
				int indiceDia = egreso.getCabecera().getFechaContabilizacion().getDayOfMonth();
				listaDias.set(indiceDia,listaDias.get(indiceDia) + egreso.getPosicion().getImporte());
			}
			setGraficosMensual(listaDias);
		}
		
	}

	private void setGraficosMensual(List<Double> listaDias) {
		AreaChart areaChart = new AreaChart(listaDias, "mensual");
		DonutChart donutChart = new DonutChart(listaDias, "mensual");
		VerticalChart verticalChart = new VerticalChart(listaDias, "mensual");
		HorizontalLayout hlGraficos = new HorizontalLayout();
		hlGraficos.setSizeFull();
		hlGraficos.add(areaChart,donutChart,verticalChart);
		reportesView.getHlGraficos().removeAll();
		reportesView.getHlGraficos().add(hlGraficos);
	}

	private void setGraficosAnual(List<Double> listaMensual) {
		AreaChart areaChart = new AreaChart(listaMensual, "anual");
		DonutChart donutChart = new DonutChart(listaMensual, "anual");
		VerticalChart verticalChart = new VerticalChart(listaMensual, "anual");
		HorizontalLayout hlGraficos = new HorizontalLayout();
		hlGraficos.setSizeFull();
		hlGraficos.add(areaChart,donutChart,verticalChart);
		reportesView.getHlGraficos().removeAll();
		reportesView.getHlGraficos().add(hlGraficos);
	}

	private void setGridEgreso(Grid<Egreso> egresoGrid) {

		egresoGrid.setHeight("200px");
		egresoGrid.setColumns("idAsiento", "cabecera.fechaContabilizacion","cabecera.textoCabecera","cabecera.sucursal.descripcion","posicion.cuenta.descripcion", "posicion.importe");
		egresoGrid.getColumnByKey("idAsiento").setWidth("90px").setFlexGrow(0);
		egresoGrid.getColumnByKey("cabecera.sucursal.descripcion").setHeader("Sucursal");
		egresoGrid.getColumnByKey("posicion.cuenta.descripcion").setHeader("Cuenta");

	}

	private void setReporteIngresos(LocalDate fechaDesde, LocalDate fechaHasta, Object tipo) {
		String nombreArchivo = "";
		Grid<Pago> pagoGrid = new Grid<>(Pago.class);
		setGridPago(pagoGrid);
		reportesView.setGridDinamico(pagoGrid);

		Transaccion transaccionExample = new Transaccion();
		if(tipo instanceof Cliente){
			transaccionExample.setCliente((Cliente)tipo);
			nombreArchivo = "Reporte " + (((Cliente) tipo).getNombreyApellido()) +".xls";
		}
		if(tipo instanceof User){
			transaccionExample.setVendedor((User)tipo);
			nombreArchivo = "Reporte " + (((User) tipo).getUser()) +".xls";
		}
		if(tipo instanceof Ciudad){
			transaccionExample.setViaje(new Viaje());
			transaccionExample.getViaje().setDestino((Ciudad)tipo);
			nombreArchivo = "Reporte " + (((Ciudad) tipo).toString()) +".xls";
		}
		if(tipo instanceof Sucursal){
			transaccionExample.setSucursal((Sucursal)tipo);
			nombreArchivo = "Reporte " + (((Sucursal) tipo).getDescripcion()) +".xls";
		}
		Pago pagoExample = new Pago();
		pagoExample.setTransaccion(transaccionExample);
		List<Pago> pagos = pagosService.findPagos(pagoExample, fechaDesde, fechaHasta);
		double totalImporte = 0;
		for (Pago pago : pagos) {
			totalImporte += pago.getImporte();
		}
		pagoGrid.setItems(pagos);
		pagoGrid.getColumnByKey("importe").setFooter("Total: $ " + totalImporte);
		reportesView.setLayout();
		reportesView.getHlCampos().add(new Anchor(new StreamResource(nombreArchivo, Exporter.exportAsExcel(pagoGrid)), "Exportar"));

		if(reportesView.getComboTipoGrafico().getValue().equals("Anual")){
			List<Double> listaMensual = new ArrayList<>(12);
			for (int i = 0; i < 12; i++) {
				listaMensual.add(0.0);
			}
			for (Pago pago : pagos) {
				int indiceMes = pago.getFechaDePago().getMonthValue()-1;
				listaMensual.set(indiceMes,listaMensual.get(indiceMes) + pago.getImporte());
			}
			setGraficosAnual(listaMensual);
		}else{
			List<Pago> pagosMesElegido = pagos.stream().filter(e-> e.getFechaDePago().getMonthValue()==reportesView.getComboBoxMeses().getValue()).collect(Collectors.toList());
			List<Double> listaDias = new ArrayList<>(31);
			for (int i = 0; i < 31; i++) {
				listaDias.add(0.0);
			}
			for (Pago pago : pagosMesElegido) {
				int indiceDia = pago.getFechaDePago().getDayOfMonth();
				listaDias.set(indiceDia,listaDias.get(indiceDia) + pago.getImporte());
			}
			setGraficosMensual(listaDias);
		}

	}

	private void setGridPago(Grid<Pago> pagoGrid) {

		pagoGrid.setHeight("200px");
		pagoGrid.setColumns("id","fechaDePago", "formaDePago", "transaccion.sucursal.descripcion", "transaccion.vendedor.user","transaccion.cliente.id", "transaccion.cliente", "transaccion.viaje.destino.nombre", "importe");
		pagoGrid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);
		pagoGrid.getColumnByKey("transaccion.cliente.id").setWidth("90px").setFlexGrow(0);
		pagoGrid.getColumnByKey("transaccion.sucursal.descripcion").setHeader("Sucursal");
		pagoGrid.getColumnByKey("transaccion.vendedor.user").setHeader("Vendedor");
		pagoGrid.getColumnByKey("transaccion.cliente.id").setHeader("ID Cliente");
		pagoGrid.getColumnByKey("transaccion.cliente").setHeader("Cliente");
		pagoGrid.getColumnByKey("transaccion.viaje.destino.nombre").setHeader("Destino");

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
		if(reportesView.getComboTipoReporte().getValue().equals(TipoReporte.DESTINO.name())){
			ComboBox<Ciudad> comboDestinos = new ComboBox<>("Destinos");
			comboDestinos.setItemLabelGenerator(Ciudad::toString);
			comboDestinos.setItems(viajeService.findAllCiudades());
			reportesView.setFiltroDinamico(comboDestinos);
			reportesView.setCampos();
		}
		if(reportesView.getComboTipoReporte().getValue().equals(TipoReporte.LOCAL.name()) ||
				reportesView.getComboTipoReporte().getValue().equals(TipoReporte.EGRESOS.name())){
			ComboBox<Sucursal> comboSucursales = new ComboBox<>("Sucursal");
			comboSucursales.setItemLabelGenerator(Sucursal::getDescripcion);
			comboSucursales.setItems(sucursalService.findAll());
			reportesView.setFiltroDinamico(comboSucursales);
			reportesView.setCampos();
		}


	}

	public void setComponents() {

		reportesView.getComboBoxMeses().setEnabled(false);

		if(Proyecto1Application.logUser!=null){
			if(Proyecto1Application.logUser.getRol().getName().equals("SUPERVISOR")){
				reportesView.getComboTipoReporte().setValue(TipoReporte.VENDEDOR.name());
				reportesView.getComboTipoReporte().setReadOnly(true);
			}
		}
	}

	public ReportesView getReportesView() {
		return reportesView;
	}

	public void setReportesView(ReportesView reportesView) {
		this.reportesView = reportesView;
	}
}
