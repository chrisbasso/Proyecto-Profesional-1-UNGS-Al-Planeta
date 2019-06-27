package com.tp.proyecto1.controllers.clientes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.Proyecto1Application;
import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.lotePunto.LotePunto;
import com.tp.proyecto1.services.LotePuntoService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.lotePuntos.PuntosClienteView;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class PuntosClienteController
{
	 private PuntosClienteView puntosClienteView;

	    @Autowired
	    private LotePuntoService lotePuntoService;

	    private ChangeHandler changeHandler;

		private Cliente cliente;
	    
	    public PuntosClienteController(Cliente cliente) {
	        Inject.Inject(this);
	        this.cliente = cliente;
	        this.puntosClienteView = new PuntosClienteView();
	        setListeners();
	        listPuntos();
	    }

	    private void setListeners() {
	        setChangeHandler(this::listPuntos);
	    }

	    private void listPuntos()
	    {
	    	System.out.println(cliente);
	    	List<LotePunto> lotesPuntos = lotePuntoService.findAllByCliente(cliente);
	         List<LotePunto> lotesPuntosActivos = new ArrayList<>();
	         List<LotePunto> lotesPuntosActivosYAcreditados = new ArrayList<>();
	         lotesPuntosActivos = lotesPuntos.stream().filter(lote -> lote.getActivo() ).collect(Collectors.toList());//deja solo los lotes  no vencidos
	         lotesPuntosActivosYAcreditados = lotesPuntosActivos.stream().filter(lote -> lote.getIsAcreditado()).collect(Collectors.toList());//deja solo los lotes no vencidos y acreditados
	         puntosClienteView.getGrid().setItems(lotesPuntosActivosYAcreditados);
	        // puntosClienteView.getGrid().setItems(lotePuntoService.findAllByCliente(cliente));
	    }

	    private void setChangeHandler(ChangeHandler h) {
	        changeHandler = h;
	    }

	    public PuntosClienteView getView(){
	        return puntosClienteView;
	    }

}
