package com.tp.proyecto1;


import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.*;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;
import com.tp.proyecto1.repository.pasajes.VentaRepository;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


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
									  ReservaRepository reservaRepository,
									  ClienteRepository clienteRepository,
									  FormaDePagoRepository formaDePagoRepository,
									  VentaRepository ventaRepository) {
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
//
//			Cliente cliente = new Cliente("Alberto Carlos","Bustos", "854445");
//			Transporte transporte = new Transporte();
//			transporte.setTipo(viajeService.findAllTipoTransportes().get(0));
//			clienteRepository.save(cliente);
//			Reserva reserva = new Reserva();
//			FormaDePago formaDePago = formaDePagoRepository.findByDescripcion("Efectivo");
//			Venta venta = new Venta();
//			Viaje viaje = new Viaje();
//			viaje.setTransporte(transporte);
//
//			viajeService.save(viaje);
//
//			Pago pago1 = new Pago(cliente, venta, formaDePago,500.50, LocalDate.now());
//			Pago pago2 = new Pago(cliente, venta, formaDePago,800.00, LocalDate.now());
//			venta.setViaje(viaje);
//			venta.setCliente(cliente);
//
//			venta.agregarPago(pago1);
//
//			ventaRepository.save(venta);

			//log.info(ventaRepository.findAll().toString());

//			Pago pago1 = new Pago(cliente, reserva, formaDePago,500.50, LocalDate.now());
//			Pago pago2 = new Pago(cliente, reserva, formaDePago,800.00, LocalDate.now());
//			reserva.agregarPago(pago1);
//			reserva.agregarPago(pago2);
//
//			reservaRepository.save(reserva);

		};
	}

}
