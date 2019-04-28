package com.tp.proyecto1.services;

import com.tp.proyecto1.model.viajes.TipoTransporte;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.repository.viajes.TipoTransporteRepository;
import com.tp.proyecto1.repository.viajes.ViajeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private TipoTransporteRepository tipoTransporteRepository;

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
    public List<Viaje> findViajes(Viaje viaje){
        return viajeRepository.findAll(Example.of(viaje));
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
}

