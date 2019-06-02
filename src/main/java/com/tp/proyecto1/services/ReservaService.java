package com.tp.proyecto1.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;

@Service
public class ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;

	@Transactional
	public Long save(Reserva reserva){
		return reservaRepository.save(reserva).getId();
	}

	@Transactional
	public List<Reserva> findAll(){
		return this.reservaRepository.findAll();
	}

	@Transactional
	public List<Reserva> findReservas(Reserva reserva){
		return reservaRepository.findAll(Example.of(reserva));
	}

	@Transactional
	public Long findReservaId(Reserva reserva){
		for(Reserva candidata : findReservas(reserva)) {
			if(candidata.equals(reserva)) {
				return candidata.getId();
			}
		}
		return Long.parseLong("-1");
	}

	@Transactional
	public void delete(Reserva reserva) {
		reservaRepository.delete(reserva);
	}

	@Transactional
	public Optional<Reserva> findById(Long id) {		
		return reservaRepository.findById(id);
	}	
}