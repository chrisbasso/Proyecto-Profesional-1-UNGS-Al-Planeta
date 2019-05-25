package com.tp.proyecto1.repository.sucursales;

import com.tp.proyecto1.model.sucursales.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}
