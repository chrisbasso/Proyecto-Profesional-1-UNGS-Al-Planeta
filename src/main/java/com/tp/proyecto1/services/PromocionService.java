package com.tp.proyecto1.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionPuntos;
import com.tp.proyecto1.model.viajes.Viaje;
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
		if (promocion.getViajesAfectados().size()>0 && promocion.getCiudadesAfectadas().size()>0)
			throw new IllegalArgumentException("No puede aver una promocion que afecte viajes y destinos a la vez.");
		promocionRepository.save(promocion);
	}	
	
	@Transactional
	public void delete(Promocion promocion)
	{
		promocionRepository.delete(promocion);
	}

	@Transactional
    public List<Promocion> findAll()
	{
		return promocionRepository.findAll();
    }
	
	@Transactional
	public Set<Promocion> findByViajesAfectados(Viaje viaje)
	{
		return promocionRepository.findByViajesAfectados(viaje);
	}
	
	@Transactional
	public Set<Promocion> findByCiudadesAfectadas(Ciudad ciudad)
	{
		return promocionRepository.findByCiudadesAfectadas(ciudad);
	}
	
	@Transactional
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
			promocionBusqueda2.setActivo(promocionBusqueda.isActivo());
			for (Promocion promoPuntos : promocionRepository.findAll(Example.of(promocionBusqueda2)))
				promociones.add(promoPuntos);
		}
        if(vencimientoMayorA!=null)
            promociones = promociones.stream().filter(e-> e.getFechaVencimiento().isBefore(vencimientoMayorA)).collect(Collectors.toList());
        
	   return promociones;
	}
	
}
