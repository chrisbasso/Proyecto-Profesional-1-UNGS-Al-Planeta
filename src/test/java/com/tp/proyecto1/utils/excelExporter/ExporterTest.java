package com.tp.proyecto1.utils.excelExporter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.InputStreamFactory;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExporterTest {

	@Test
	public void testExportAsExcel() {
		// Setup
		final Grid<T> grid = null;
		final InputStreamFactory expectedResult = null;

		// Run the test
		final InputStreamFactory result = Exporter.exportAsExcel(grid);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
