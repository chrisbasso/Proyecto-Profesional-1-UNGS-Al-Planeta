package com.tp.proyecto1.repository.contabilidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.contabilidad.Asiento;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {

}