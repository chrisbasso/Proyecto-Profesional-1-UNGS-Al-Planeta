package com.tp.proyecto1.repository.pasajes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.pasajes.Reserva;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	List<Reserva> findAllByViaje_FechaSalida(LocalDate fechaSalida);

}