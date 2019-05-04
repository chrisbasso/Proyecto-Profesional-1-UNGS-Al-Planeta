package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.repository.pasajes.VentaRepository;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private FormaDePagoRepository formaDePagoRepository;
    
    private static final Logger log = LoggerFactory.getLogger(VentaService.class);
    
    @Transactional
    public void save(Venta venta){
        ventaRepository.save(venta);
    }
    
    @Transactional
    public List<Venta> findAll(){
    	return this.ventaRepository.findAll();
    }

    @Transactional
    public List<Venta> findViajes(Venta viaje, LocalDate fechaDesde, LocalDate fechaHasta){
        List<Venta> ventas = ventaRepository.findAll(Example.of(viaje));

        return ventas;
    }

    @Transactional
    public void delete(Venta venta) {
        ventaRepository.delete(venta);
    }

    @Transactional
    public FormaDePago createFormaDePagoIfNotExist(String forma) {

        FormaDePago formaPago = formaDePagoRepository.findByDescripcion(forma);
        if (formaPago == null) {
            formaPago = new FormaDePago(forma);
           formaDePagoRepository.save(formaPago);
        }
        return formaPago;
    }

    @Transactional
    public List<FormaDePago> findAllFomaDePagos() {

        return formaDePagoRepository.findAll();

    }
}
