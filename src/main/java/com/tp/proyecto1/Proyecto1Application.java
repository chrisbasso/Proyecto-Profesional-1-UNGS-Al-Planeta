package com.tp.proyecto1;


import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.repository.viajes.PaisRepository;
import com.tp.proyecto1.services.*;
import com.vaadin.flow.component.UI;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;
import com.tp.proyecto1.repository.viajes.PromocionDescuentoRepository;
import com.tp.proyecto1.repository.viajes.PromocionRepository;
import com.tp.proyecto1.repository.pasajes.PasajeVentaRepository;


@SpringBootApplication
public class Proyecto1Application {

	private static final Logger log = LoggerFactory.getLogger(Proyecto1Application.class);

	public static User logUser;

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Application.class);
	}

	@Bean
	public CommandLineRunner loadData(UserService userService,
									  ViajeService viajeService,
									  VentaService ventaService,
									  ConfiguracionService configuracionService,
									  ReservaRepository reservaRepository,
									  ClienteRepository clienteRepository,
									  FormaDePagoRepository formaDePagoRepository,
									  PasajeVentaRepository pasajeVentaRepository,
									  PromocionRepository promocionRepository,
									  DestinoService destinoService,
									  ConfiguracionService configService,
									  TagDestinoService tagDestinoService,
									  PaisRepository paisRepository
									  /*PromocionDescuentoRepository promocionDescuentosRepository,
									  PromocionDescuentoRepository promocionPuntosRepository*/) {
		return args -> {
			userService.createPrivilegeIfNotFound("READ_PRIVILEGE");
			userService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
			userService.createRoleIfNotFound("ADMIN", userService.getPrivileges());
			User userAdmin = new User("root", "root", userService.getRoles());
			userService.createUserIfNotExist(userAdmin);
			User userEmployee = new User("pepe", "pepe", userService.getRoles());
			userService.createUserIfNotExist(userEmployee);
			viajeService.createTipoTransporteIfNotExist("Avión");
			viajeService.createTipoTransporteIfNotExist("Bus");
			viajeService.createTipoTransporteIfNotExist("Tren");
			viajeService.createTipoTransporteIfNotExist("Buque");
			viajeService.createTipoTransporteIfNotExist("Crucero");
			ventaService.createFormaDePagoIfNotExist("Efectivo");
			ventaService.createFormaDePagoIfNotExist("Débito");
			ventaService.createFormaDePagoIfNotExist("Crédito");
			configService.createConfiguracionIfNotExist("reserva_fecha_maxima", "3");
			if(tagDestinoService.findAll().isEmpty()){
				TagDestino tagPlaya = new TagDestino("Playa");
				TagDestino tagMontania = new TagDestino("Montaña");
				TagDestino tagEuropa = new TagDestino("Europa");
				tagDestinoService.save(tagPlaya);
				tagDestinoService.save(tagMontania);
				tagDestinoService.save(tagEuropa);
			}
			if(paisRepository.findAll().isEmpty()){
				Pais argentina = new Pais();
				argentina.setNombre("Argentina");
				Ciudad buenosAires = new Ciudad();
				buenosAires.setNombre("Buenos Aires");
				buenosAires.setPais(argentina);
				Ciudad mendoza = new Ciudad();
				mendoza.setNombre("Mendoza");
				mendoza.setPais(argentina);
				Ciudad misiones = new Ciudad();
				misiones.setNombre("Misiones");
				misiones.setPais(argentina);
				Set<Ciudad> ciudadesArgentina = new HashSet<>();
				ciudadesArgentina.add(buenosAires);
				ciudadesArgentina.add(misiones);
				ciudadesArgentina.add(mendoza);
				argentina.setCiudades(ciudadesArgentina);
				paisRepository.save(argentina);

				Pais brasil = new Pais();
				brasil.setNombre("Brasil");
				Ciudad sanPablo = new Ciudad();
				sanPablo.setNombre("San Pablo");
				sanPablo.setPais(brasil);
				Ciudad rioJaneiro = new Ciudad();
				rioJaneiro.setNombre("Rio de Janeiro");
				rioJaneiro.setPais(brasil);
				Set<Ciudad> ciudadesBrasil = new HashSet<>();
				ciudadesBrasil.add(rioJaneiro);
				ciudadesBrasil.add(sanPablo);
				brasil.setCiudades(ciudadesBrasil);
				paisRepository.save(brasil);
			}

/*
			System.out.println(promocionRepository.findByViajesAfectados(viajeService.findAll().get(0)));
			
			for(Destino destino : destinoService.findAll())
			{
				System.out.println(destino);
				System.out.println(promocionRepository.findByDestinosAfectados(destino));
			}
			*/
			
			//configuracionService.createConfiguracionIfNotExist("reserva_fecha_maxima","5");
//
//			Cliente cliente = new Cliente("Alberto Carlos","Bustos", "854445");
//			Transporte transporte = new Transporte();
//			transporte.setTipo(viajeService.findAllTipoTransportes().get(0));
//			clienteRepository.save(cliente);
//			Reserva reserva = new Reserva();
//			FormaDePago formaDePago = formaDePagoRepository.findByDescripcion("Efectivo");
//			PasajeVenta venta = new PasajeVenta();
//			Viaje viaje = new Viaje();
//			viaje.setTransporte(transporte);
//
//			viajeService.save(viaje);
//
//			Pago pago1 = new Pago(cliente, venta, formaDePago,500.50, LocalDate.now());
//			Pago pago2 = new Pago(cliente, venta, formaDePago,800.00, LocalDate.now());
//			venta.setViaje(viaje);
//			venta.setPersona(cliente);
//
//			venta.agregarPago(pago1);
//
//			ventaRepository.save(venta);

			//log.info(ventaRepository.findAllPasajeVentas().toString());

//			Pago pago1 = new Pago(cliente, reserva, formaDePago,500.50, LocalDate.now());
//			Pago pago2 = new Pago(cliente, reserva, formaDePago,800.00, LocalDate.now());
//			reserva.agregarPago(pago1);
//			reserva.agregarPago(pago2);
//
//			reservaRepository.save(reserva);

		};
	}

}
