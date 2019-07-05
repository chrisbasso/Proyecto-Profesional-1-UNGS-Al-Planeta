package com.tp.proyecto1.controllers.promociones;

import com.tp.proyecto1.model.viajes.Promocion;
import com.tp.proyecto1.model.viajes.PromocionDescuento;
import com.tp.proyecto1.model.viajes.PromocionPuntos;
import com.tp.proyecto1.services.PromocionService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.promociones.PromocionView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@UIScope
public class PromocionesController {

    private PromocionView promocionView;

    @Autowired
    private PromocionService promocionService;

    private PromocionFormController promocionFormController;
    
    private ChangeHandler changeHandler;

    public PromocionesController() {
        Inject.Inject(this);
        this.promocionView = new PromocionView();
        setListeners();
        setComponents();
        listPromociones();
    }

    private void setComponents() {
       this.promocionView.getGrid().addComponentColumn(this::createEditButton).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);
    }

    private void setListeners() {
        setChangeHandler(this::listPromociones);
        promocionView.getNewPromocionButton().addClickListener(e-> openNewPromocionForm());
        promocionView.getSearchButton().addClickListener(e-> listPromociones());
    }

	private void openNewPromocionForm() {
        promocionFormController = new PromocionFormController();
        promocionFormController.getPromocionForm().open();
        promocionFormController.setChangeHandler(this::listPromociones);
    }
    
    
    private Button createEditButton(Promocion promocion) {
        return new Button(VaadinIcon.EDIT.create(), clickEvent -> {
            promocionFormController = new PromocionFormController();
            promocionFormController.setComponentsValues(promocion);
            promocionFormController.getPromocionForm().open();
            promocionFormController.setChangeHandler(this::listPromociones);
        });
    }

    private void listPromociones() {

        if(checkFiltros())
        {
        	System.out.println("filtros On");
            promocionView.getGrid().setItems(promocionService.findPromociones(setPromocionBusqueda(), promocionView.getVencimientoMenorAFilter().getValue()));
        }
        else
        {
         promocionView.getGrid().setItems(promocionService.findAll());
       }
    }

    private Promocion setPromocionBusqueda()
    {
    	Promocion promocionBusqueda;
    	
        if(!promocionView.getTipoPromoFilter().isEmpty())
        {
        	if (promocionView.getTipoPromoFilter().getValue().equals("Puntos"))
        	{
        		promocionBusqueda = new PromocionPuntos();
        		promocionBusqueda.setTipoPromocion("Puntos");
        	}
        	else
        	{
        		promocionBusqueda = new PromocionDescuento();
        		promocionBusqueda.setTipoPromocion("Descuento");
        	}
        }
        else
        {
        	promocionBusqueda = new PromocionDescuento();
        }
    	
        if(!promocionView.getIdFilter().isEmpty())
            promocionBusqueda.setId(promocionView.getIdFilter().getValue().longValue());
        
        if(!promocionView.getNombreFilter().isEmpty())
            promocionBusqueda.setNombrePromocion(promocionView.getNombreFilter().getValue());
        
        if(!promocionView.getCodigoPromoFilter().isEmpty())
            promocionBusqueda.setCodigoPromocion(promocionView.getCodigoPromoFilter().getValue());
        promocionBusqueda.setActivo(promocionView.getActivosCheck().getValue());
        return promocionBusqueda;
    }

    private boolean checkFiltros() {
        return !promocionView.getIdFilter().isEmpty() || !promocionView.getTipoPromoFilter().isEmpty() ||
                !promocionView.getNombreFilter().isEmpty() || !promocionView.getCodigoPromoFilter().isEmpty() ||
                !promocionView.getVencimientoMenorAFilter().isEmpty() || promocionView.getActivosCheck().getValue();
    }

    private void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public PromocionView getView(){
        return promocionView;
    }


}

