package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.repository.clientes.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
	public List<Cliente> findClientes(Cliente cliente){
		return clienteRepository.findAll(Example.of(cliente));
	}

	@Transactional
	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

	@Transactional
	public Optional<Cliente> findById(Long id) {		
		return clienteRepository.findById(id);
	}
}

