package com.tp.proyecto1.repository.eventos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp.proyecto1.model.eventos.Recordatorio;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long>
{

}
