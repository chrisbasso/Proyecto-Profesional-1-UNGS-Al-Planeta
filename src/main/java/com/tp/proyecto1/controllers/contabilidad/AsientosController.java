package com.tp.proyecto1.controllers.contabilidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.model.contabilidad.Cabecera;
import com.tp.proyecto1.model.contabilidad.Posicion;
import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.AsientoService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.ConfirmationDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.contabilidad.AsientoView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class AsientosController {

    @Autowired
    private AsientoService asientoService;
    @Autowired
    private UserService userService;
    private AsientoFormController asientoFormController;
    private AsientoView asientoView;
    private CuentaController cuentaController;
    private ChangeHandler changeHandler;
    
    public AsientosController() {
        Inject.Inject(this);
        this.asientoView = new AsientoView();        
        agregarBotonesEdicion();
        setListeners();
        refreshAsientos();
    }

    private void agregarBotonesEdicion() {
    	asientoView.agregarColumnaVisualizar(this::createViewButton);
        asientoView.agregarColumnaBorrado(this::createDeleteButton);
    }
    
    private Button createViewButton(Asiento asiento) {
        Button btnVer = new Button(VaadinIcon.FOLDER_OPEN_O.create(), clickEvent -> mostrarAsiento(asiento));
    		return btnVer;
    }
    
    private void mostrarAsiento(Asiento asiento) {
    	asientoFormController = new AsientoFormController();
    	asientoFormController.getFormVisualizar(asiento);
    	asientoFormController.setChangeHandler(this::listAsientos);    	
    }
    
    private Button createDeleteButton(Asiento asiento) {
        Button btnBorrar = new Button(VaadinIcon.TRASH.create(), clickEvent -> borrarAsiento(asiento));
    		if(asiento.getAnulado()){
    			btnBorrar.setEnabled(false);
    		}
    		return btnBorrar;
    }
    
    private void borrarAsiento(Asiento asiento) {
    	String mensaje = "¿Realmente desea anular el asiento " 
    						+ asiento.getId()
    						+ ", del "
    						+ asiento.getFechaContabilizacion()
    						+ "?";
		ConfirmationDialog confirmationDialog = new ConfirmationDialog(mensaje);
		confirmationDialog.getConfirmButton().addClickListener(event -> {
			asiento.setAnulado(Proyecto1Application.logUser);						
			asientoService.save(asiento);			
			Notification.show("Asiento anulado");
			changeHandler.onChange();
		});
		confirmationDialog.open();
	}

    private void setListeners() {
    	asientoView.setBtnAgregarListener(e->crearAsiento());
    	asientoView.setBtnCuentasListener(e->abmCuentas());
    	asientoView.setBtnBuscarListener(e->listAsientos());    	
    }
    
    private void crearAsiento() {
    	asientoFormController = new AsientoFormController();
    	asientoFormController.getFormCrear();
    	asientoFormController.setChangeHandler(this::listAsientos);
    }
    
    private void listAsientos() {
    	if(asientoView.getValueNumeroAsiento() != null ){
    		Optional asiento = asientoService.findById(asientoView.getValueNumeroAsiento());
    		if(asiento.isPresent()) {
    			List<Asiento> asientos = new ArrayList<Asiento>();
    			asientos.add((Asiento)asiento.get());
        		asientoView.cargarAsientos(asientos);	
    		}    		
    	}else {
	        Asiento asientoBusqueda = new Asiento();
	        if(checkFiltros()){
	            setParametrosBusqueda(asientoBusqueda);
	            asientoView.cargarAsientos(asientoService.findAsientos(asientoBusqueda));
	        }else {
	            asientoView.cargarAsientos(asientoService.findAll());
	        }
    	}
    }

    private void setParametrosBusqueda(Asiento asientoBusqueda) {
    	Cabecera cabecera = new Cabecera();
    	asientoBusqueda.setCabecera(cabecera);
    	List<Posicion> posiciones = new ArrayList<>();
    	asientoBusqueda.setPosiciones(posiciones);
        if(asientoView.getValueNumeroAsiento() != null){
            asientoBusqueda.setId(asientoView.getValueNumeroAsiento());
        }
        if(asientoView.getValueFecha()!=null){
            asientoBusqueda.setFechaContabilizacion(asientoView.getValueFecha());
        }
        if(asientoView.getValueUsuario()!=null){
        	User usuario = userService.getUserById(asientoView.getValueUsuario());
            asientoBusqueda.setUsuario(usuario);
        }
    }

    private boolean checkFiltros() {
        return asientoView.getValueUsuario()==null || asientoView.getValueNumeroAsiento() == null  ||
                asientoView.getValueFecha() == null;
    }
    
    private void abmCuentas(){
    	cuentaController = new CuentaController();
    	cuentaController.getFormCrear();    	
    }
    
    private void refreshAsientos() {
    	List<Asiento> asientos = asientoService.findAll();
    	asientoView.cargarAsientos(asientos);
    }
    
 	public AsientoView getAsientosView(){
        return asientoView;
    }
}