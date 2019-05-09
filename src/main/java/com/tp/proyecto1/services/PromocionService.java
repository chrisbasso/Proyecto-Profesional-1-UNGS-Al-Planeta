package com.tp.proyecto1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionPuntos;
import com.tp.proyecto1.repository.viajes.PromocionPuntosRepository;
import com.tp.proyecto1.repository.viajes.PromocionRepository;

@Service
public class PromocionService
{

	@Autowired
	private PromocionRepository promocionRepository;
	
	private static final Logger log = LoggerFactory.getLogger(PromocionService.class);
	 
	 
	@Transactional
	public void save(Promocion promocion)
	{
		promocionRepository.save(promocion);
	}
	
	@Transactional
	public void delete(Promocion promocion)
	{
		promocionRepository.delete(promocion);
	}
	
}
