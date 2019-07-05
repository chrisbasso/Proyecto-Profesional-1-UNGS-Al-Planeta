package com.tp.proyecto1.controllers.clientes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.services.LotePuntoService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.lotePuntos.PuntosClienteWindow;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		 List<LotePunto> lotesPuntos = lotePuntoService.findAllByCliente(cliente);
         List<LotePunto> lotesPuntosActivos = new ArrayList<>();
         List<LotePunto> lotesPuntosActivosYAcreditados = new ArrayList<>();
         lotesPuntosActivos = lotesPuntos.stream().filter(lote -> lote.getActivo() ).collect(Collectors.toList());//deja solo los lotes  no vencidos
         lotesPuntosActivosYAcreditados = lotesPuntosActivos.stream().filter(lote -> lote.getIsAcreditado()).collect(Collectors.toList());//deja solo los lotes no vencidos y acreditados
         this.view.getGrid().setItems(lotesPuntosActivosYAcreditados);
         //this.view.getGrid().setItems(lotePuntoService.findAllByCliente(cliente));

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
