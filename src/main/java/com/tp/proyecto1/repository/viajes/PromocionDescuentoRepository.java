package com.tp.proyecto1.repository.viajes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tp.proyecto1.model.viajes.PromocionDescuento;

@Repository
public interface PromocionDescuentoRepository extends JpaRepository<PromocionDescuento, Long> {


}
