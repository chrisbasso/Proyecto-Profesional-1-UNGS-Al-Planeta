package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.Pago;
import com.tp.proyecto1.repository.pasajes.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PagosService {

	@Autowired
	private PagoRepository pagoRepository;

	@Transactional
	public List<Pago> findPagos(Pago pago, LocalDate fechaDesde, LocalDate fechaHasta){
		List<Pago> pagos = pagoRepository.findAll(Example.of(pago));

		if(fechaDesde!=null){
			pagos = pagos.stream().filter(e-> e.getFechaDePago().isAfter(fechaDesde)).collect(Collectors.toList());
		}
		if(fechaHasta!=null){
			pagos = pagos.stream().filter(e-> e.getFechaDePago().isBefore(fechaHasta)).collect(Collectors.toList());
		}
		return pagos;
	}

	@Transactional
	public List<Pago> findAllPago(){

		return this.pagoRepository.findAll();
	}

}
