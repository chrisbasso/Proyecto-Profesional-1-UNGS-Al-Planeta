package com.tp.proyecto1.services;

import com.tp.proyecto1.model.viajes.TagDestino;
import com.tp.proyecto1.repository.viajes.TagDestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagDestinoService {
	
	@Autowired
	TagDestinoRepository tagDestinoRepository;

	@Transactional
	public void save(TagDestino tagDestino){
		tagDestinoRepository.save(tagDestino);
	}

	@Transactional
	public List<TagDestino> findAll(){
		return this.tagDestinoRepository.findAll();
	}
	
	@Transactional
	public void delete(TagDestino tagDestino)
	{
		tagDestinoRepository.delete(tagDestino);
	}
}
