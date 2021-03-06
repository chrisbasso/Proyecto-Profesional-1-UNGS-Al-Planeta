package com.tp.proyecto1;


import com.tp.proyecto1.controllers.reserva.ReservaREST;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.model.pasajes.EstadoTransaccion;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.lotePuntos.LotePuntoRepository;
import com.tp.proyecto1.repository.pasajes.*;
import com.tp.proyecto1.repository.sucursales.SucursalRepository;
import com.tp.proyecto1.repository.viajes.ContinenteRepository;
import com.tp.proyecto1.repository.viajes.PaisRepository;
import com.tp.proyecto1.repository.viajes.PromocionRepository;
import com.tp.proyecto1.services.*;
import com.tp.proyecto1.utils.EnviadorDeMail;
import com.tp.proyecto1.views.reportes.VoucherVentaJR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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
									  LotePuntoRepository lotePuntoRepository,
									  ContinenteRepository continenteRepository,
									  VentaRepository ventaRepository) {
		return args -> {
			setSurcursales(sucursalRepository);
			crearUsuarios(userService);
			crearTiposTransportes(viajeService);
			crearFormasDePago(ventaService);
			crearConfiguracion(configService);
			crearTagsDestino(tagDestinoService);
			crearPaisesCiudades(continenteRepository);
			//crearViajes(viajeService);
			crearCuentas(asientoService);
			procesoVertificarVencimientos(viajeService, reservaRepository, promocionRepository, lotePuntoRepository,ventaRepository);
			procesoEnvioDeVoucher(ventaRepository);

		};
	}

	private void procesoVertificarVencimientos(ViajeService viajeService, ReservaRepository reservaRepository, PromocionRepository promocionRepository, LotePuntoRepository lotePuntoRepository, VentaRepository ventaRepository) {
		Timer timer = new Timer();

		TimerTask task = new TimerTask() {

			@Override
			public void run()
			{
				log.info("Verificando Vencimientos...");
				List<Reserva> reservas = reservaRepository.findAll();				
				for (Reserva reserva : reservas) {
					boolean seDebeAnular = false;
					if(!(EstadoTransaccion.VENDIDA).equals(reserva.getEstadoTransaccion())) {
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
				LotePunto lotePuntoExample2 = new LotePunto();
				lotePuntoExample2.setIsAcreditado(Boolean.FALSE);
				Venta venta = new Venta();
				
				for (Viaje viaje : viajes) {
					if(viaje.getHoraSalida().isBefore(LocalTime.now()) && viaje.isActivo()){
						log.info(viaje.getHoraSalida().toString());
						venta.setViaje(viaje);
						lotePuntoExample2.setVenta(venta);
						List<LotePunto> lotePuntos2 = lotePuntoRepository.findAll(Example.of(lotePuntoExample2));
						for(LotePunto lotePunto : lotePuntos2){
							if(lotePunto.getVenta().getEstadoTransaccion() != EstadoTransaccion.CANCELADA || 
									lotePunto.getVenta().getEstadoTransaccion() != EstadoTransaccion.PENALIZADA) {
								lotePunto.setIsAcreditado(Boolean.TRUE);
								lotePuntoRepository.save(lotePunto);
							}
						}
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
				
				/*log.info("Verificando envio de vouchers...");
				
				EnviadorDeMail enviadorDeMail = new EnviadorDeMail();
				LocalDate fechaActual = LocalDate.now();
				List<Venta> ventas = ventaRepository.findAll();	
				for (Venta venta : ventas) {
					LocalDate fechaVoucher =  venta.getViaje().getFechaSalida().minusDays(1);
					if (venta.isActivo() && fechaVoucher.compareTo(fechaActual) == 0 && venta.getEstadoTransaccion() != EstadoTransaccion.VOUCHERENVIADO 
							&& venta.getEstadoTransaccion() != EstadoTransaccion.CANCELADA  && venta.getEstadoTransaccion() != EstadoTransaccion.PENALIZADA){
						venta.setEstadoTransaccion(EstadoTransaccion.VOUCHERENVIADO);
						ventaRepository.save(venta);
						List<Venta> ventasList = new ArrayList<Venta>();
						ventasList.add(venta);
						VoucherVentaJR voucherVenta = new VoucherVentaJR(ventas);
						voucherVenta.exportarAPdf(venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni());
						enviadorDeMail.enviarConGmailVoucher(venta.getCliente().getEmail(),
								"Voucher del Viaje- " + venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni(), venta);
					}
				}*/
			}
		};

		timer.schedule(task, 100, 700000); //una vez por minuto
	}
	
	private void procesoEnvioDeVoucher(VentaRepository ventaRepository) {
		Timer timer = new Timer();

		TimerTask task = new TimerTask() {

			@Override
			public void run()
			{
				log.info("Verificando envio de vouchers...");

				LocalDate fechaActual = LocalDate.now();
				List<Venta> ventas = ventaRepository.findAll();	
				for (Venta venta : ventas) {
					int cantDiasRestantes = venta.getViaje().getFechaSalida().compareTo(fechaActual);
					if (venta.isActivo() && cantDiasRestantes == 2){
						LocalDate fechaVoucher =  venta.getViaje().getFechaSalida().minusDays(1);
						if (venta.isActivo() && fechaVoucher.compareTo(fechaActual) == 0 && (venta.getEstadoTransaccion() != EstadoTransaccion.VOUCHERENVIADO 
								|| venta.getEstadoTransaccion() != EstadoTransaccion.CANCELADA  || venta.getEstadoTransaccion() != EstadoTransaccion.PENALIZADA)){
							venta.setEstadoTransaccion(EstadoTransaccion.VOUCHERENVIADO);
							ventaRepository.save(venta);
							EnviadorDeMail enviadorDeMail = new EnviadorDeMail();
							List<Venta> ventasList = new ArrayList<Venta>();
							ventasList.add(venta);
							VoucherVentaJR voucherVenta = new VoucherVentaJR(ventas);
							voucherVenta.exportarAPdf(venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni());
							enviadorDeMail.enviarConGmailVoucher(venta.getCliente().getEmail(),
									"Voucher del Viaje- " + venta.getCliente().getNombreyApellido()+ "-"+ venta.getCliente().getDni(), venta);
						}
					}
				
				}
			}
		};

		timer.schedule(task, 10, 800000); //una vez por minuto
	}
		

	private void setSurcursales(SucursalRepository sucursalRepository) {
		if(sucursalRepository.findAll().isEmpty()){
			sucursalRepository.save(new Sucursal("Polvorines"));
			sucursalRepository.save(new Sucursal("Palermo"));
			sucursalRepository.save(new Sucursal("Central"));
		}
		sucursal = sucursalRepository.findAll().get(0); // setea la primer sucursal por defecto, solo para testear

	}

	private void crearPaisesCiudades(ContinenteRepository continenteRepository) {
		if(continenteRepository.findAll().isEmpty()){

			Continente america = new Continente("America");
			Continente europa = new Continente("Europa");
			Continente asia = new Continente("Asia");
			Continente oceania = new Continente("Oceania");
			Continente africa = new Continente("Africa");

			Pais argentina = new Pais("Argentina");
			Pais brasil = new Pais("Brasil");

			Provincia buenosAires = new Provincia("Buenos Aires");
			Provincia mendoza = new Provincia("Mendoza");
			Provincia misiones = new Provincia("Misiones");

			Ciudad laPlata = new Ciudad("La Plata");
			Ciudad posadas = new Ciudad("Posadas");

			laPlata.setProvincia(buenosAires);
			posadas.setProvincia(misiones);

			buenosAires.getCiudades().add(laPlata);
			misiones.getCiudades().add(posadas);
			buenosAires.setPais(argentina);
			misiones.setPais(argentina);
			mendoza.setPais(argentina);

			argentina.getProvincias().add(buenosAires);
			argentina.getProvincias().add(mendoza);
			argentina.getProvincias().add(misiones);
			argentina.setContinente(america);
			brasil.setContinente(america);

			america.getPaises().add(argentina);
			america.getPaises().add(brasil);

			continenteRepository.save(america);
			continenteRepository.save(europa);
			continenteRepository.save(asia);
			continenteRepository.save(oceania);
			continenteRepository.save(africa);
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
		configService.createConfiguracionIfNotExist("movimiento_caja-numero_cuenta","100");
		configService.createConfiguracionIfNotExist("punto_por_pesos", "10");
		configService.createConfiguracionIfNotExist("mail_remitente_gmail", "proy.despegue");
		configService.createConfiguracionIfNotExist("mail_remitente_pass_gmail", "Laboratorio1");
		configService.createConfiguracionIfNotExist("puerto_seguro_google", "587");

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
		userService.createRoleIfNotFound("ADMINISTRADOR", userService.getPrivileges());
		userService.createRoleIfNotFound("VENDEDOR", userService.getPrivileges());
		userService.createRoleIfNotFound("SUPERVISOR", userService.getPrivileges());
		userService.createRoleIfNotFound("CONTADOR", userService.getPrivileges());
		userService.createRoleIfNotFound("CLIENTE", userService.getPrivileges());
		User userAdmin = new User("admin", "admin", userService.getRolByName("ADMINISTRADOR"));
		userAdmin.setEnabled(true);
		userAdmin.setSucursal(sucursal);
		userService.createUserIfNotExist(userAdmin);
		User userVendedor = new User("vendedor", "vendedor", userService.getRolByName("VENDEDOR"));
		userVendedor.setEnabled(true);
		userVendedor.setSucursal(sucursal);
		userService.createUserIfNotExist(userVendedor);
		User userVendedor2 = new User("vendedor2", "vendedor2", userService.getRolByName("VENDEDOR"));
		userVendedor2.setEnabled(true);
		userVendedor2.setSucursal(sucursal);
		userService.createUserIfNotExist(userVendedor2);
		User userSupervisor = new User("supervisor", "supervisor", userService.getRolByName("SUPERVISOR"));
		userSupervisor.setEnabled(true);
		userSupervisor.setSucursal(sucursal);
		userService.createUserIfNotExist(userSupervisor);
		User userContador = new User("contador", "contador", userService.getRolByName("CONTADOR"));
		userContador.setEnabled(true);
		userContador.setSucursal(sucursal);
		userService.createUserIfNotExist(userContador);
		User userCliente = new User("cliente", "cliente", userService.getRolByName("CLIENTE"));
		userCliente.setEnabled(true);
		userCliente.setSucursal(sucursal);
		userService.createUserIfNotExist(userCliente);
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
}