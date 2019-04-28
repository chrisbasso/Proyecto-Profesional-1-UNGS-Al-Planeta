package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.TipoTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoTransporteRepository extends JpaRepository<TipoTransporte, Long> {

    TipoTransporte findByDescripcion(String descripcion);
}
