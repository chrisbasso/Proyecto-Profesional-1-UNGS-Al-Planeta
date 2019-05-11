package com.tp.proyecto1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.viajes.Destino;
import com.tp.proyecto1.repository.viajes.DestinoRepository;

@Service
public class DestinoService {
	
	
	@Autowired
	DestinoRepository destinoRepository;

	@Transactional
	public void save(Destino destino){
		destinoRepository.save(destino);
	}

	@Transactional
	public List<Destino> findAll(){
		return this.destinoRepository.findAll();
	}
	
	@Transactional
	public void delete(Destino destino)
	{
		destinoRepository.delete(destino);
	}

}
