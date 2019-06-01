package com.tp.proyecto1.views.configuracion;

import java.util.List;

import org.omg.CORBA.portable.ValueFactory;

import com.tp.proyecto1.model.configuraciones.Configuracion;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;

public class ConfiguracionView  extends FilterGridLayout<Configuracion> {
    
	private TextField descripcionFilter;
    private Button btnAgregar;
    private Button btnBuscar;

    
	public ConfiguracionView() {
		super(Configuracion.class);
        setComponents();
        setLayout();
        setGrid();
	}

    private void setComponents() {
        descripcionFilter = new TextField();                
        btnAgregar = new Button("Configuracion", VaadinIcon.PLUS.create());
        btnAgregar.setMinWidth("110px");
        btnBuscar = new Button("Buscar", VaadinIcon.SEARCH.create());
        btnBuscar.setMinWidth("110px");
    }

    private void setLayout() {
        HorizontalLayout hlSpace = new HorizontalLayout();
        hlSpace.setWidthFull();
        this.hlActions.add(descripcionFilter, btnBuscar, hlSpace, btnAgregar);
    }

    private void setGrid() {
        grid.setColumns("clave", "value");
        grid.getColumnByKey("clave").setHeader("Configuracion");
        grid.getColumnByKey("value").setHeader("Valor");
    }

    public void agregarColumnaVisualizar(ValueProvider<Configuracion, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }
    
    public void cargarConfiguraciones(List <Configuracion> configuraciones) {
    	grid.setItems(configuraciones);
    }
 
    public String getValueClave() {
    	String ret = "";
    	if(descripcionFilter.getValue() != null) {
    		ret = descripcionFilter.getValue();
    	}
    	return ret;
    }
    
    public Configuracion getConfiguracionSeleccionada() {
    	return grid.asSingleSelect().getValue();
    }

    public void setBtnAgregarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnAgregar.addClickListener(e);
    }
    
    public void setBtnBuscarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnBuscar.addClickListener(e);
    }
}