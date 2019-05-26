package com.tp.proyecto1.views.reportes;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.tooltip.builder.YBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;

import java.time.LocalDate;
import java.util.List;

public class VerticalChart extends Div {

	public VerticalChart(List<Double> datos) {

		Series series = new Series();
		series.setName("INGRESOS");
		series.setData(datos.toArray());

		ApexCharts barChart = new ApexCharts()
				.withChart(ChartBuilder.get()
						.withType(Type.bar)
						.build())
				.withPlotOptions(PlotOptionsBuilder.get()
						.withBar(BarBuilder.get()
								.withHorizontal(false)
								.withColumnWidth("55%")
								.build())
						.build())
				.withDataLabels(DataLabelsBuilder.get()
						.withEnabled(false).build())
				.withStroke(StrokeBuilder.get()
						.withShow(true)
						.withWidth(2.0)
						.withColors("transparent")
						.build())
				.withSeries(series)
				.withYaxis(YAxisBuilder.get()
						.withTitle(TitleBuilder.get()
								.withText("$ (thousands)")
								.build())
						.build())
				.withXaxis(XAxisBuilder.get().withCategories("Ene","Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct","Nov", "Dic").build())
				.withFill(FillBuilder.get()
						.withOpacity(1.0).build())
				.withTooltip(TooltipBuilder.get()
						.withY(YBuilder.get()
								.withFormatter("function (val) {\n" + // Formatter currently not yet working
										"return \"$ \" + val + \" thousands\"\n" +
										"}").build())
						.build());
		add(barChart);
		setWidth("50%");
		setHeight("50%");
	}
}

