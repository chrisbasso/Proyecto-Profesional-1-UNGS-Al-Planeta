package com.tp.proyecto1.repository.viajes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Viaje;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

	List <Viaje> findByCiudad(Ciudad ciudad);
}
