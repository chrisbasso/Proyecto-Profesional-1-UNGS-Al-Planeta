package com.tp.proyecto1.views.contabilidad;

import com.tp.proyecto1.model.contabilidad.MovimientoCaja;
import com.tp.proyecto1.model.sucursales.Sucursal;
import com.tp.proyecto1.utils.ConfigDatePicker;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.function.ValueProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MovimientosCajaView extends FilterGridLayout<MovimientoCaja> {
	
    private DatePicker fechaFilter;
    private NumberField idUsuarioFilter;
    private ComboBox<Sucursal> sucursalFilter;    
    private Button btnBuscar;
    private Button btnAgregar; 

    public MovimientosCajaView() {
        super(MovimientoCaja.class);
        setComponents();
        setLayout();
        setGrid();
    }

    private void setComponents() {
        fechaFilter = new DatePicker("Fecha Contab.");
		ConfigDatePicker configDatePicker = new ConfigDatePicker();
		configDatePicker.setearLenguajeEspañol(fechaFilter);
        idUsuarioFilter = new NumberField("ID Usuario");        
        sucursalFilter = new ComboBox<Sucursal>();
        sucursalFilter.setLabel("Sucursal");
        btnBuscar = new Button("Buscar", VaadinIcon.SEARCH.create());
        btnBuscar.setMinWidth("110px");
        btnAgregar = new Button("Agregar", VaadinIcon.PLUS.create());
        btnAgregar.setMinWidth("110px");     
    }

    private void setLayout() {
        HorizontalLayout hlSpace = new HorizontalLayout();
        hlSpace.setWidthFull();
        this.hlActions.add(idUsuarioFilter,fechaFilter, sucursalFilter, btnBuscar, hlSpace, btnAgregar);
    }

    private void setGrid() {
        grid.setColumns("cabecera.fechaContabilizacion", "idAsiento", "cabecera.sucursal", "cabecera.usuario.user", "cabecera.textoCabecera", "importe");
        grid.getColumnByKey("cabecera.fechaContabilizacion").setHeader("Fecha");
        grid.getColumnByKey("cabecera.usuario.user").setHeader("Usuario");
        grid.getColumnByKey("cabecera.textoCabecera").setHeader("Texto");
    }

    public void agregarColumnaBorrado(ValueProvider<MovimientoCaja, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }
    
    public void cargarComboSucursal(List <Sucursal> sucursales) {
    	sucursalFilter.setItems(sucursales);
    }
    
    public Sucursal getValueSucursal() {
    	Sucursal ret = null;
    	if(sucursalFilter.getValue() != null) {
    		ret = sucursalFilter.getValue();
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
    
    public void cargarMovimientos(List <MovimientoCaja> movimientos) {
    	grid.setItems(movimientos);
    }
    
    public void cargarEtiquetasSaldos(Map<Sucursal, Double> map) {
    	this.hlFooter.removeAll();
        this.hlFooter.setWidthFull();
        VerticalLayout etiqueta = new VerticalLayout();
    	etiqueta.add("Saldos por sucursal");
    	this.hlFooter.add(etiqueta);
    	for (Map.Entry<Sucursal, Double> valor : map.entrySet()) {
    		Label sucursal = new Label(valor.getKey().getDescripcion().toString());
    		Label total = new Label("$ " + valor.getValue().toString());
    		VerticalLayout cuadro = new VerticalLayout();
    		cuadro.add(sucursal);    
    		cuadro.add(total);    		
    		this.hlFooter.add(cuadro);    		
		}
    }
    
    public MovimientoCaja getMovimientoSeleccionado() {
    	return grid.asSingleSelect().getValue();
    }

    public void setUsuarioListener(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
        idUsuarioFilter.addValueChangeListener(e);
    }
    
    public void setBtnBuscarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnBuscar.addClickListener(e);
    }
    
    public void setBtnAgregarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnAgregar.addClickListener(e);
    }
}