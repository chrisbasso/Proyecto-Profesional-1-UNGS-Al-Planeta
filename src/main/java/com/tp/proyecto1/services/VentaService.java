package com.tp.proyecto1.services;

import com.tp.proyecto1.repository.pasajes.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;


}
