package com.tp.proyecto1.controllers.viajes;

import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Continente;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Provincia;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.GenericDialog;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.viajes.DestinosForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class DestinosFormController {

	private DestinosForm destinosForm;

	@Autowired
	private ViajeService viajeService;

	public DestinosFormController() {
		Inject.Inject(this);
		this.destinosForm = new DestinosForm();
		setListeners();
		setComponents();
	}

	private void setComponents() {
		destinosForm.getComboContinente().setItems(viajeService.findAllContinente());
	}

	private void setListeners() {
		destinosForm.getBtnPais().addClickListener(e->guardarPais());
		destinosForm.getBtnProvincia().addClickListener(e->guardarProvincia());
		destinosForm.getBtnCiudad().addClickListener(e->guardarCiudad());
		destinosForm.getComboContinente().addValueChangeListener(e -> setComboPaises(e.getValue(),destinosForm.getComboPais()));
		destinosForm.getComboPais().addValueChangeListener(e -> setComboProvincias(e.getValue(),destinosForm.getComboProvincia()));
	}

	private void guardarCiudad() {

		Ciudad ciudad = new Ciudad(destinosForm.getTxtCiudad().getValue());
		ciudad.setProvincia(destinosForm.getComboProvincia().getValue());

		if(!viajeService.saveCiudad(ciudad)){
			GenericDialog genericDialog = new GenericDialog("Ya existe la ciudad que intenta agregar");
		}else{
			GenericDialog genericDialog = new GenericDialog("Ciudad agregada");
		}

	}

	private void guardarProvincia() {

		Provincia provincia = new Provincia(destinosForm.getTxtProvincia().getValue());
		provincia.setPais(destinosForm.getComboPais().getValue());

		if(!viajeService.saveProvincia(provincia)){
			GenericDialog genericDialog = new GenericDialog("Ya existe la provincia que intenta agregar");
		}else{
			GenericDialog genericDialog = new GenericDialog("Provincia agregada");
			destinosForm.getComboProvincia().clear();
			destinosForm.getComboContinente().clear();
			destinosForm.getComboContinente().setItems(viajeService.findAllContinente());

		}


	}

	private void guardarPais() {

		Pais pais = new Pais(destinosForm.getTxtPais().getValue());
		pais.setContinente(destinosForm.getComboContinente().getValue());

		if(!viajeService.savePais(pais)){
			GenericDialog genericDialog = new GenericDialog("Ya existe el país que intenta agregar");
		}else{
			GenericDialog genericDialog = new GenericDialog("País agregado");
			destinosForm.getComboPais().clear();
			destinosForm.getComboContinente().clear();
			destinosForm.getComboContinente().setItems(viajeService.findAllContinente());
		}

	}

	private void setComboPaises(Continente continente, ComboBox<Pais> comboPaises) {
		comboPaises.clear();
		if (continente != null) {
			comboPaises.setItems(continente.getPaises());
		}
	}
	private void setComboProvincias(Pais pais, ComboBox<Provincia> comboProvincia) {
		comboProvincia.clear();
		if (pais != null) {
			comboProvincia.setItems(pais.getProvincias());
		}
	}

	public DestinosForm getDestinosForm() {
		return destinosForm;
	}

	public void setDestinosForm(DestinosForm destinosForm) {
		this.destinosForm = destinosForm;
	}
}
