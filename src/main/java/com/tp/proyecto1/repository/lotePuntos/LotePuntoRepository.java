package com.tp.proyecto1.repository.lotePuntos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.lotePunto.LotePunto;

@Repository
public interface LotePuntoRepository extends JpaRepository<LotePunto, Long> {

}
