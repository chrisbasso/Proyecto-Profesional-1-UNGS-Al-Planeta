package com.tp.proyecto1.repository.lotePuntos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;

@Repository
public interface LotePuntoRepository extends JpaRepository<LotePunto, Long> {
	
	List<LotePunto> findAllByCliente(Cliente cliente);

}
