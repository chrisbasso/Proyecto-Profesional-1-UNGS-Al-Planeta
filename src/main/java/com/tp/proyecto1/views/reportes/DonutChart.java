package com.tp.proyecto1.views.reportes;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.vaadin.flow.component.html.Div;

import java.util.List;

public class DonutChart extends Div {

	public DonutChart(List<Double> datos, String mensual) {
		Double[] target = new Double[datos.size()];
		for (int i = 0; i < target.length; i++) {
			target[i] = datos.get(i);
		}


		ApexCharts donutChart = new ApexCharts()
				.withChart(ChartBuilder.get().withType(Type.donut).build())
				.withLegend(LegendBuilder.get()
						.withPosition(Position.right)
						.build())
				.withSeries(target)
				.withResponsive(ResponsiveBuilder.get()
						.withBreakpoint(480.0)
						.withOptions(OptionsBuilder.get()
								.withLegend(LegendBuilder.get()
										.withPosition(Position.bottom)
										.build())
								.build())
						.build());
		add(donutChart);
		setWidth("30%");
		setHeight("30%");
//		donutChart.withColors("#800000","#808000","#008000","#008080","#800080","#00FFFF", "#FFFF00", "#00FF00", "#e6194B", "#bfef45", "#fabebe", "#469990");
	}
}

