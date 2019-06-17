package com.tp.proyecto1.repository.eventos;

import com.tp.proyecto1.model.clientes.Persona;
import com.tp.proyecto1.model.eventos.Evento;
import com.tp.proyecto1.model.users.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

	List<Evento> findAllByPersona(Persona persona);
	
	List<Evento> findAllByIsAbierto(Boolean estado);
	
}
