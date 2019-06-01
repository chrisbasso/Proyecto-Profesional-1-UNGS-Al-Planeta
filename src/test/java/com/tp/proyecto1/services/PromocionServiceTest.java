package com.tp.proyecto1.services;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.viajes.PromocionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PromocionServiceTest {

	@Mock
	private PromocionRepository mockPromocionRepository;

	@InjectMocks
	private PromocionService promocionServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Promocion promocion = null;
		when(mockPromocionRepository.save(null)).thenReturn(null);

		// Run the test
		promocionServiceUnderTest.save(promocion);

		// Verify the results
	}

	@Test
	public void testDelete() {
		// Setup
		final Promocion promocion = null;

		// Run the test
		promocionServiceUnderTest.delete(promocion);

		// Verify the results
		verify(mockPromocionRepository).delete(null);
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Promocion> expectedResult = Arrays.asList();
		when(mockPromocionRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Promocion> result = promocionServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindByViajesAfectados() {
		// Setup
		final Viaje viaje = null;
		final Set<Promocion> expectedResult = new HashSet<>();
		when(mockPromocionRepository.findByViajesAfectados(null)).thenReturn(new HashSet<>());

		// Run the test
		final Set<Promocion> result = promocionServiceUnderTest.findByViajesAfectados(viaje);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindByCiudadesAfectadas() {
		// Setup
		final Ciudad ciudad = null;
		final Set<Promocion> expectedResult = new HashSet<>();
		when(mockPromocionRepository.findByCiudadesAfectadas(null)).thenReturn(new HashSet<>());

		// Run the test
		final Set<Promocion> result = promocionServiceUnderTest.findByCiudadesAfectadas(ciudad);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindPromociones() {
		// Setup
		final Promocion promocionBusqueda = null;
		final LocalDate vencimientoMayorA = LocalDate.of(2017, 1, 1);
		final Collection<Promocion> expectedResult = Arrays.asList();
		when(mockPromocionRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final Collection<Promocion> result = promocionServiceUnderTest.findPromociones(promocionBusqueda, vencimientoMayorA);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
