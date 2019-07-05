package com.tp.proyecto1.controllers.configuracion;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.configuracion.ConfiguracionView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@UIScope
public class ConfiguracionController {

	@Autowired
    private ConfiguracionService configuracionService;
    private ConfiguracionFormController configuracionFormController;
    private ConfiguracionView configuracionView;

	public ConfiguracionController () {
        Inject.Inject(this);
        this.configuracionView = new ConfiguracionView();        
        agregarBotonesEdicion();
        setListeners();
        refreshConfiguraciones();		
	}
	
    private void agregarBotonesEdicion() {
    	configuracionView.agregarColumnaVisualizar(this::createViewButton);
    	configuracionView.agregarColumnaEditar(this::createEditButton);
    }
    
    private Button createViewButton(Configuracion configuracion) {
        Button btnVer = new Button(VaadinIcon.FOLDER_OPEN_O.create(), clickEvent -> mostrarConfiguracion(configuracion));
    		return btnVer;
    }
    
    private Button createEditButton(Configuracion configuracion) {
        Button btnEditar = new Button(VaadinIcon.PENCIL.create(), clickEvent -> editarConfiguracion(configuracion));
    		return btnEditar;
    }
    
    private void mostrarConfiguracion(Configuracion configuracion) {
    	configuracionFormController = new ConfiguracionFormController();
    	configuracionFormController.getFormVisualizar(configuracion);
    	configuracionFormController.setChangeHandler(this::listConfiguraciones);    	
    }
    
    private void editarConfiguracion(Configuracion configuracion) {
    	String clave = "Configuracion: " + configuracion.getClave();
    	String valorActual = "Valor a modificar: " + configuracion.getValue();
    	Dialog dialog = new Dialog();
    	Input input = new Input();
    	    	
    	dialog.add(clave);
    	dialog.add(valorActual);
    	dialog.add(input);
    	dialog.open();
    	input.getElement().callFunction("focus");
    	dialog.addDialogCloseActionListener(e-> {
    		nuevoValor(input.getValue(), configuracion);
    		dialog.close();
    	});    	
    }
    
    private void nuevoValor(String nuevoValor, Configuracion configuracion) {
    	if(!"".equals(nuevoValor)) {
    		configuracion.setValue(nuevoValor);
    		configuracionService.save(configuracion);
    		refreshConfiguraciones();
    	}else {
    		Notification.show("Sin modificacion");
    	}
    }
    
    private void setListeners() {
    	configuracionView.setBtnAgregarListener(e->crearConfiguracion());    	
    	configuracionView.setBtnBuscarListener(e->listConfiguraciones());    	
    }
    
    private void crearConfiguracion() {
    	configuracionFormController = new ConfiguracionFormController();
    	configuracionFormController.getFormCrear();
    	configuracionFormController.setChangeHandler(this::listConfiguraciones);
    }
    
    private void listConfiguraciones() {
    	if(configuracionView.getValueClave() != null ){
    		String clave = configuracionView.getValueClave(); 
    		String valor = configuracionService.findValueByKey(clave);
    		Configuracion configuracionHallada = new Configuracion(clave, valor);
			List<Configuracion> configuraciones = new ArrayList<Configuracion>();
			configuraciones.add(configuracionHallada);
    		configuracionView.cargarConfiguraciones(configuraciones);
    	}else {
	        Configuracion configuracionBusqueda = new Configuracion();
	        if(checkFiltros()){
	            setParametrosBusqueda(configuracionBusqueda);
	            configuracionView.cargarConfiguraciones(configuracionService.findConfiguraciones(configuracionBusqueda));
	        }else {
	            configuracionView.cargarConfiguraciones(configuracionService.findAll());
	        }
    	}
    }

    private void setParametrosBusqueda(Configuracion configuracionBusqueda) {
        if(configuracionView.getValueClave() != null){
            configuracionBusqueda.setClave(configuracionView.getValueClave());
        }
    }

    private boolean checkFiltros() {
        return configuracionView.getValueClave()==null;
    }
        
    private void refreshConfiguraciones() {
    	List<Configuracion> configuraciones = configuracionService.findAll();
    	configuracionView.cargarConfiguraciones(configuraciones);
    }
    
 	public ConfiguracionView getConfiguracionView(){
        return configuracionView;
    }
}