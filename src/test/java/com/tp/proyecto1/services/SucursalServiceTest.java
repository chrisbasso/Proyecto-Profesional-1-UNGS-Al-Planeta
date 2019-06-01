package com.tp.proyecto1.services;

import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.repository.sucursales.SucursalRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SucursalServiceTest {

	@Mock
	private SucursalRepository mockSucursalRepository;

	@InjectMocks
	private SucursalService sucursalServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Sucursal sucursal = null;
		when(mockSucursalRepository.save(null)).thenReturn(null);

		// Run the test
		sucursalServiceUnderTest.save(sucursal);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Sucursal> expectedResult = Arrays.asList();
		when(mockSucursalRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Sucursal> result = sucursalServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
