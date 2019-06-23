package com.tp.proyecto1.services;

import com.tp.proyecto1.model.pasajes.FormaDePago;
import com.tp.proyecto1.model.pasajes.PasajeVenta;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.repository.pasajes.PasajeVentaRepository;
import com.tp.proyecto1.repository.pasajes.FormaDePagoRepository;

import java.util.List;

import com.tp.proyecto1.repository.pasajes.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService {

    @Autowired
    private PasajeVentaRepository pasajeVentaRepository;
    
    @Autowired
    private FormaDePagoRepository formaDePagoRepository;

    @Autowired
    private VentaRepository ventaRepository;
    
    private static final Logger log = LoggerFactory.getLogger(VentaService.class);
    
    @Transactional
    public void save(PasajeVenta pasajeVenta){
        pasajeVentaRepository.save(pasajeVenta);
    }

    @Transactional
    public void save(Venta venta){
        ventaRepository.save(venta);
    }
    
    @Transactional
    public List<PasajeVenta> findAllPasajeVentas(){
    	return this.pasajeVentaRepository.findAll();
    }

    @Transactional
    public List<PasajeVenta> findPasajesVentas(PasajeVenta pasajeVenta){
        List<PasajeVenta> pasajeVentas = pasajeVentaRepository.findAll(Example.of(pasajeVenta));
        return pasajeVentas;
    }

    @Transactional
    public List<Venta> findAllVentas(){

        return this.ventaRepository.findAll();
    }

    @Transactional
    public List<Venta> findVentas(Venta venta){
        List<Venta> ventas = ventaRepository.findAll(Example.of(venta));
        return ventas;
    }

    @Transactional
    public void delete(PasajeVenta pasajeVenta) {
        pasajeVentaRepository.delete(pasajeVenta);
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
    
	@Transactional
	public List <Venta> findByIdCliente(Long idCliente){
		return ventaRepository.findAllByCliente_Id(idCliente);
	}
}
