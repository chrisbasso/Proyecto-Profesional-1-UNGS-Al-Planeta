package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.repository.lotePuntos.LotePuntoRepository;
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

public class LotePuntoServiceTest {

	@Mock
	private LotePuntoRepository mockLotePuntoRepository;

	@InjectMocks
	private LotePuntoService lotePuntoServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final LotePunto puntos = null;
		when(mockLotePuntoRepository.save(null)).thenReturn(null);

		// Run the test
		lotePuntoServiceUnderTest.save(puntos);

		// Verify the results
	}

	@Test
	public void testDelete() {
		// Setup
		final LotePunto puntos = null;

		// Run the test
		lotePuntoServiceUnderTest.delete(puntos);

		// Verify the results
		verify(mockLotePuntoRepository).delete(null);
	}

	@Test
	public void testFindAllByCliente() {
		// Setup
		final Cliente cliente = null;
		final List<LotePunto> expectedResult = Arrays.asList();
		when(mockLotePuntoRepository.findAllByCliente(null)).thenReturn(Arrays.asList());

		// Run the test
		final List<LotePunto> result = lotePuntoServiceUnderTest.findAllByCliente(cliente);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
