package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Interesado;
import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.clientes.InteresadoRepository;
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

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	InteresadoRepository interesadoRepository;

	@Transactional
	public void save(Evento evento){
		eventoRepository.save(evento);
		if(evento.getPersona() instanceof Interesado){
			interesadoRepository.save((Interesado) evento.getPersona());
		}
	}

	@Transactional
	public List<Evento> findAll(){
		return this.eventoRepository.findAll();
	}

	@Transactional
	public List<Evento> findEventosByPersona(Persona persona){
		return eventoRepository.findAllByPersona(persona);
	}

}
