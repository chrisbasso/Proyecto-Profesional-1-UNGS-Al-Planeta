package com.tp.proyecto1.services;

import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.repository.viajes.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ViajeServiceTest {

	@Mock
	private ViajeRepository mockViajeRepository;
	@Mock
	private TipoTransporteRepository mockTipoTransporteRepository;
	@Mock
	private TagDestinoRepository mockTagDestinoRepository;
	@Mock
	private PaisRepository mockPaisRepository;
	@Mock
	private CiudadRepository mockCiudadRepository;

	@InjectMocks
	private ViajeService viajeServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Viaje viaje = null;
		when(mockViajeRepository.save(null)).thenReturn(null);

		// Run the test
		viajeServiceUnderTest.save(viaje);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Viaje> expectedResult = Arrays.asList();
		when(mockViajeRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Viaje> result = viajeServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindViajes() {
		// Setup
		final Viaje viaje = new Viaje();
		final LocalDate fechaDesde = LocalDate.of(2017, 1, 1);
		final LocalDate fechaHasta = LocalDate.of(2017, 1, 1);
		final List<Viaje> expectedResult = Arrays.asList();
		when(mockViajeRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Viaje> result = viajeServiceUnderTest.findViajes(viaje, fechaDesde, fechaHasta);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final Viaje viaje = null;

		// Run the test
		viajeServiceUnderTest.delete(viaje);

		// Verify the results
		verify(mockViajeRepository).delete(null);
	}

	@Test
	public void testCreateTipoTransporteIfNotExist() {
		// Setup
		final String tipo = "tipo";
		final TipoTransporte expectedResult = null;
		when(mockTipoTransporteRepository.findByDescripcion("descripcion")).thenReturn(null);
		when(mockTipoTransporteRepository.save(null)).thenReturn(null);

		// Run the test
		final TipoTransporte result = viajeServiceUnderTest.createTipoTransporteIfNotExist(tipo);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllTipoTransportes() {
		// Setup
		final List<TipoTransporte> expectedResult = Arrays.asList();
		when(mockTipoTransporteRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<TipoTransporte> result = viajeServiceUnderTest.findAllTipoTransportes();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllPaises() {
		// Setup
		final List<Pais> expectedResult = Arrays.asList();
		when(mockPaisRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Pais> result = viajeServiceUnderTest.findAllPaises();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindAllCiudades() {
		// Setup
		final List<Ciudad> expectedResult = Arrays.asList();
		when(mockCiudadRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Ciudad> result = viajeServiceUnderTest.findAllCiudades();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testSaveTagDestino() {
		// Setup
		final TagDestino tag = null;
		when(mockTagDestinoRepository.save(null)).thenReturn(null);

		// Run the test
		viajeServiceUnderTest.saveTagDestino(tag);

		// Verify the results
	}

	@Test
	public void testSavePais() {
		// Setup
		final Pais pais = null;
		when(mockPaisRepository.save(null)).thenReturn(null);

		// Run the test
		viajeServiceUnderTest.savePais(pais);

		// Verify the results
	}

	@Test
	public void testSaveCiudad() {
		// Setup
		final Ciudad ciudad = null;
		when(mockCiudadRepository.save(null)).thenReturn(null);

		// Run the test
		viajeServiceUnderTest.saveCiudad(ciudad);

		// Verify the results
	}
}
