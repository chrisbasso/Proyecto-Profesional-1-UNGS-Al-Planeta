package com.tp.proyecto1.repository.contabilidad;

import com.tp.proyecto1.model.contabilidad.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {
	List <Asiento> findAllByCabecera_FechaContabilizacionBetween(LocalDate from, LocalDate to); 
}