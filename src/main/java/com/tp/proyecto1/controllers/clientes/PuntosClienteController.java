package com.tp.proyecto1.controllers.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

	    public PuntosClienteController() {
	        Inject.Inject(this);
	        this.puntosClienteView = new PuntosClienteView();
	        setListeners();
	        listPuntos();
	    }

	    private void setListeners() {
	        setChangeHandler(this::listPuntos);
	    }

	    private void listPuntos()
	    {

	         puntosClienteView.getGrid().setItems(lotePuntoService.findAllByCliente(null));
	    }

	    private void setChangeHandler(ChangeHandler h) {
	        changeHandler = h;
	    }

	    public PuntosClienteView getView(){
	        return puntosClienteView;
	    }

}
