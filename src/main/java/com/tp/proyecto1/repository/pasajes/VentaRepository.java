package com.tp.proyecto1.repository.pasajes;

import com.tp.proyecto1.model.pasajes.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
	List<Venta> findAllByViaje_FechaSalida(LocalDate fechaSalida);

	List<Venta> findAllByCliente_Id(Long idCliente);
}
