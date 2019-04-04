package com.tp.proyecto1.Repository;

import com.tp.proyecto1.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	List<Persona> findByLastNameStartsWithIgnoreCase(String lastName);
}