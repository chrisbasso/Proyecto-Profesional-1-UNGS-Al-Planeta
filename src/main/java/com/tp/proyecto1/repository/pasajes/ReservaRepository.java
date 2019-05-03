package com.tp.proyecto1.repository.pasajes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.pasajes.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}