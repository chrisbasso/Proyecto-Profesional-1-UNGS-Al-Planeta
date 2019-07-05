package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.repository.lotePuntos.LotePuntoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LotePuntoService
{

	@Autowired
	private LotePuntoRepository lotePuntoRepository;
	
	private static final Logger log = LoggerFactory.getLogger(LotePuntoService.class);
	 
	 
	@Transactional
	public void save(LotePunto puntos)
	{
		lotePuntoRepository.save(puntos);
	}
	
	@Transactional
	public void delete(LotePunto puntos)
	{
		lotePuntoRepository.delete(puntos);
	}
	
	@Transactional
	public List<LotePunto> findAllByCliente(Cliente cliente)
	{
		return lotePuntoRepository.findAllByCliente(cliente);
	}
	
}
