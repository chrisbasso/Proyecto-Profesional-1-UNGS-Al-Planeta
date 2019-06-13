package com.tp.proyecto1.repository.contabilidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

	Cuenta findByNumeroCuenta(Integer numeroCuenta);
	List <Cuenta> findByTipoCuenta(TipoCuenta tipoCuenta); 
}