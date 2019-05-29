package com.tp.proyecto1.controllers.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.services.LotePuntoService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.lotePuntos.PuntosClienteWindow;

public class HistorialPuntosClienteWindowController {

	@Autowired
	private LotePuntoService lotePuntoService;

	private PuntosClienteWindow view;

	private Cliente cliente;

	public HistorialPuntosClienteWindowController(Cliente cliente) {
		Inject.Inject(this);
		this.view = new PuntosClienteWindow();
		this.cliente = cliente;
		setComponents();
		listarLotePuntos();
		setListeners();

	}

	private void listarLotePuntos() {
		this.view.getGrid().setItems(lotePuntoService.findAllByCliente(cliente));

	}

	private void setListeners() {
		this.view.getBtnClose().addClickListener(e->view.close());
	
	}


	private void setComponents() {
	}

	public PuntosClienteWindow getView() {
		return view;
	}

	public void setView(PuntosClienteWindow view) {
		this.view = view;
	}

}
