package com.tp.proyecto1.services;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Transaccion;
import com.tp.proyecto1.repository.pasajes.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransaccionService {

	@Autowired
	TransaccionRepository transaccionRepository;

	@Transactional
	public boolean verificarTransaccionCliente(Cliente cliente){
		List<Transaccion> transacciones =  transaccionRepository.findAllByCliente(cliente);
		for (Transaccion transaccion : transacciones) {
			if(transaccion.isActivo()) {
				return true;
			}
		}
		return false;
	}
}
