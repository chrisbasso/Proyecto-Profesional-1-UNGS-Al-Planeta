package com.tp.proyecto1.utils.excelExporter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.InputStreamFactory;

public class Exporter {

	private Exporter(){}

	public static <T> InputStreamFactory exportAsExcel(Grid<T> grid){
		return new ExcelFileBuilder<>(grid)::build;
	}

}
