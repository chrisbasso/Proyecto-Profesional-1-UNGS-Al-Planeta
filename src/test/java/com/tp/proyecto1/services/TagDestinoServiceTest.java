package com.tp.proyecto1.services;

import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.repository.viajes.TagDestinoRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TagDestinoServiceTest {

	private TagDestinoService tagDestinoServiceUnderTest;

	@Before
	public void setUp() {
		tagDestinoServiceUnderTest = new TagDestinoService();
		tagDestinoServiceUnderTest.tagDestinoRepository = mock(TagDestinoRepository.class);
	}

	@Test
	public void testSave() {
		// Setup
		final TagDestino tagDestino = null;
		when(tagDestinoServiceUnderTest.tagDestinoRepository.save(null)).thenReturn(null);

		// Run the test
		tagDestinoServiceUnderTest.save(tagDestino);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<TagDestino> expectedResult = Arrays.asList();
		when(tagDestinoServiceUnderTest.tagDestinoRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<TagDestino> result = tagDestinoServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final TagDestino tagDestino = null;

		// Run the test
		tagDestinoServiceUnderTest.delete(tagDestino);

		// Verify the results
		verify(tagDestinoServiceUnderTest.tagDestinoRepository).delete(null);
	}
}
