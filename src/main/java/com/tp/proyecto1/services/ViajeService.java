package com.tp.proyecto1.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.repository.viajes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private TipoTransporteRepository tipoTransporteRepository;

    @Autowired
    private TagDestinoRepository tagDestinoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    private static final Logger log = LoggerFactory.getLogger(ViajeService.class);

    @Transactional
    public void save(Viaje viaje){
        viajeRepository.save(viaje);
    }

    @Transactional
    public List<Viaje> findAll(){
        return this.viajeRepository.findAll();
    }

    @Transactional
    public List<Viaje> findViajes(Viaje viaje, LocalDate fechaDesde, LocalDate fechaHasta){
        List<Viaje> viajes = viajeRepository.findAll(Example.of(viaje));
        if(fechaDesde!=null){
            viajes = viajes.stream().filter(e-> e.getFechaSalida().isAfter(fechaDesde)).collect(Collectors.toList());
        }
        if(fechaHasta!=null){
            viajes = viajes.stream().filter(e-> e.getFechaSalida().isBefore(fechaHasta)).collect(Collectors.toList());
        }
        return viajes;
    }

    @Transactional
    public void delete(Viaje viaje) {
        viajeRepository.delete(viaje);
    }

    @Transactional
    public TipoTransporte createTipoTransporteIfNotExist(String tipo) {

        TipoTransporte tipoTransporte = tipoTransporteRepository.findByDescripcion(tipo);
        if (tipoTransporte == null) {
            tipoTransporte = new TipoTransporte(tipo);
            tipoTransporteRepository.save(tipoTransporte);
        }
        return tipoTransporte;
    }

    @Transactional
    public List<TipoTransporte> findAllTipoTransportes() {

        return tipoTransporteRepository.findAll();

    }

    @Transactional
    public List<Pais> findAllPaises() {

        return paisRepository.findAll();

    }

    @Transactional
    public List<Ciudad> findAllCiudades() {

        return ciudadRepository.findAll();

    }


    
    @Transactional
    public void saveTagDestino(TagDestino tag) {
    	tagDestinoRepository.save(tag);
    }
}
