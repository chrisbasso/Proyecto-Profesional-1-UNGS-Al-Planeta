package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}
