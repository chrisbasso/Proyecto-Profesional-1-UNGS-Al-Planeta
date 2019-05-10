package com.tp.proyecto1.repository.eventos;

import com.tp.proyecto1.model.eventos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
