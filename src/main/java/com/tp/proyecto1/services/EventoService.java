package com.tp.proyecto1.services;

import com.tp.proyecto1.Proyecto1Application;
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
import java.util.stream.Collectors;

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
	public List<Evento> findAll() {

		List<Evento> eventos = eventoRepository.findAll();

		if(Proyecto1Application.logUser != null){
			String role = Proyecto1Application.logUser.getRol().getName();
			if(role.equals("VENDEDOR")){
				eventos = eventos.stream().filter(e->e.getUsuarioAsignado().equals(Proyecto1Application.logUser)).collect(Collectors.toList());
			}
		}

		return eventos;
	}

	@Transactional
	public List<Evento> findEventosByPersona(Persona persona){
		return eventoRepository.findAllByPersona(persona);
	}

	@Transactional
	public List<Evento> findEventos(Evento eventoConsulta) {

		if(Proyecto1Application.logUser != null){
			String role = Proyecto1Application.logUser.getRol().getName();
			if(role.equals("VENDEDOR")){
				eventoConsulta.setUsuarioAsignado(Proyecto1Application.logUser);
			}
		}
		return eventoRepository.findAll(Example.of(eventoConsulta));
	}
}
