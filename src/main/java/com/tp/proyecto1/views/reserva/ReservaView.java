package com.tp.proyecto1.views.reserva;

import java.time.LocalDate;
import java.util.List;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;

public class ReservaView extends FilterGridLayout<Reserva> {

    private NumberField numeroClienteFilter;
    private TextField paisFilter;
    private TextField ciudadFilter;
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
        this.paisFilter = new TextField("País");
        this.ciudadFilter = new TextField("Ciudad");
        this.codTransporteFilter = new TextField("Cod. Transporte");
        this.btnBuscar = new Button("Buscar", VaadinIcon.SEARCH.create());
        this.btnBuscar.setMinWidth("110px");
        this.btnComprobante = new Button("Exportar Comprobante");
        this.fechaFilter = new DatePicker("Fecha");
        this.btnVender = new Button("Venta");
    }

    private void setLayout() {
        HorizontalLayout hlSpace = new HorizontalLayout();
        this.hlFooter.add(btnComprobante, btnVender);
        hlSpace.setWidthFull();
        this.hlActions.add(numeroClienteFilter, paisFilter, ciudadFilter, codTransporteFilter,fechaFilter,hlSpace, btnBuscar);
    }

    private void setGrid() {
        grid.setColumns("cliente.id", "cliente.nombre", "cliente.apellido", "cliente.dni", "viaje.destino.ciudad",
                "viaje.destino.pais", "viaje.transporte.codTransporte", "viaje.fechaSalida","viaje.precio");
        grid.getColumnByKey("cliente.id").setHeader("Nº Cliente");
        grid.getColumnByKey("cliente.id").setWidth("100px").setFlexGrow(0);
    }
    
    public void agregarColumnaEdicion(ValueProvider<Reserva, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }

    public void agregarColumnaBorrado(ValueProvider<Reserva, Button> e) {
    	grid.addComponentColumn(e).setHeader("").setTextAlign(ColumnTextAlign.END).setWidth("75px").setFlexGrow(0);    	
    }
    
	public Grid<Reserva> getGrid() {
		return grid;
	}

    public Long getNumeroClienteFilter() {
    	Long idCliente = 0L;
    	if(numeroClienteFilter.getValue() != null) {
    		idCliente = numeroClienteFilter.getValue().longValue();
    	}
        return idCliente;
    }

    public String getPaisFilter() {
    	String pais = "";
    	if(paisFilter.getValue() != null) {
    		pais = paisFilter.getValue().toString();
    	}
        return pais;
    }

    public String getCiudadFilter() {
    	String ciudad = "";
    	if(ciudadFilter.getValue() != null) {
    		ciudad = ciudadFilter.getValue().toString();
    	}
        return ciudad;
    }
    
    public String getCodTransporteFilter() {
    	String codTransporte = "";
    	if(codTransporteFilter.getValue() != null) {
    		codTransporte = codTransporteFilter.getValue().toString();
    	}
        return codTransporte;
    }

    public LocalDate getFechaFilter() {
    	LocalDate fecha = null;
    	if(fechaFilter.getValue() != null) {
    		fecha = fechaFilter.getValue();
    	}
        return fecha;
    }
    
    public void cargarReservas(List <Reserva> reservas) {
    	grid.setItems(reservas);
    }

    public void setBtnBuscarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnBuscar.addClickListener(e);
    }

    public void setBtnComprobanteListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnComprobante.addClickListener(e);
    }

	public Button getBtnVender() {
		return btnVender;
	}

	public void setBtnVender(Button btnVender) {
		this.btnVender = btnVender;
	}
    
    
}
