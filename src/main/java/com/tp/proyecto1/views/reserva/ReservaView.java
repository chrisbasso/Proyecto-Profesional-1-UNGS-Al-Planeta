package com.tp.proyecto1.views.reserva;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Provincia;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;

public class ReservaView extends FilterGridLayout<Reserva> {

    private NumberField numeroClienteFilter;
    private NumberField numeroViajeFilter;
    private ComboBox<Ciudad> ciudadFilter;
    private TextField codTransporteFilter;
    private DatePicker fechaFilter;
    private Button btnBuscar;
    private Button btnComprobante;
    private Button btnVender;

    public ReservaView() {
        super(Reserva.class);
        setComponents();
        setLayout();
        setGrid();
    }

    private void setComponents() {
        this.numeroClienteFilter = new NumberField("Nº Cliente");
        this.numeroViajeFilter = new NumberField("Nº Viaje");
        this.ciudadFilter = new ComboBox<>("Ciudad");
        this.ciudadFilter.setItemLabelGenerator(Ciudad::getNombre);
        this.codTransporteFilter = new TextField("Cod. Transporte");
        this.btnBuscar = new Button("Buscar", VaadinIcon.SEARCH.create());
        this.btnBuscar.setMinWidth("110px");
        this.btnComprobante = new Button("Exportar Comprobante");
        this.fechaFilter = new DatePicker("Fecha Salida");
        this.btnVender = new Button("Vender");
    }

    private void setLayout() {
        HorizontalLayout hlSpace = new HorizontalLayout();
        this.hlFooter.add(btnComprobante, btnVender);
        hlSpace.setWidthFull();
        this.hlActions.add(numeroClienteFilter, numeroViajeFilter, ciudadFilter, codTransporteFilter,fechaFilter,hlSpace, btnBuscar);
    }
    
    private void setGrid() {
        grid.setColumns("cliente.id", "cliente.nombre", "cliente.apellido", "cliente.dni", "viaje.destino.provincia.pais.nombre",
                "viaje.destino.provincia.nombre","viaje.destino.nombre", "viaje.transporte.codTransporte", "viaje.fechaSalida",
                "viaje.precio", "totalPagado");
        grid.getColumnByKey("cliente.id").setHeader("Nº Cliente");
        grid.getColumnByKey("cliente.id").setWidth("100px").setFlexGrow(0);
        grid.getColumnByKey("viaje.destino.provincia.pais.nombre").setHeader("País");
        grid.getColumnByKey("viaje.destino.provincia.nombre").setHeader("Provincia");
        grid.getColumnByKey("viaje.destino.nombre").setHeader("Ciudad");
    }
    
    public void cargarCiudades(List<Ciudad> list) {
    	ciudadFilter.setItems(list);
    }
    
    public void agregarColumnaEdicion(ValueProvider<Reserva, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }

    public void agregarColumnaBorrado(ValueProvider<Reserva, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }
    
    public Long getValueNumeroCliente() {
    	Long idCliente = 0L;
    	if(numeroClienteFilter.getValue() != null) {
    		idCliente = numeroClienteFilter.getValue().longValue();
    	}
        return idCliente;
    }

    public Long getValueNumeroViaje() {
    	Long idViaje = 0L;
    	if(numeroViajeFilter.getValue() != null) {
    		idViaje = numeroViajeFilter.getValue().longValue();
    	}
        return idViaje;
    }
    
    public Ciudad getValueCiudad() {
    	Ciudad ciudad = null;
    	if(ciudadFilter.getValue() != null) {
    		ciudad = ciudadFilter.getValue();
    	}
        return ciudad;
    }
    
    public String getValueCodTransporte() {
    	String codTransporte = "";
    	if(codTransporteFilter.getValue() != null) {
    		codTransporte = codTransporteFilter.getValue();
    	}
        return codTransporte;
    }

    public LocalDate getValueFecha() {
    	LocalDate fecha = null;
    	if(fechaFilter.getValue() != null) {
    		fecha = fechaFilter.getValue();
    	}
        return fecha;
    }
    
    public void cargarReservas(List <Reserva> reservas) {
    	grid.setItems(reservas);
    }
    
    public Reserva getReservaSeleccionada() {
    	return grid.asSingleSelect().getValue();
    }

    public void setIdClienteListener(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
        numeroClienteFilter.addValueChangeListener(e);
    }
        
    public void setIdViajeListener(ValueChangeListener<? super ComponentValueChangeEvent<NumberField, Double>> e) {
        numeroViajeFilter.addValueChangeListener(e);
    }
        
    public void setBtnBuscarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnBuscar.addClickListener(e);
    }

    public void setBtnComprobanteListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnComprobante.addClickListener(e);
    }

   public void setBtnVenderListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnVender.addClickListener(e);
    }
}