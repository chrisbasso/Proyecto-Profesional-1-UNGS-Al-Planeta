package com.tp.proyecto1.services;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.tp.proyecto1.repository.configuraciones.ConfiguracionRepository;
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

public class ConfiguracionServiceTest {

	@Mock
	private ConfiguracionRepository mockConfiguracionRepository;

	@InjectMocks
	private ConfiguracionService configuracionServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Configuracion config = null;
		when(mockConfiguracionRepository.save(null)).thenReturn(null);

		// Run the test
		configuracionServiceUnderTest.save(config);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Configuracion> expectedResult = Arrays.asList();
		when(mockConfiguracionRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Configuracion> result = configuracionServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindConfiguraciones() {
		// Setup
		final Configuracion config = new Configuracion();
		final List<Configuracion> expectedResult = Arrays.asList();
		when(mockConfiguracionRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Configuracion> result = configuracionServiceUnderTest.findConfiguraciones(config);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final Configuracion config = null;

		// Run the test
		configuracionServiceUnderTest.delete(config);

		// Verify the results
		verify(mockConfiguracionRepository).delete(null);
	}

	@Test
	public void testFindById() {
		// Setup
		final Long id = 0L;
		final Optional<Configuracion> expectedResult = null;
		when(mockConfiguracionRepository.findById(null)).thenReturn(null);

		// Run the test
		final Optional<Configuracion> result = configuracionServiceUnderTest.findById(id);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindValueByKey() {
		// Setup
		final String key = "key";
		final String expectedResult = "result";
		when(mockConfiguracionRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final String result = configuracionServiceUnderTest.findValueByKey(key);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testCreateConfiguracionIfNotExist() {
		// Setup
		final String key = "key";
		final String value = "value";
		final Configuracion expectedResult = null;
		when(mockConfiguracionRepository.findAll()).thenReturn(Arrays.asList());
		when(mockConfiguracionRepository.save(null)).thenReturn(null);

		// Run the test
		final Configuracion result = configuracionServiceUnderTest.createConfiguracionIfNotExist(key, value);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
