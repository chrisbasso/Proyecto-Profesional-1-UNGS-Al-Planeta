package com.tp.proyecto1.views.reportes;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class AreaChart extends Div {


	public AreaChart(List<Double> datos, String tipo) {

		Series series = new Series();
		series.setName("INGRESOS");
		series.setData(datos.toArray());


		LocalDate fecha = LocalDate.now();

		ApexCharts areaChart;

		if(tipo.equals("anual")){
			areaChart = new ApexCharts()
					.withChart(
							ChartBuilder.get()
									.withType(Type.area)
									.withZoom(ZoomBuilder.get()
											.withEnabled(false)
											.build())
									.build())
					.withDataLabels(DataLabelsBuilder.get()
							.withEnabled(false)
							.build())
					.withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
					.withSeries(series)
					.withTitle(TitleSubtitleBuilder.get()
							.withText("Informe de Ingresos Anual")
							.withAlign(Align.left).build())
					.withSubtitle(TitleSubtitleBuilder.get()
							.withText("Importes")
							.withAlign(Align.left).build())
					.withLabels(IntStream.range(1, 13).boxed().map(value -> LocalDate.of(fecha.getYear(), value, 1).toString()).toArray(String[]::new))
					.withXaxis(XAxisBuilder.get()
							.withType(XAxisType.datetime).build())
					.withYaxis(YAxisBuilder.get()
							.withOpposite(true).build())
					.withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build());
		}else{
			areaChart = new ApexCharts()
					.withChart(
							ChartBuilder.get()
									.withType(Type.area)
									.withZoom(ZoomBuilder.get()
											.withEnabled(false)
											.build())
									.build())
					.withDataLabels(DataLabelsBuilder.get()
							.withEnabled(false)
							.build())
					.withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
					.withSeries(series)
					.withTitle(TitleSubtitleBuilder.get()
							.withText("Informe de Ingresos Mensual")
							.withAlign(Align.left).build())
					.withSubtitle(TitleSubtitleBuilder.get()
							.withText("Importes")
							.withAlign(Align.left).build())
					.withLabels(IntStream.range(1, 31).boxed().map(day -> LocalDate.of(fecha.getYear(), fecha.getMonth(), day).toString()).toArray(String[]::new))
					.withXaxis(XAxisBuilder.get()
							.withType(XAxisType.datetime).build())
					.withYaxis(YAxisBuilder.get()
							.withOpposite(true).build())
					.withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build());
		}


		add(areaChart);
		setWidth("45%");
		setHeight("45%");

	}
}

