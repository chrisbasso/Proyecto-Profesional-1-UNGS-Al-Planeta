package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Transaccion;
import com.tp.proyecto1.repository.pasajes.TransaccionRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransaccionServiceTest {

	private TransaccionService transaccionServiceUnderTest;

	@Before
	public void setUp() {
		transaccionServiceUnderTest = new TransaccionService();
		transaccionServiceUnderTest.transaccionRepository = mock(TransaccionRepository.class);
	}

	@Test
	public void testVerificarTransaccionCliente() {
		// Setup
		final Cliente cliente = new Cliente();
		when(transaccionServiceUnderTest.transaccionRepository.findAllByCliente(cliente)).thenReturn(Arrays.asList());

		// Run the test
		final boolean result = transaccionServiceUnderTest.verificarTransaccionCliente(cliente);

		// Verify the results
		assertTrue(result);
	}

	@Test
	public void testFindAllTransacciones() {
		// Setup
		final Transaccion transaccion = new Transaccion();
		final List<Transaccion> expectedResult = Arrays.asList();
		when(transaccionServiceUnderTest.transaccionRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Transaccion> result = transaccionServiceUnderTest.findAllTransacciones(transaccion);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
