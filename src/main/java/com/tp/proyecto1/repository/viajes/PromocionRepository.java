package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long>{

	
	Set<Promocion> findByViajesAfectados(Viaje viaje);
	
	Set<Promocion> findByCiudadesAfectadas(Ciudad ciudad);
}
