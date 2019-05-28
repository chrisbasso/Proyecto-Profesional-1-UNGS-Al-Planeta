package com.tp.proyecto1.repository.viajes;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.model.viajes.Viaje;
//import com.tp.proyecto1.model.viajes.PromocionPuntos;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long>{

	
	Set<Promocion> findByViajesAfectados(Viaje viaje);
	
	Set<Promocion> findByDestinosAfectados(Destino destino);
}
