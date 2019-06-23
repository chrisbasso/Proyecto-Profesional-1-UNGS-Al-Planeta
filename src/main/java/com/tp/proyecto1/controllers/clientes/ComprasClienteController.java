package com.tp.proyecto1.controllers.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.services.VentaService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.ventas.VentaClienteView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;

@Controller
@UIScope
public class ComprasClienteController 
{
	private VentaClienteView reservaClientesView;
	
	private ComprasClienteFormController comprasClienteFormController;

    @Autowired
    private VentaService reservaService;

    private ChangeHandler changeHandler;

	private Cliente cliente;
    
    public ComprasClienteController(Cliente cliente) {
        Inject.Inject(this);
        this.cliente = cliente;
        this.reservaClientesView = new VentaClienteView();
        setListeners();
        setComponents();
        listPuntos();
    }

    private void setComponents()
	{
    	this.reservaClientesView.getGrid().addComponentColumn(this::createDetailsButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
	}

	private void setListeners() {
        setChangeHandler(this::listPuntos);
    }

    private void listPuntos()
    {
    	System.out.println(cliente);
         reservaClientesView.getGrid().setItems(reservaService.findByIdCliente(cliente.getId()));
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public VentaClienteView getView(){
        return reservaClientesView;
    }
    
    private Button createDetailsButton(Venta venta)
    {
    	return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
    		comprasClienteFormController = new ComprasClienteFormController(venta.getViaje());
    		comprasClienteFormController.setComponentsValues(venta);
    		comprasClienteFormController.getView().open();
        });
    }
    
    

}
