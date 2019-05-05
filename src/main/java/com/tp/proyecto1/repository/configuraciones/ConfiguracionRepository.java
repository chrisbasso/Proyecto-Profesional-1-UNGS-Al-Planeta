package com.tp.proyecto1.repository.configuraciones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.configuraciones.Configuracion;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {

}