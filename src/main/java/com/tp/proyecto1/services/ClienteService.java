package com.tp.proyecto1.services;

import com.tp.proyecto1.model.Cliente;
import com.tp.proyecto1.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Transactional
	public void save(Cliente cliente){
		clienteRepository.save(cliente);
	}

	@Transactional
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}

	@Transactional
	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
	}
}

