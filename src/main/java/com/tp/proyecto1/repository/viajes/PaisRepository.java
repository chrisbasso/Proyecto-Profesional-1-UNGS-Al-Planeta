package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

	Pais findByNombreIgnoreCase(String nombre);
}
