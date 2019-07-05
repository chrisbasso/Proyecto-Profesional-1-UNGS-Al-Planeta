package com.tp.proyecto1.repository.configuraciones;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {

}