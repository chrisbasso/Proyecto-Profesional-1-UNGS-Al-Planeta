package com.tp.proyecto1.repository.contabilidad;

import com.tp.proyecto1.model.contabilidad.Cuenta;
import com.tp.proyecto1.model.contabilidad.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

	Cuenta findByNumeroCuenta(Integer numeroCuenta);
	List <Cuenta> findByTipoCuenta(TipoCuenta tipoCuenta); 
}