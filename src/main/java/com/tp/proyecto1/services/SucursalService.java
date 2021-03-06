package com.tp.proyecto1.services;

import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.repository.sucursales.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SucursalService {
	@Autowired
	private SucursalRepository sucursalRepository;
	
	
	@Transactional
	public void save(Sucursal sucursal) {
		sucursalRepository.save(sucursal);
	}
	
	@Transactional
	public List<Sucursal> findAll(){
		return this.sucursalRepository.findAll();
	}

}
