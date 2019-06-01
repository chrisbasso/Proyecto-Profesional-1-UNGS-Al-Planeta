package com.tp.proyecto1.controllers.reportes;

import com.tp.proyecto1.services.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class ReportesControllerTest {

	@Mock
	private UserService mockUserService;
	@Mock
	private ClienteService mockClienteService;
	@Mock
	private TransaccionService mockTransaccionService;
	@Mock
	private PagosService mockPagosService;
	@Mock
	private ViajeService mockViajeService;
	@Mock
	private SucursalService mockSucursalService;

	@InjectMocks
	private ReportesController reportesControllerUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}
}
