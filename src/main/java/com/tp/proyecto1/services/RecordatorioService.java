package com.tp.proyecto1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.eventos.Recordatorio;
import com.tp.proyecto1.repository.eventos.RecordatorioRepository;

@Service
public class RecordatorioService
{
	
	@Autowired
	RecordatorioRepository recordatorioRepository;

	@Transactional
	public void save(Recordatorio recordatorio)
	{
		recordatorioRepository.save(recordatorio);
	}
}
