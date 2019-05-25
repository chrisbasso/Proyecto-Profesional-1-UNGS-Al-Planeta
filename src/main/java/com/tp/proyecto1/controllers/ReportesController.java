package com.tp.proyecto1.controllers;

import com.tp.proyecto1.services.ClienteService;
import com.tp.proyecto1.services.DestinoService;
import com.tp.proyecto1.services.UserService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reportes.ReportesView;
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
	}

	public ReportesView getReportesView() {
		return reportesView;
	}

	public void setReportesView(ReportesView reportesView) {
		this.reportesView = reportesView;
	}
}
