package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.repository.pasajes.PagoRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PagosServiceTest {

	@Mock
	private PagoRepository mockPagoRepository;

	@InjectMocks
	private PagosService pagosServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testFindPagos() {
		// Setup
		final Pago pago = new Pago();
		final LocalDate fechaDesde = LocalDate.of(2017, 1, 1);
		final LocalDate fechaHasta = LocalDate.of(2017, 1, 1);
		final List<Pago> expectedResult = Arrays.asList();
		when(mockPagoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Pago> result = pagosServiceUnderTest.findPagos(pago, fechaDesde, fechaHasta);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllPago() {
		// Setup
		final List<Pago> expectedResult = Arrays.asList();
		when(mockPagoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Pago> result = pagosServiceUnderTest.findAllPago();

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
