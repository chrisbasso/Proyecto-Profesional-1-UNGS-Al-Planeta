package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.Continente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinenteRepository extends JpaRepository<Continente, Long> {

	Continente findByNombre(String nombre);
}