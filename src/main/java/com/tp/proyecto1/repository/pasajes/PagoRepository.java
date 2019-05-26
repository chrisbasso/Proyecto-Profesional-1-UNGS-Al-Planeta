package com.tp.proyecto1.repository.pasajes;

import com.tp.proyecto1.model.pasajes.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
