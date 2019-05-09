package com.tp.proyecto1.repository.viajes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionPuntos;

@Repository
public interface PromocionPuntosRepository extends JpaRepository<PromocionPuntos, Long> {


}
