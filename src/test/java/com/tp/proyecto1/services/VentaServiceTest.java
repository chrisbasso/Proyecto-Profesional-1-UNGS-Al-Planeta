package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.PasajeVenta;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;
import com.tp.proyecto1.repository.pasajes.PasajeVentaRepository;
import com.tp.proyecto1.repository.pasajes.VentaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class VentaServiceTest {

	@Mock
	private PasajeVentaRepository mockPasajeVentaRepository;
	@Mock
	private FormaDePagoRepository mockFormaDePagoRepository;
	@Mock
	private VentaRepository mockVentaRepository;

	@InjectMocks
	private VentaService ventaServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final PasajeVenta pasajeVenta = null;
		when(mockPasajeVentaRepository.save(null)).thenReturn(null);

		// Run the test
		ventaServiceUnderTest.save(pasajeVenta);

		// Verify the results
	}

	@Test
	public void testSave1() {
		// Setup
		final Venta venta = null;
		when(mockVentaRepository.save(null)).thenReturn(null);

		// Run the test
		ventaServiceUnderTest.save(venta);

		// Verify the results
	}

	@Test
	public void testFindAllPasajeVentas() {
		// Setup
		final List<PasajeVenta> expectedResult = Arrays.asList();
		when(mockPasajeVentaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<PasajeVenta> result = ventaServiceUnderTest.findAllPasajeVentas();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindPasajesVentas() {
		// Setup
		final PasajeVenta pasajeVenta = new PasajeVenta();
		final List<PasajeVenta> expectedResult = Arrays.asList();
		when(mockPasajeVentaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<PasajeVenta> result = ventaServiceUnderTest.findPasajesVentas(pasajeVenta);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllVentas() {
		// Setup
		final List<Venta> expectedResult = Arrays.asList();
		when(mockVentaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Venta> result = ventaServiceUnderTest.findAllVentas();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindVentas() {
		// Setup
		final Venta venta = new Venta();
		final List<Venta> expectedResult = Arrays.asList();
		when(mockVentaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Venta> result = ventaServiceUnderTest.findVentas(venta);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final PasajeVenta pasajeVenta = null;

		// Run the test
		ventaServiceUnderTest.delete(pasajeVenta);

		// Verify the results
		verify(mockPasajeVentaRepository).delete(null);
	}

	@Test
	public void testCreateFormaDePagoIfNotExist() {
		// Setup
		final String forma = "forma";
		final FormaDePago expectedResult = null;
		when(mockFormaDePagoRepository.findByDescripcion("descripcion")).thenReturn(null);
		when(mockFormaDePagoRepository.save(null)).thenReturn(null);

		// Run the test
		final FormaDePago result = ventaServiceUnderTest.createFormaDePagoIfNotExist(forma);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllFomaDePagos() {
		// Setup
		final List<FormaDePago> expectedResult = Arrays.asList();
		when(mockFormaDePagoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<FormaDePago> result = ventaServiceUnderTest.findAllFomaDePagos();

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
