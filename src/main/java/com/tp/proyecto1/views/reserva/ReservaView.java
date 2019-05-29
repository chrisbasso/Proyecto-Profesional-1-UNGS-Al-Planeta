package com.tp.proyecto1.views.reserva;

import java.time.LocalDate;
import java.util.List;

import com.tp.proyecto1.model.pasajes.Reserva;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Viaje;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
    private ComboBox<Pais> paisFilter;
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
        this.paisFilter = new ComboBox<>("País");
        this.paisFilter.setItemLabelGenerator(Pais::getNombre);
        this.ciudadFilter = new ComboBox<>("Ciudad");
        this.ciudadFilter.setItemLabelGenerator(Ciudad::getNombre);
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
        grid.setColumns("cliente.id", "cliente.nombre", "cliente.apellido", "cliente.dni", "viaje.ciudad.nombre",
                "viaje.ciudad.pais.nombre", "viaje.transporte.codTransporte", "viaje.fechaSalida","viaje.precio");
        grid.getColumnByKey("cliente.id").setHeader("Nº Cliente");
        grid.getColumnByKey("cliente.id").setWidth("100px").setFlexGrow(0);
        grid.getColumnByKey("viaje.ciudad.pais.nombre").setHeader("País");
        grid.getColumnByKey("viaje.ciudad.nombre").setHeader("Ciudad");
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

    public String getValuePais() {
    	String pais = "";
    	if(paisFilter.getValue() != null) {
    		pais = paisFilter.getValue().getNombre();
    	}
        return pais;
    }

    public String getValueCiudad() {
    	String ciudad = "";
    	if(ciudadFilter.getValue() != null) {
    		ciudad = ciudadFilter.getValue().getNombre();
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

    public void setBtnBuscarListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnBuscar.addClickListener(e);
    }

    public void setBtnComprobanteListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnComprobante.addClickListener(e);
    }

    public void setNumeroClienteFilter(NumberField numeroClienteFilter) {
        this.numeroClienteFilter = numeroClienteFilter;
    }

    public void setCodTransporteFilter(TextField codTransporteFilter) {
        this.codTransporteFilter = codTransporteFilter;
    }

    public void setFechaFilter(DatePicker fechaFilter) {
        this.fechaFilter = fechaFilter;
    }

    public NumberField getNumeroClienteFilter() {
        return numeroClienteFilter;
    }

    public ComboBox<Pais> getPaisFilter() {
        return paisFilter;
    }

    public void setPaisFilter(ComboBox<Pais> paisFilter) {
        this.paisFilter = paisFilter;
    }

    public ComboBox<Ciudad> getCiudadFilter() {
        return ciudadFilter;
    }

    public void setCiudadFilter(ComboBox<Ciudad> ciudadFilter) {
        this.ciudadFilter = ciudadFilter;
    }

    public TextField getCodTransporteFilter() {
        return codTransporteFilter;
    }

    public DatePicker getFechaFilter() {
        return fechaFilter;
    }

    public Button getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(Button btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public Button getBtnComprobante() {
        return btnComprobante;
    }

    public void setBtnComprobante(Button btnComprobante) {
        this.btnComprobante = btnComprobante;
    }

    public Button getBtnVender() {
        return btnVender;
    }

    public void setBtnVender(Button btnVender) {
        this.btnVender = btnVender;
    }

    public void setBtnVenderListener(ComponentEventListener<ClickEvent<Button>> e) {
        btnVender.addClickListener(e);
    }
}