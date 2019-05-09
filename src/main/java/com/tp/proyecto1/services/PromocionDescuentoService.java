package com.tp.proyecto1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionDescuento;
import com.tp.proyecto1.model.viajes.PromocionPuntos;
import com.tp.proyecto1.repository.viajes.PromocionDescuentoRepository;
import com.tp.proyecto1.repository.viajes.PromocionPuntosRepository;

@Service
public class PromocionDescuentoService
{

	@Autowired
	private PromocionDescuentoRepository promocionRepository;
	
	private static final Logger log = LoggerFactory.getLogger(PromocionDescuentoService.class);
	 
	 
	@Transactional
	public void save(PromocionDescuento promocion)
	{
		promocionRepository.save(promocion);
	}
	
	@Transactional
	public void delete(PromocionDescuento promocion)
	{
		promocionRepository.delete(promocion);
	}
	
}
