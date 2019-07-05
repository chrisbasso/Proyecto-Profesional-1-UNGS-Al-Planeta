package com.tp.proyecto1.services;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.tp.proyecto1.repository.configuraciones.ConfiguracionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracionService {


		@Autowired
		private ConfiguracionRepository configuracionRepository;

		private static final Logger log = LoggerFactory.getLogger(ConfiguracionService.class);

		@Transactional
		public Configuracion save(Configuracion config){
			return configuracionRepository.save(config);
		}

		@Transactional
		public List<Configuracion> findAll(){
			return this.configuracionRepository.findAll();			
		}

		@Transactional
		public List<Configuracion> findConfiguraciones(Configuracion config){
			return configuracionRepository.findAll(Example.of(config));
		}

		@Transactional
		public void delete(Configuracion config) {
			configuracionRepository.delete(config);
		}

		@Transactional
		public Optional<Configuracion> findById(Long id) {		
			return configuracionRepository.findById(id);
		}
		
		@Transactional
		public String findValueByKey(String key) {		
			for(Configuracion config : findAll()) {
				if(config == null) {
					return null;
				}else {
					if(config.getClave().equals(key)) {
						return config.getValue();
					}
				}
			}
			return null;
		}
		
	    @Transactional
	    public Configuracion createConfiguracionIfNotExist(String key, String value) {
	        Configuracion config = new Configuracion(key, value);
	    	if(findValueByKey(key) != null) {
	        	return config;
	        }else {
	        	save(config);
	        	return config;
	        }	        
	    }
	}