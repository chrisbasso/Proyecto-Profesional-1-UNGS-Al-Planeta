package com.tp.proyecto1.repository.pasajes;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	List<Reserva> findAllByViaje_FechaSalida(LocalDate fechaSalida);
	List<Reserva> findAllByCliente_Id(Long idCliente);
	List<Reserva> findAllByViaje_Id(Long idViaje);
	List<Reserva> findAllByViaje_IdAndCliente_Id(Long idViaje, Long idCliente);
	List<Reserva> findAllByViaje_Origen(Ciudad ciudad);
	List<Reserva> findAllByFecha(LocalDate fecha);
}