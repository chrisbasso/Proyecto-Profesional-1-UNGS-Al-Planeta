package com.tp.proyecto1.views.contabilidad;

import java.time.LocalDate;
import java.util.List;

import com.tp.proyecto1.model.contabilidad.Asiento;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;

public class AsientoView extends FilterGridLayout<Asiento> {

    private NumberField numeroAsientoFilter;
    private DatePicker fechaFilter;
    private NumberField idUsuarioFilter;    
    private Button btnAgregar;
    private Button btnABMCuentas;
    private Button btnBuscar;
    
    private Button btnComprobante;

    public AsientoView() {
        super(Asiento.class);
        setComponents();
        setLayout();
        setGrid();
    }

    private void setComponents() {
        numeroAsientoFilter = new NumberField("Nº Asiento");
        idUsuarioFilter = new NumberField("ID Usuario");
        fechaFilter = new DatePicker("Fecha Contab.");        
        btnAgregar = new Button("Asiento", VaadinIcon.PLUS.create());
        btnAgregar.setMinWidth("110px");
        btnABMCuentas = new Button("ABM Cuentas", VaadinIcon.TOOLS.create());
        btnABMCuentas.setMinWidth("110px");
        btnBuscar = new Button("Buscar", VaadinIcon.SEARCH.create());
        btnBuscar.setMinWidth("110px");
        btnComprobante = new Button("Exportar Comprobante");                
    }

    private void setLayout() {
        HorizontalLayout hlSpace = new HorizontalLayout();
        this.hlFooter.add(btnComprobante);
        hlSpace.setWidthFull();
        this.hlActions.add(numeroAsientoFilter, idUsuarioFilter,fechaFilter, btnBuscar, hlSpace, btnAgregar, btnABMCuentas);
    }

    private void setGrid() {
        grid.setColumns("id", "fechaContabilizacion", "usuario.id", "cabecera.textoCabecera", "anulado");
        grid.getColumnByKey("id").setHeader("Nº Asiento");
        grid.getColumnByKey("usuario.id").setHeader("Nº Usuario");
        grid.getColumnByKey("cabecera.textoCabecera").setHeader("Texto Cabecera");
    }

    public void agregarColumnaVisualizar(ValueProvider<Asiento, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }
    
    public void agregarColumnaBorrado(ValueProvider<Asiento, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }
    
    public Long getValueNumeroAsiento() {
    	Long ret = null;
    	if(numeroAsientoFilter.getValue() != null) {
    		ret = numeroAsientoFilter.getValue().longValue();
    	}
    	return ret;    	
    }

    public LocalDate getValueFecha() {
    	LocalDate ret = null;
    	if(fechaFilter.getValue() != null) {
    		ret = fechaFilter.getValue();
    	}
    	return ret;    	
    }

    public Long getValueUsuario() {
    	Long ret = null;
    	if(idUsuarioFilter.getValue() != null) {
    		ret = Double.doubleToLongBits(idUsuarioFilter.getValue());
    	}
    	return ret;
    }
    
    public void cargarAsientos(List <Asiento> asientos) {
    	grid.setItems(asientos);
    }
    
    public Asiento getAsientoSeleccionado() {
    	return grid.asSingleSelect().getValue();
    }

    public void setBtnAgregarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnAgregar.addClickListener(e);
    }
    
    public void setBtnCuentasListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnABMCuentas.addClickListener(e);
    }

    public void setBtnBuscarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnBuscar.addClickListener(e);
    }

    public void setBtnComprobanteListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnComprobante.addClickListener(e);
    }
}