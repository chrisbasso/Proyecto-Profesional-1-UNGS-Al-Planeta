package com.tp.proyecto1.repository.pasajes;

import com.tp.proyecto1.model.pasajes.Venta;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
	List<Venta> findAllByViaje_FechaSalida(LocalDate fechaSalida);
}
