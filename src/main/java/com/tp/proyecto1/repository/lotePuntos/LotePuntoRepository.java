package com.tp.proyecto1.repository.lotePuntos;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotePuntoRepository extends JpaRepository<LotePunto, Long> {
	
	List<LotePunto> findAllByCliente(Cliente cliente);

}
