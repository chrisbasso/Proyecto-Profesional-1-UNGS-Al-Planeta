package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;
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

public class ReservaServiceTest {

	@Mock
	private ReservaRepository mockReservaRepository;

	@InjectMocks
	private ReservaService reservaServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testSave() {
		// Setup
		final Reserva reserva = null;
		when(mockReservaRepository.save(null)).thenReturn(null);

		// Run the test
		reservaServiceUnderTest.save(reserva);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Reserva> expectedResult = Arrays.asList();
		when(mockReservaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Reserva> result = reservaServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindReservas() {
		// Setup
		final Reserva reserva = new Reserva();
		final List<Reserva> expectedResult = Arrays.asList();
		when(mockReservaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Reserva> result = reservaServiceUnderTest.findReservas(reserva);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindReservaId() {
		// Setup
		final Reserva reserva = new Reserva();
		final Long expectedResult = 0L;
		when(mockReservaRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final Long result = reservaServiceUnderTest.findReservaId(reserva);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final Reserva reserva = null;

		// Run the test
		reservaServiceUnderTest.delete(reserva);

		// Verify the results
		verify(mockReservaRepository).delete(null);
	}

	@Test
	public void testFindById() {
		// Setup
		final Long id = 0L;
		final Optional<Reserva> expectedResult = null;
		when(mockReservaRepository.findById(null)).thenReturn(null);

		// Run the test
		final Optional<Reserva> result = reservaServiceUnderTest.findById(id);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
