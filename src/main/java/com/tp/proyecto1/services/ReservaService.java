package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.repository.pasajes.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	
	@Transactional
	public List <Reserva> findByIdCliente(Long idCliente){
		return reservaRepository.findAllByCliente_Id(idCliente);
	}

	@Transactional
	public List <Reserva> findByIdViaje(Long idViaje) {
		return reservaRepository.findAllByViaje_Id(idViaje);		
	}

	@Transactional
	public List <Reserva> findByIdViajeIdCliente(Long idViaje, Long idCliente) {
		return reservaRepository.findAllByViaje_IdAndCliente_Id(idViaje, idCliente);		
	}

	public List <Reserva> findByCiudad(Ciudad ciudad) {
		return reservaRepository.findAllByViaje_Origen(ciudad);
	}

	public List <Reserva>  findByFecha(LocalDate fecha) {
		return reservaRepository.findAllByFecha(fecha);
	}	
}