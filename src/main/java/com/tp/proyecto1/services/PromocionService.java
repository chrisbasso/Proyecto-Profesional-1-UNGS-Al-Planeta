package com.tp.proyecto1.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionDescuento;
import com.tp.proyecto1.model.viajes.PromocionPuntos;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.viajes.PromocionDescuentoRepository;
import com.tp.proyecto1.repository.viajes.PromocionPuntosRepository;

@Service
public class PromocionService
{

	@Autowired
	private PromocionDescuentoRepository promocionDescuentoRepository;
	
	@Autowired
	private PromocionPuntosRepository promocionPuntosRepository;
	
	private static final Logger log = LoggerFactory.getLogger(PromocionService.class);
	 
	 
	@Transactional
	public void save(Promocion promocion)
	{
		if (promocion.getClass().equals(PromocionDescuento.class))
			promocionDescuentoRepository.save((PromocionDescuento)promocion);
		else
			promocionPuntosRepository.save((PromocionPuntos)promocion);
	}	
	
	@Transactional
	public void delete(Promocion promocion)
	{
		if (promocion.getClass().equals(PromocionDescuento.class))
			promocionDescuentoRepository.delete((PromocionDescuento)promocion);
		else
			promocionPuntosRepository.delete((PromocionPuntos)promocion);
	}

	@Transactional
    public List<Promocion> findAll()
	{
        List<Promocion> ret = new ArrayList<Promocion>();
        for (PromocionDescuento promoDesc : promocionDescuentoRepository.findAll())
        	ret.add(promoDesc);
        for (PromocionPuntos promoPuntos : promocionPuntosRepository.findAll())
        	ret.add(promoPuntos);
        return ret;
    }
	
}
