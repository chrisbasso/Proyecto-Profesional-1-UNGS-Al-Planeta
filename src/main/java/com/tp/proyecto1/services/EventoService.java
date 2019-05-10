package com.tp.proyecto1.services;

import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.repository.eventos.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoService {

	@Autowired
	EventoRepository eventoRepository;

	@Transactional
	public void save(Evento evento){
		eventoRepository.save(evento);
	}

	@Transactional
	public List<Evento> findAll(){
		return this.eventoRepository.findAll();
	}

	@Transactional
	public List<Evento> findReservas(Evento evento){
		return eventoRepository.findAll(Example.of(evento));
	}

}
