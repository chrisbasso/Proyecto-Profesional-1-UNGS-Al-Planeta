package com.tp.proyecto1.repository.pasajes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tp.proyecto1.model.pasajes.FormaDePago;

@Repository
public interface FormaDePagoRepository extends JpaRepository<FormaDePago, Long> {
		FormaDePago findByDescripcion(String descripcion);
}
//Seteo los componentes a utilizar