package com.tp.proyecto1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.Posicion;
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
	public void save(Asiento asiento, Cabecera cabecera,List<Posicion>posiciones){
		cabeceraRepository.save(cabecera);
		posicionRepository.saveAll(posiciones);
		asientoRepository.save(asiento);
	}
	
	@Transactional
	public void save(Asiento asiento){
		asientoRepository.save(asiento);
	}

	@Transactional
	public List<Asiento> findAll(){
		return this.asientoRepository.findAll();
	}

	@Transactional
	public List<Cuenta> findAllCuentas(){
		return this.cuentaRepository.findAll();
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
		return cuentaRepository.findAll();
	}

	@Transactional
	public void saveCuenta(Cuenta cuenta){
		cuentaRepository.save(cuenta);
	}

}