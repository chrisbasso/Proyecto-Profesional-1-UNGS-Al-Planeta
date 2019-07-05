package com.tp.proyecto1.controllers.configuracion;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.configuracion.ConfiguracionForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class ConfiguracionFormController {

	@Autowired
	private ConfiguracionService configService;
	
	private ConfiguracionForm configuracionForm;
	private ChangeHandler changeHandler;
	
	public ConfiguracionFormController() {
		Inject.Inject(this);
	}
	
	public void getFormCrear() {
		configuracionForm = new ConfiguracionForm ();
		setListeners();
		configuracionForm.open();
	}
	
	public void getFormVisualizar(Configuracion configuracion){
		configuracionForm = new ConfiguracionForm (configuracion);
		configuracionForm.open();
	}
	
	private void setListeners() {
		configuracionForm.setBtnGuardarListener(e->guardar());
		configuracionForm.setClaveListener(e->validarHabilitarGuardado());
		configuracionForm.setValorListener(e->validarHabilitarGuardado());
	}
	
	private void guardar() {
		if(validarDatosCompletos()) {
			String clave = configuracionForm.getClave();
			String valor = configuracionForm.getValor();
			Configuracion configuracion = new Configuracion(clave, valor);
			if(configService.save(configuracion) != null) {
				mensajeGuardado();	
				changeHandler.onChange();
				configuracionForm.close();
			}else {
				mensajeErrorGuardado();
			}				
		}else {
			Notification.show("Complete los datos para guardar");
		}		
	}
	
	private void mensajeGuardado() {
		Notification.show("Configuracion guardada con éxito.");		
	}
	
	private void mensajeErrorGuardado() {
		Notification.show("Ha ocurrido un error con la base de datos.");		
	}
	
	private boolean validarDatosCompletos() {
		boolean notNull = (configuracionForm.getClave() != null && configuracionForm.getValor() != null); 
		boolean notVacio = (!"".equals(configuracionForm.getClave()) && !"".equals(configuracionForm.getValor()));
		return (notNull && notVacio);			
	}
	
	private void validarHabilitarGuardado(){
		if(validarDatosCompletos()) {
			configuracionForm.habilitarBtnGuardar();
		}else {
			configuracionForm.deshabilitarBtnGuardar();
		}
	}
	
	public void setChangeHandler(ChangeHandler ch) {
		changeHandler = ch;		
	}
}