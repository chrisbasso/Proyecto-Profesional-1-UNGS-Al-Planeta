package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.DestinoService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.BuscadorClientesComponent;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.utils.TipoReporte;
import com.tp.proyecto1.views.reportes.ReportesView;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class ReportesController {

	private ReportesView reportesView;

	@Autowired
	DestinoService destinoService;

	@Autowired
	UserService userService;

	@Autowired
	ClienteService clienteService;


	public ReportesController() {
		Inject.Inject(this);
		this.reportesView = new ReportesView();
		setComponents();
		setListeners();
	}

	private void setListeners() {

		reportesView.getComboTipoReporte().addValueChangeListener(e->setFiltroDinamico());
		reportesView.getBtnGenerar().addClickListener(e-> generarReporte());

	}

	private void generarReporte() {
	}

	private void setFiltroDinamico() {

		if(reportesView.getComboTipoReporte().getValue().equals(TipoReporte.VENDEDOR.name())){
			ComboBox<User> comboVendedor = new ComboBox<>("Vendedor");
			comboVendedor.setItemLabelGenerator(User::getUser);
			comboVendedor.setItems(userService.findAll());
			reportesView.setFiltroDinamico(comboVendedor);
			reportesView.setCampos();
		}
		if(reportesView.getComboTipoReporte().getValue().equals(TipoReporte.CLIENTE.name())){
			BuscadorClientesComponent buscadorClientesComponent = new BuscadorClientesComponent(new Label());
			buscadorClientesComponent.getFiltro().setLabel("Cliente");
			buscadorClientesComponent.getSearchButton().addThemeVariants(ButtonVariant.LUMO_SMALL);
			reportesView.setFiltroDinamico(buscadorClientesComponent);
			reportesView.setCampos();
		}

	}

	private void setComponents() {
	}

	public ReportesView getReportesView() {
		return reportesView;
	}

	public void setReportesView(ReportesView reportesView) {
		this.reportesView = reportesView;
	}
}
