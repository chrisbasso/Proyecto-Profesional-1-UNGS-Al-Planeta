package com.tp.proyecto1.controllers.login;

import com.tp.proyecto1.controllers.usuarios.LoginController;
import com.tp.proyecto1.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class LoginControllerTest {

	@Mock
	private UserService mockUserService;

	private LoginController loginControllerUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		loginControllerUnderTest = new LoginController(mockUserService);
	}

	@Test
	public void testLogout() {
		// Setup

		// Run the test
		loginControllerUnderTest.logout();

		// Verify the results
	}
}
