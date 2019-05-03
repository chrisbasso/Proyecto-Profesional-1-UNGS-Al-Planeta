package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class VentaView extends FilterGridLayout<Venta> implements View {


    private NumberField numeroClienteFilter;
    private TextField paisFilter;
    private TextField ciudadFilter;
    private TextField codTransporteFilter;
    private DatePicker fechaFilter;
    private Button searchButton;
    private Button btnComprobante;


    public VentaView() {
        super(Venta.class);
        setComponents();
        setLayout();
        setGrid();
    }

    @Override
    public void setComponents() {


        this.numeroClienteFilter = new NumberField("Nº Cliente");
        this.paisFilter = new TextField("País");
        this.ciudadFilter = new TextField("Ciudad");
        this.codTransporteFilter = new TextField("Cod. Transporte");
        this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
        this.searchButton.setMinWidth("110px");
        this.btnComprobante = new Button("Exportar Comprobante");
        this.fechaFilter = new DatePicker("Fecha");

    }

    @Override
    public void setLayout() {

        HorizontalLayout hlSpace = new HorizontalLayout();
        this.hlFooter.add(btnComprobante);
        hlSpace.setWidthFull();
        this.hlActions.add(numeroClienteFilter, paisFilter, ciudadFilter, codTransporteFilter,fechaFilter,hlSpace, searchButton);

    }

    @Override
    public void setGrid() {

        grid.setColumns("cliente.id", "cliente.nombre", "cliente.apellido", "cliente.dni", "viaje.destino.ciudad",
                "viaje.destino.pais", "viaje.transporte.codTransporte", "viaje.fechaSalida","viaje.precio");
        grid.getColumnByKey("cliente.id").setHeader("Nº Cliente");
        grid.getColumnByKey("cliente.id").setWidth("100px").setFlexGrow(0);

    }

    public NumberField getNumeroClienteFilter() {
        return numeroClienteFilter;
    }

    public void setNumeroClienteFilter(NumberField numeroClienteFilter) {
        this.numeroClienteFilter = numeroClienteFilter;
    }

    public TextField getPaisFilter() {
        return paisFilter;
    }

    public void setPaisFilter(TextField paisFilter) {
        this.paisFilter = paisFilter;
    }

    public TextField getCiudadFilter() {
        return ciudadFilter;
    }

    public void setCiudadFilter(TextField ciudadFilter) {
        this.ciudadFilter = ciudadFilter;
    }

    public TextField getCodTransporteFilter() {
        return codTransporteFilter;
    }

    public void setCodTransporteFilter(TextField codTransporteFilter) {
        this.codTransporteFilter = codTransporteFilter;
    }

    public DatePicker getFechaFilter() {
        return fechaFilter;
    }

    public void setFechaFilter(DatePicker fechaFilter) {
        this.fechaFilter = fechaFilter;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public Button getBtnComprobante() {
        return btnComprobante;
    }

    public void setBtnComprobante(Button btnComprobante) {
        this.btnComprobante = btnComprobante;
    }
}
