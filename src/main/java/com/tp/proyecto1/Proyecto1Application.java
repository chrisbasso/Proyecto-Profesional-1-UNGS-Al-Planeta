package com.tp.proyecto1;


import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;


@SpringBootApplication
public class Proyecto1Application {

	private static final Logger log = LoggerFactory.getLogger(Proyecto1Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Application.class);
	}

	@Bean
	public CommandLineRunner loadData(UserService userService,
									  ViajeService viajeService,
									  VentaService ventaService,
									  ConfiguracionService configuracionService,
									  ReservaService reservaService,
									  ClienteService clienteService,
									  FormaDePagoRepository formaDePagoService) {
		return args -> {
			userService.createPrivilegeIfNotFound("READ_PRIVILEGE");
			userService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
			userService.createRoleIfNotFound("ADMIN", userService.getPrivileges());
			User userAdmin = new User("root", "root", userService.getRoles());
			userService.createUserIfNotExist(userAdmin);
			viajeService.createTipoTransporteIfNotExist("Avión");
			viajeService.createTipoTransporteIfNotExist("Bus");
			viajeService.createTipoTransporteIfNotExist("Tren");
			viajeService.createTipoTransporteIfNotExist("Buque");
			viajeService.createTipoTransporteIfNotExist("Crucero");
			ventaService.createFormaDePagoIfNotExist("Efectivo");
			ventaService.createFormaDePagoIfNotExist("Débito");
			ventaService.createFormaDePagoIfNotExist("Crédito");
			configuracionService.createConfiguracionIfNotExist("reserva_fecha_maxima","5");

			/*
			 * Cliente cliente = crearClientes(clienteService); Viaje viaje =
			 * crearViajes(viajeService);
			 * 
			 * FormaDePago formaDePago = formaDePagoService.findByDescripcion("Efectivo");
			 * Venta venta = new Venta(); Pago pago1 = new Pago(cliente, venta,
			 * formaDePago,500.50, LocalDate.now()); Pago pago2 = new Pago(cliente, venta,
			 * formaDePago,800.00, LocalDate.now()); venta.setViaje(viaje);
			 * venta.setCliente(cliente); venta.agregarPago(pago1);
			 * ventaService.save(venta);
			 * 
			 * log.info(ventaService.findAll().toString());
			 */
		};
	}

	private Viaje crearViajes(ViajeService viajeService) {
		Viaje viaje = new Viaje();
		for(int i = 0; i < 5; i++) {
			Transporte transporte = new Transporte();
			transporte.setTipo(viajeService.findAllTipoTransportes().get(0));
			TagDestino tag = new TagDestino();
			viajeService.saveTagDestino(tag);
			Destino destino = new Destino("BsAs", "AR", "Evitar el centro", tag);
			viaje.setDestino(destino);
			viaje.setTransporte(transporte);
			viaje.setFechaSalida(LocalDate.now());
			viaje.setHoraSalida(LocalTime.now());
			viaje.setFechaLlegada(LocalDate.of(2019, 12, 23));
			viaje.setHoraLlegada(LocalTime.now());
			viaje.setPrecio(i * 100.00);
			viaje.setDescripcion("Un viaje más");
			viaje.setActivo(true);			
			viajeService.save(viaje);
		}		
		return viaje;
	}

	private Cliente crearClientes(ClienteService clienteService) {
		Cliente cliente = new Cliente("Alberto Carlos","Bustos", "854445");
		clienteService.save(cliente);		
		Cliente cliente2 = new Cliente("Homero","Naisanisi", "12311");
		clienteService.save(cliente2);		
		return cliente;
	}
}
