package com.tp.proyecto1.services;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.repository.contabilidad.AsientoRepository;
import com.tp.proyecto1.repository.contabilidad.CabeceraRepository;
import com.tp.proyecto1.repository.contabilidad.CuentaRepository;
import com.tp.proyecto1.repository.contabilidad.PosicionRepository;
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

public class AsientoServiceTest {

	@Mock
	private AsientoRepository mockAsientoRepository;
	@Mock
	private CuentaRepository mockCuentaRepository;
	@Mock
	private CabeceraRepository mockCabeceraRepository;
	@Mock
	private PosicionRepository mockPosicionRepository;

	@InjectMocks
	private AsientoService asientoServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Asiento asiento = null;
		final Cabecera cabecera = null;
		final List<Posicion> posiciones = Arrays.asList();
		when(mockCabeceraRepository.save(null)).thenReturn(null);
		when(mockPosicionRepository.saveAll(null)).thenReturn(Arrays.asList());
		when(mockAsientoRepository.save(null)).thenReturn(null);

		// Run the test
		asientoServiceUnderTest.save(asiento, cabecera, posiciones);

		// Verify the results
	}

	@Test
	public void testSave1() {
		// Setup
		final Asiento asiento = null;
		when(mockAsientoRepository.save(null)).thenReturn(null);

		// Run the test
		asientoServiceUnderTest.save(asiento);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Asiento> expectedResult = Arrays.asList();
		when(mockAsientoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Asiento> result = asientoServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllCuentas() {
		// Setup
		final List<Cuenta> expectedResult = Arrays.asList();
		when(mockCuentaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Cuenta> result = asientoServiceUnderTest.findAllCuentas();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAsientos() {
		// Setup
		final Asiento asiento = new Asiento();
		final List<Asiento> expectedResult = Arrays.asList();
		when(mockAsientoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Asiento> result = asientoServiceUnderTest.findAsientos(asiento);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAsientoId() {
		// Setup
		final Asiento asiento = new Asiento();
		final Long expectedResult = 0L;
		when(mockAsientoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final Long result = asientoServiceUnderTest.findAsientoId(asiento);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final Asiento asiento = null;

		// Run the test
		asientoServiceUnderTest.delete(asiento);

		// Verify the results
		verify(mockAsientoRepository).delete(null);
	}

	@Test
	public void testFindById() {
		// Setup
		final Long id = 0L;
		final Optional<Asiento> expectedResult = null;
		when(mockAsientoRepository.findById(id)).thenReturn(null);

		// Run the test
		final Optional<Asiento> result = asientoServiceUnderTest.findById(id);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindCuentas() {
		// Setup
		final List<Cuenta> expectedResult = Arrays.asList();
		when(mockCuentaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Cuenta> result = asientoServiceUnderTest.findCuentas();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testSaveCuenta() {
		// Setup
		final Cuenta cuenta = null;
		when(mockCuentaRepository.save(null)).thenReturn(null);

		// Run the test
		asientoServiceUnderTest.saveCuenta(cuenta);

		// Verify the results
	}
}
