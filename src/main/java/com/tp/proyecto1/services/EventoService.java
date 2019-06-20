package com.tp.proyecto1.services;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.clientes.Interesado;
import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import com.tp.proyecto1.repository.clientes.InteresadoRepository;
import com.tp.proyecto1.repository.eventos.EventoRepository;
import com.tp.proyecto1.repository.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	
	@Autowired
	UserRepository userRepository;

	@Transactional
	public void save(Evento evento){
		
		eventoRepository.save(evento);
		if(evento.getPersona() instanceof Interesado){
			interesadoRepository.save((Interesado) evento.getPersona());
		}
	}

	@Transactional
	public List<Evento> findAll(Evento evento) {

		List<Evento> eventos = eventoRepository.findAll(Example.of(evento));

		if(Proyecto1Application.logUser != null){
			String role = Proyecto1Application.logUser.getRol().getName();
			if(role.equals("VENDEDOR")){
				eventos = eventos.stream().filter(e->e.getUsuarioAsignado().equals(Proyecto1Application.logUser)).collect(Collectors.toList());
			}
			else if (role.equals("SUPERVISOR"))
			{
				List<Evento>auxiliar = new ArrayList<>();
				for (User empleado : userRepository.findBySucursal(Proyecto1Application.logUser.getSucursal()))
				{
					auxiliar.addAll( eventos.stream().filter(e->e.getUsuarioAsignado().equals(empleado)).collect(Collectors.toList()));
				}
				eventos = auxiliar;
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
		List<Evento> eventos = new ArrayList<>();
		if(Proyecto1Application.logUser != null){
			String role = Proyecto1Application.logUser.getRol().getName();
			if(role.equals("VENDEDOR")){
				eventoConsulta.setUsuarioAsignado(Proyecto1Application.logUser);
				eventos = eventoRepository.findAll(Example.of(eventoConsulta));
			}
			else if (role.equals("SUPERVISOR"))
			{
				for (User empleado : userRepository.findBySucursal(Proyecto1Application.logUser.getSucursal()))
				{
					eventoConsulta.setUsuarioAsignado(empleado);
					eventos.addAll(eventoRepository.findAll(Example.of(eventoConsulta)));
				}
			}
		}
		return eventos;
	}
}
