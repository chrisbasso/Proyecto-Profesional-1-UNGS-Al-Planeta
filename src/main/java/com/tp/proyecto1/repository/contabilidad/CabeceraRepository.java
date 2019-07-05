package com.tp.proyecto1.repository.contabilidad;

import com.tp.proyecto1.model.contabilidad.Cabecera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabeceraRepository extends JpaRepository<Cabecera, Long> {

}