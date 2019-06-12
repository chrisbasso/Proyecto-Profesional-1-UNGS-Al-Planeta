package com.tp.proyecto1.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Egreso;
import com.tp.proyecto1.model.contabilidad.MovimientoCaja;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;
import com.tp.proyecto1.repository.contabilidad.AsientoRepository;
import com.tp.proyecto1.repository.contabilidad.CabeceraRepository;
import com.tp.proyecto1.repository.contabilidad.CuentaRepository;
import com.tp.proyecto1.repository.contabilidad.PosicionRepository;

@Service
public class AsientoService {
	@Autowired
	private AsientoRepository asientoRepository;
	@Autowired
	private CuentaRepository cuentaRepository;
	@Autowired
	private CabeceraRepository cabeceraRepository;
	@Autowired
	private PosicionRepository posicionRepository;
	
	@Transactional
	public Long save(Asiento asiento){
		cabeceraRepository.save(asiento.getCabecera());
		posicionRepository.saveAll(asiento.getPosiciones());
		return asientoRepository.save(asiento).getId();
	}
	
	@Transactional
	public List<Asiento> findAll(){
		return this.asientoRepository.findAll();
	}

	@Transactional
	public List<Asiento> findAsientos(Asiento asiento){
		return asientoRepository.findAll(Example.of(asiento));
	}

	@Transactional
	public Long findAsientoId(Asiento asiento){
		for(Asiento candidato : findAsientos(asiento)) {
			if(candidato.equals(asiento)) {
				return candidato.getId();
			}
		}
		return Long.parseLong("-1");
	}

	@Transactional
	public void delete(Asiento asiento) {
		asientoRepository.delete(asiento);
	}

	@Transactional
	public Optional<Asiento> findById(Long id) {		
		return asientoRepository.findById(id);
	}

	@Transactional
	public List<Cuenta> findCuentas(){
		return cuentaRepository.findAll(Sort.by("numeroCuenta"));
	}

	@Transactional
	public Cuenta findCuentaByNumero(Integer numeroCuenta){
		return cuentaRepository.findByNumeroCuenta(numeroCuenta);
	}

	@Transactional
	public void saveCuenta(Cuenta cuenta){
		cuentaRepository.save(cuenta);
	}
	
	@Transactional
	public List <Egreso> findEgresos(LocalDate desde, LocalDate hasta){		
		List <Asiento> asientos = asientoRepository.findAllByCabecera_FechaContabilizacionBetween(desde, hasta); 
		List <Egreso> egresos = new ArrayList <Egreso>();
		for(Asiento asiento : asientos) {
			for(Posicion posicion : asiento.getPosiciones()) {
				if(posicion.getCuenta().getTipoCuenta().equals(TipoCuenta.EGRESO)) {
					Egreso candidato = Egreso.getInstancia(asiento.getId(), asiento.getCabecera(), posicion);
					if(candidato != null) {
						egresos.add(candidato);
					}
				}
			}
		}		
		return egresos;		
	}
	
	@Transactional
	public List<Cuenta> findCuentasGasto(){
		return cuentaRepository.findByTipoCuenta(TipoCuenta.EGRESO);
	}
	
	@Transactional
	public List<MovimientoCaja> findMovimientosCaja(LocalDate desde, LocalDate hasta){
		List<MovimientoCaja> movimientos = new ArrayList<MovimientoCaja>();
		
		List <Asiento> asientos = asientoRepository.findAllByCabecera_FechaContabilizacionBetween(desde, hasta);
		for (Asiento asiento : asientos) {
			for (Posicion posicion : asiento.getPosiciones()) {
				MovimientoCaja movim = MovimientoCaja.getInstancia(asiento.getId(), asiento.getCabecera(), posicion); 
				if(movim != null) {
					movimientos.add(movim);
				}
			}			
		}
		return movimientos;
	}
	
	@Transactional
	public List<MovimientoCaja> findMovimientosCajaFiltrado(LocalDate fecha, Usuario usuario, Sucursal suc){
		List<MovimientoCaja> movimientos = new ArrayList<MovimientoCaja>();
		List <Asiento> asientos = asientoRepository.findAllByCabecera_FechaContabilizacionBetween(fecha, fecha);
		for (Asiento asiento : asientos) {
			if(asiento.getUsuario().equals(usuario) && asiento.getSucursal().equals(suc)){
				for (Posicion posicion : asiento.getPosiciones()) {
					MovimientoCaja movim = MovimientoCaja.getInstancia(asiento.getId(), asiento.getCabecera(), posicion); 
					if(movim != null) {
						movimientos.add(movim);
					}
				}			
			}
		}
		return movimientos;
	}
}
