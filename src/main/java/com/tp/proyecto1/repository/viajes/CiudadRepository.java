package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
}
