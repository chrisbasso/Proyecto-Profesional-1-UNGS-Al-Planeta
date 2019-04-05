package com.tp.proyecto1.services;

import com.tp.proyecto1.repository.PersonaRepository;
import com.tp.proyecto1.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	private static final Logger log = LoggerFactory.getLogger(PersonaService.class);

	@Transactional
	public void save(Persona persona){
		personaRepository.save(persona);
	}

	@Transactional
	public List<Persona> findAll(){
		return this.personaRepository.findAll();
	}

	@Transactional
	public void delete(Persona persona) {
		personaRepository.delete(persona);
	}
}

