package com.tp.proyecto1.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.tp.proyecto1.repository.configuraciones.ConfiguracionRepository;

@Service
public class ConfiguracionService {
		private static final Logger logger = Logger.getLogger(ConfiguracionService.class.getName()); 
		
		static void logHandler() throws SecurityException, IOException {		 
			FileHandler fileTxt = new FileHandler("LogConfServ.txt");
			SimpleFormatter formatterTxt = new SimpleFormatter();
	        fileTxt.setFormatter(formatterTxt);
	        logger.addHandler(fileTxt);			
		}
		
		@Autowired
		private ConfiguracionRepository configuracionRepository;

		@Transactional
		public void save(Configuracion config){
			configuracionRepository.save(config);
		}

		@Transactional
		public List<Configuracion> findAll(){
			return configuracionRepository.findAll();
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
			try {
				logHandler();
			} catch (SecurityException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Configuracion config : findAll()) {
				if(config.getClave().equals(key)) {
					
					return config.getValor();
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
