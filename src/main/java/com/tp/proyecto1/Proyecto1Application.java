package com.tp.proyecto1;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;

import com.tp.proyecto1.controllers.reserva.ReservaREST;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.pasajes.EstadoTransaccion;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.Transporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.lotePuntos.LotePuntoRepository;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;
import com.tp.proyecto1.repository.pasajes.PasajeVentaRepository;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;
import com.tp.proyecto1.repository.pasajes.TransaccionRepository;
import com.tp.proyecto1.repository.sucursales.SucursalRepository;
import com.tp.proyecto1.repository.viajes.PaisRepository;
import com.tp.proyecto1.repository.viajes.PromocionRepository;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.services.TagDestinoService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.services.ViajeService;


@SpringBootApplication
public class Proyecto1Application {

	private static final Logger log = LoggerFactory.getLogger(Proyecto1Application.class);

	public static User logUser;

	public static Sucursal sucursal;

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Application.class);
	}

	@Bean
	public CommandLineRunner loadData(UserService userService,
									  ViajeService viajeService,
									  VentaService ventaService,
									  ConfiguracionService configService,
									  ReservaRepository reservaRepository,
									  ClienteRepository clienteRepository,
									  FormaDePagoRepository formaDePagoRepository,
									  PasajeVentaRepository pasajeVentaRepository,
									  PromocionRepository promocionRepository,
									  TagDestinoService tagDestinoService,
									  PaisRepository paisRepository,
									  TransaccionRepository transaccionRepository,
									  SucursalRepository sucursalRepository,
									  AsientoService asientoService,
									  LotePuntoRepository lotePuntoRepository) {
		return args -> {
			crearUsuarios(userService);
			crearTiposTransportes(viajeService);
			crearFormasDePago(ventaService);
			crearConfiguracion(configService);
			crearTagsDestino(tagDestinoService);
			crearPaisesCiudades(paisRepository);
		//	crearViajes(viajeService);
			setSurcursales(sucursalRepository);
			crearCuentas(asientoService);
			procesoVertificarVencimientos(viajeService, reservaRepository, promocionRepository, lotePuntoRepository);

		};
	}

	private void procesoVertificarVencimientos(ViajeService viajeService, ReservaRepository reservaRepository, PromocionRepository promocionRepository, LotePuntoRepository lotePuntoRepository) {
		Timer timer = new Timer();

		TimerTask task = new TimerTask() {

			@Override
			public void run()
			{
				log.info("Verificando Vencimientos...");

				List<Reserva> reservas = reservaRepository.findAll();				
				for (Reserva reserva : reservas) {
					boolean seDebeAnular = false;
					if(!reserva.getEstadoTransaccion().equals(EstadoTransaccion.VENDIDA)) {
						if(ReservaREST.esAnulablePorVencimientoFechaReserva(reserva)) {
							seDebeAnular = true;
						}else if(ReservaREST.esAnulablePorVencimientoPago(reserva)) {
							seDebeAnular = true;
						}
					}					
					if(seDebeAnular) {
						reserva.setEstadoTransaccion(EstadoTransaccion.VENCIDA);
						reserva.inactivar();
						reservaRepository.save(reserva);
					}
				}

				Viaje viajeExample = new Viaje();
				viajeExample.setFechaSalida(LocalDate.now());
				List<Viaje> viajes = viajeService.findViajes(viajeExample,null, null);
				for (Viaje viaje : viajes) {
					if(viaje.getHoraSalida().isBefore(LocalTime.now()) && viaje.isActivo()){
						log.info(viaje.getHoraSalida().toString());
						viaje.setActivo(Boolean.FALSE);
						viajeService.save(viaje);
					}
				}

				Promocion promocionExample = new Promocion();
				promocionExample.setFechaVencimiento(LocalDate.now().minusDays(1));
				List<Promocion> promociones = promocionRepository.findAll(Example.of(promocionExample));
				for (Promocion promocion : promociones) {
					if(promocion.isActivo()){
						promocion.setActivo(Boolean.FALSE);
						promocionRepository.save(promocion);
					}
				}
				
				Promocion promocionExamplePasajesCero = new Promocion();
				promocionExamplePasajesCero.setCantidadPasajesRestantes(0);
				List<Promocion> promocionesPasajesCero = promocionRepository.findAll(Example.of(promocionExamplePasajesCero));
				for (Promocion promocion : promocionesPasajesCero) {
					promocion.setActivo(Boolean.FALSE);
					promocionRepository.save(promocion);
				}
				
				LotePunto lotePuntoExample = new LotePunto();
				lotePuntoExample.setFechaVencimiento(LocalDate.now().minusDays(1));
				List<LotePunto> lotePuntos = lotePuntoRepository.findAll(Example.of(lotePuntoExample));
				for(LotePunto lotePunto : lotePuntos) {
					lotePunto.setActivo(Boolean.FALSE);
					lotePuntoRepository.save(lotePunto);
				}

			}
		};

		timer.schedule(task, 10, 60000); //una vez por minuto
	}

	private void setSurcursales(SucursalRepository sucursalRepository) {
		if(sucursalRepository.findAll().isEmpty()){
			sucursalRepository.save(new Sucursal("Polvorines"));
			sucursalRepository.save(new Sucursal("Palermo"));
			sucursalRepository.save(new Sucursal("Central"));
		}
		sucursal = sucursalRepository.findAll().get(0); // setea la primer sucursal por defecto, solo para testear

	}

	private void crearPaisesCiudades(PaisRepository paisRepository) {
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
	}

	private void crearTagsDestino(TagDestinoService tagDestinoService) {
		if(tagDestinoService.findAll().isEmpty()){
			TagDestino tagPlaya = new TagDestino("Playa");
			TagDestino tagMontania = new TagDestino("Montaña");
			TagDestino tagEuropa = new TagDestino("Europa");
			tagDestinoService.save(tagPlaya);
			tagDestinoService.save(tagMontania);
			tagDestinoService.save(tagEuropa);
		}
	}

	private void crearConfiguracion(ConfiguracionService configService) {
		configService.createConfiguracionIfNotExist("reserva_porcentaje_pago_parcial-cifra", "30");
		configService.createConfiguracionIfNotExist("reserva_vencimiento_pago_parcial-dias", "10");
		configService.createConfiguracionIfNotExist("reserva_vencimiento_reserva-dias", "5");
		configService.createConfiguracionIfNotExist("pesos_por_punto", "10");
		configService.createConfiguracionIfNotExist("cant_anios_venc_puntos", "1");	
	}

	private void crearFormasDePago(VentaService ventaService) {
		ventaService.createFormaDePagoIfNotExist("Efectivo");
		ventaService.createFormaDePagoIfNotExist("Débito");
		ventaService.createFormaDePagoIfNotExist("Crédito");
	}

	private void crearTiposTransportes(ViajeService viajeService) {
		viajeService.createTipoTransporteIfNotExist("Avión");
		viajeService.createTipoTransporteIfNotExist("Bus");
		viajeService.createTipoTransporteIfNotExist("Tren");
		viajeService.createTipoTransporteIfNotExist("Buque");
		viajeService.createTipoTransporteIfNotExist("Crucero");
	}

	private void crearUsuarios(UserService userService) {
		userService.createPrivilegeIfNotFound("READ_PRIVILEGE");
		userService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
		userService.createRoleIfNotFound("ADMIN", userService.getPrivileges());
		User userAdmin = new User("root", "root", userService.getRoles());
		userService.createUserIfNotExist(userAdmin);
		User userEmployee = new User("pepe", "pepe", userService.getRoles());
		userService.createUserIfNotExist(userEmployee);
		User userBatch = new User("batch", "batch", userService.getRoles());
		userService.createUserIfNotExist(userBatch);
	}
	
	private void crearCuentas(AsientoService asientoService) {
		if(asientoService.findCuentas().size()== 0) {
			asientoService.saveCuenta(new Cuenta(100, "Efectivo", TipoCuenta.ACTIVO));
			asientoService.saveCuenta(new Cuenta(101, "Banco", TipoCuenta.ACTIVO));
			asientoService.saveCuenta(new Cuenta(102, "Tarjetas de credito", TipoCuenta.ACTIVO));
			asientoService.saveCuenta(new Cuenta(103, "Clientes por cobrar", TipoCuenta.ACTIVO));
			asientoService.saveCuenta(new Cuenta(200, "Proveedores", TipoCuenta.PASIVO));
			asientoService.saveCuenta(new Cuenta(201, "Sueldos", TipoCuenta.PASIVO));
			asientoService.saveCuenta(new Cuenta(202, "Pasajes reservados", TipoCuenta.PASIVO));
			asientoService.saveCuenta(new Cuenta(300, "Patrimonio Neto", TipoCuenta.PN));
			asientoService.saveCuenta(new Cuenta(400, "Ventas", TipoCuenta.INGRESO));
			asientoService.saveCuenta(new Cuenta(401, "Reservas vencidas", TipoCuenta.INGRESO));
			asientoService.saveCuenta(new Cuenta(500, "Alquiler", TipoCuenta.EGRESO));
			asientoService.saveCuenta(new Cuenta(501,"Limpieza", TipoCuenta.EGRESO));
			asientoService.saveCuenta(new Cuenta(502,"Impuestos", TipoCuenta.EGRESO));
			asientoService.saveCuenta(new Cuenta(503,"Sueldos", TipoCuenta.EGRESO));
			asientoService.saveCuenta(new Cuenta(504,"Mantenimiento", TipoCuenta.EGRESO));			
		}
	}
	/*
	private void crearViajes(ViajeService viajeService) {
		for (int i = 0; i<5; i++) {
	        Transporte transporte = new Transporte("codigo " + i,viajeService.findAllTipoTransportes().get(0), i*5, "clase " + i);
			Viaje viaje = new Viaje(viajeService.findAllCiudades().get(0), transporte, LocalDate.now().plusDays(10), LocalTime.now(), i*2000.0, "Viaje " + i, true);
			viajeService.save(viaje);
		}
		
	}*/
}