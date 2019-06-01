package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClienteServiceTest {

	@Mock
	private ClienteRepository mockClienteRepository;

	@InjectMocks
	private ClienteService clienteServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Cliente cliente = null;
		when(mockClienteRepository.save(null)).thenReturn(null);

		// Run the test
		clienteServiceUnderTest.save(cliente);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Cliente> expectedResult = Arrays.asList();
		when(mockClienteRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Cliente> result = clienteServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindClientes() {
		// Setup
		final Cliente cliente = new Cliente();
		final List<Cliente> expectedResult = Arrays.asList();
		when(mockClienteRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Cliente> result = clienteServiceUnderTest.findClientes(cliente);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final Cliente cliente = null;

		// Run the test
		clienteServiceUnderTest.delete(cliente);

		// Verify the results
		verify(mockClienteRepository).delete(null);
	}

	@Test
	public void testFindById() {
		// Setup
		final Long id = 0L;
		final Optional<Cliente> expectedResult = null;
		when(mockClienteRepository.findById(id)).thenReturn(null);

		// Run the test
		final Optional<Cliente> result = clienteServiceUnderTest.findById(id);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
