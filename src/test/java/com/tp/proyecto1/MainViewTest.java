package com.tp.proyecto1;

import com.tp.proyecto1.controllers.clientes.ClientesController;
import com.tp.proyecto1.controllers.configuracion.ConfiguracionController;
import com.tp.proyecto1.controllers.contabilidad.AsientosController;
import com.tp.proyecto1.controllers.eventos.EventosController;
import com.tp.proyecto1.controllers.usuarios.LoginController;
import com.tp.proyecto1.controllers.promociones.PromocionesController;
import com.tp.proyecto1.controllers.reportes.ReportesController;
import com.tp.proyecto1.controllers.reserva.ReservasController;
import com.tp.proyecto1.controllers.venta.VentasController;
import com.tp.proyecto1.controllers.viajes.ViajesController;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class MainViewTest {

	@Mock
	private ClientesController mockClientesController;
	@Mock
	private LoginController mockLoginController;
	@Mock
	private ViajesController mockViajesController;
	@Mock
	private VentasController mockVentasController;
	@Mock
	private EventosController mockEventosController;
	@Mock
	private ReservasController mockReservasController;
	@Mock
	private AsientosController mockAsientosController;
	@Mock
	private PromocionesController mockPromocionesController;
	@Mock
	private ReportesController mockReportesController;
	@Mock
	private ConfiguracionController mockConfiguracionController;

	@InjectMocks
	private MainView mainViewUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
	}
}
