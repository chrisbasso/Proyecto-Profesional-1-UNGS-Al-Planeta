package com.tp.proyecto1.controllers.clientes;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.services.ReservaService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.reserva.ReservaClienteView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class ReservasClienteController
{
	private ReservaClienteView reservaClientesView;
	
	
	private ReservasClienteFormController reservasClienteFormController;

    @Autowired
    private ReservaService reservaService;

    private ChangeHandler changeHandler;

	private Cliente cliente;
    
    public ReservasClienteController(Cliente cliente) {
        Inject.Inject(this);
        this.cliente = cliente;
        this.reservaClientesView = new ReservaClienteView();
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

    public ReservaClienteView getView(){
        return reservaClientesView;
    }
    
    private Button createDetailsButton(Reserva reserva)
    {
    	return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
    		System.out.println("asd");
    		reservasClienteFormController = new ReservasClienteFormController(reserva.getViaje());
            reservasClienteFormController.getView(reserva).open();
        });
    }

}
