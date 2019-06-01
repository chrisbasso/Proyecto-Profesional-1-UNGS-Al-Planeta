package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.clientes.InteresadoRepository;
import com.tp.proyecto1.repository.eventos.EventoRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventoServiceTest {

	private EventoService eventoServiceUnderTest;

	@Before
	public void setUp() {
		eventoServiceUnderTest = new EventoService();
		eventoServiceUnderTest.eventoRepository = mock(EventoRepository.class);
		eventoServiceUnderTest.clienteRepository = mock(ClienteRepository.class);
		eventoServiceUnderTest.interesadoRepository = mock(InteresadoRepository.class);
	}

	@Test
	public void testSave() {
		// Setup
		final Evento evento = new Evento();
		when(eventoServiceUnderTest.eventoRepository.save(null)).thenReturn(null);
		when(eventoServiceUnderTest.interesadoRepository.save(null)).thenReturn(null);

		// Run the test
		eventoServiceUnderTest.save(evento);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<Evento> expectedResult = Arrays.asList();
		when(eventoServiceUnderTest.eventoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Evento> result = eventoServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindEventosByPersona() {
		// Setup
		final Persona persona = null;
		final List<Evento> expectedResult = Arrays.asList();
		when(eventoServiceUnderTest.eventoRepository.findAllByPersona(null)).thenReturn(Arrays.asList());

		// Run the test
		final List<Evento> result = eventoServiceUnderTest.findEventosByPersona(persona);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testFindEventos() {
		// Setup
		final Evento eventoConsulta = new Evento();
		final List<Evento> expectedResult = Arrays.asList();
		when(eventoServiceUnderTest.eventoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Evento> result = eventoServiceUnderTest.findEventos(eventoConsulta);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
