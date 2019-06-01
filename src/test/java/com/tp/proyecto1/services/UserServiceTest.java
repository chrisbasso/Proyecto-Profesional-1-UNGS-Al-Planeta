package com.tp.proyecto1.services;

import com.tp.proyecto1.model.users.Privilege;
import com.tp.proyecto1.model.users.Role;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.repository.users.PrivilegeRepository;
import com.tp.proyecto1.repository.users.RoleRepository;
import com.tp.proyecto1.repository.users.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

	@Mock
	private UserRepository mockUserRepository;
	@Mock
	private RoleRepository mockRoleRepository;
	@Mock
	private PrivilegeRepository mockPrivilegeRepository;

	@InjectMocks
	private UserService userServiceUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testCreateUserIfNotExist() {
		// Setup
		final User user = new User();
		when(mockUserRepository.findByUser("userName")).thenReturn(null);
		when(mockUserRepository.save(null)).thenReturn(null);

		// Run the test
		userServiceUnderTest.createUserIfNotExist(user);

		// Verify the results
	}

	@Test
	public void testFindAll() {
		// Setup
		final List<User> expectedResult = Arrays.asList();
		when(mockUserRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<User> result = userServiceUnderTest.findAll();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testDelete() {
		// Setup
		final User user = null;

		// Run the test
		userServiceUnderTest.delete(user);

		// Verify the results
		verify(mockUserRepository).delete(null);
	}

	@Test
	public void testCreatePrivilegeIfNotFound() {
		// Setup
		final String name = "name";
		final Privilege expectedResult = null;
		when(mockPrivilegeRepository.findByName("name")).thenReturn(null);
		when(mockPrivilegeRepository.save(null)).thenReturn(null);

		// Run the test
		final Privilege result = userServiceUnderTest.createPrivilegeIfNotFound(name);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testSave() {
		// Setup
		final User user = null;
		when(mockUserRepository.save(null)).thenReturn(null);

		// Run the test
		userServiceUnderTest.save(user);

		// Verify the results
	}

	@Test
	public void testCreateRoleIfNotFound() {
		// Setup
		final String name = "name";
		final Collection<Privilege> privileges = Arrays.asList();
		final Role expectedResult = null;
		when(mockRoleRepository.findByName("name")).thenReturn(null);
		when(mockRoleRepository.save(null)).thenReturn(null);

		// Run the test
		final Role result = userServiceUnderTest.createRoleIfNotFound(name, privileges);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetPrivileges() {
		// Setup
		final List<Privilege> expectedResult = Arrays.asList();
		when(mockPrivilegeRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Privilege> result = userServiceUnderTest.getPrivileges();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetRoles() {
		// Setup
		final List<Role> expectedResult = Arrays.asList();
		when(mockRoleRepository.findAll()).thenReturn(Arrays.asList());

		// Run the test
		final List<Role> result = userServiceUnderTest.getRoles();

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testValideUser() {
		// Setup
		final User user = new User();
		when(mockUserRepository.exists(null)).thenReturn(false);

		// Run the test
		final boolean result = userServiceUnderTest.valideUser(user);

		// Verify the results
		assertTrue(result);
	}

	@Test
	public void testGetUserByName() {
		// Setup
		final String userName = "userName";
		final User expectedResult = null;
		when(mockUserRepository.findByUser("userName")).thenReturn(null);

		// Run the test
		final User result = userServiceUnderTest.getUserByName(userName);

		// Verify the results
		assertEquals(expectedResult, result);
	}

	@Test
	public void testGetUserById() {
		// Setup
		final Long userId = 0L;
		final User expectedResult = null;
		when(mockUserRepository.findById(null)).thenReturn(null);

		// Run the test
		final User result = userServiceUnderTest.getUserById(userId);

		// Verify the results
		assertEquals(expectedResult, result);
	}
}
