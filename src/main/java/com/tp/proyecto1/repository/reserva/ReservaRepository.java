package com.tp.proyecto1.repository.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.reserva.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}