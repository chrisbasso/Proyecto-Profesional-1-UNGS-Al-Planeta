package com.tp.proyecto1.repository.contabilidad;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.contabilidad.Asiento;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {
	List <Asiento> findAllByCabecera_FechaContabilizacionBetween(LocalDate from, LocalDate to); 
}