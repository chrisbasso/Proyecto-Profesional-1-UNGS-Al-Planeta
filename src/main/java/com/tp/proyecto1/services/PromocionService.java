package com.tp.proyecto1.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionDescuento;
import com.tp.proyecto1.model.viajes.PromocionPuntos;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.viajes.PromocionDescuentoRepository;
import com.tp.proyecto1.repository.viajes.PromocionPuntosRepository;
import com.tp.proyecto1.repository.viajes.PromocionRepository;

@Service
public class PromocionService
{

	/*@Autowired
	private PromocionDescuentoRepository promocionDescuentoRepository;
	
	@Autowired
	private PromocionPuntosRepository promocionPuntosRepository;
	*/
	@Autowired
	private PromocionRepository promocionRepository;
	
	private static final Logger log = LoggerFactory.getLogger(PromocionService.class);
	 
	 
	@Transactional
	public void save(Promocion promocion)
	{
		promocionRepository.save(promocion);
		/*
		if (promocion.getClass().equals(PromocionDescuento.class))
			promocionDescuentoRepository.save((PromocionDescuento)promocion);
		else
			promocionPuntosRepository.save((PromocionPuntos)promocion);*/
	}	
	
	@Transactional
	public void delete(Promocion promocion)
	{
		promocionRepository.delete(promocion);
		/*
		if (promocion.getClass().equals(PromocionDescuento.class))
			promocionDescuentoRepository.delete((PromocionDescuento)promocion);
		else
			promocionPuntosRepository.delete((PromocionPuntos)promocion);*/
	}

	@Transactional
    public List<Promocion> findAll()
	{
		return promocionRepository.findAll();
		/*
        List<Promocion> ret = new ArrayList<Promocion>();
        for (PromocionDescuento promoDesc : promocionDescuentoRepository.findAll())
        	ret.add(promoDesc);
        for (PromocionPuntos promoPuntos : promocionPuntosRepository.findAll())
        	ret.add(promoPuntos);
        return ret;*/
    }

	public Collection<Promocion> findPromociones(Promocion promocionBusqueda, LocalDate vencimientoMayorA)
	{
		List<Promocion> promociones; 
		promociones = promocionRepository.findAll(Example.of(promocionBusqueda));
		if (promocionBusqueda.getTipoPromocion()==null)
		{			
			
			Promocion promocionBusqueda2 = new PromocionPuntos();
			promocionBusqueda2.setId(promocionBusqueda.getId());
			promocionBusqueda2.setNombrePromocion(promocionBusqueda.getNombrePromocion());
			promocionBusqueda2.setDescripcion(promocionBusqueda.getDescripcion());
			promocionBusqueda2.setFechaVencimiento(promocionBusqueda.getFechaVencimiento());
			promocionBusqueda2.setCodigoPromocion(promocionBusqueda.getCodigoPromocion());
			promocionBusqueda2.setDoubleValue(promocionBusqueda.getDoubleValue());
			promocionBusqueda2.setCantidadPasajes(promocionBusqueda.getCantidadPasajes());
			for (Promocion promoPuntos : promocionRepository.findAll(Example.of(promocionBusqueda2)))
				promociones.add(promoPuntos);
		}
        if(vencimientoMayorA!=null)
            promociones = promociones.stream().filter(e-> e.getFechaVencimiento().isBefore(vencimientoMayorA)).collect(Collectors.toList());
        
	   return promociones;
	}
	
}
