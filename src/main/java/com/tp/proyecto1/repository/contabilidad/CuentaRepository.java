package com.tp.proyecto1.repository.contabilidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.contabilidad.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}