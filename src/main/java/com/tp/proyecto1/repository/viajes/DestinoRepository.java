package com.tp.proyecto1.repository.viajes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Destino;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long>{

	public Destino findByCiudad(Ciudad ciudad);
}
