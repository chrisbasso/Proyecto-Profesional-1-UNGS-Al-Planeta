package com.tp.proyecto1.views.ventas;

import com.tp.proyecto1.model.pasajes.Venta;
import com.tp.proyecto1.model.viajes.Ciudad;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.utils.FilterGridLayout;
import com.tp.proyecto1.utils.View;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class VentaView extends FilterGridLayout<Venta> implements View {


    private NumberField numeroClienteFilter;
    private ComboBox<Pais> paisFilter;
    private ComboBox<Ciudad> ciudadFilter;
    private TextField codTransporteFilter;
    private DatePicker fechaFilter;
    private Button searchButton;
    private Button btnComprobante;
    private Button btnEnvioMail;
	private Checkbox activosCheck;


    public VentaView() {
        super(Venta.class);
        setComponents();
        setLayout();
        setGrid();
    }

    @Override
    public void setComponents() {
        this.numeroClienteFilter = new NumberField("Nº Cliente");
        this.paisFilter = new ComboBox<>("País");
        this.paisFilter.setItemLabelGenerator(Pais::getNombre);
        this.ciudadFilter = new ComboBox<>("Ciudad");
        this.ciudadFilter.setItemLabelGenerator(Ciudad::getNombre);
        this.codTransporteFilter = new TextField("Cod. Transporte");
        this.searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());
        this.searchButton.setMinWidth("110px");
        this.btnComprobante = new Button("Exportar Comprobante");
        this.fechaFilter = new DatePicker("Fecha");
        this.activosCheck = new Checkbox("Solo Activos");
		this.activosCheck.setMinWidth("140px");
		activosCheck.setValue(true);
		this.btnEnvioMail = new Button("Enviar Mail", VaadinIcon.MAILBOX.create());
    }

    @Override
    public void setLayout() {

        HorizontalLayout hlSpace = new HorizontalLayout();
        this.hlFooter.add(btnComprobante,btnEnvioMail);
        hlSpace.setWidthFull();
        this.hlActions.add(numeroClienteFilter, paisFilter, ciudadFilter, codTransporteFilter,fechaFilter,activosCheck, hlSpace, searchButton);

    }

    @Override
    public void setGrid() {

        grid.setColumns("cliente.id", "cliente.nombre", "cliente.apellido", "cliente.dni", "viaje.ciudad.nombre",
                "viaje.ciudad.pais.nombre", "viaje.transporte.codTransporte", "viaje.fechaSalida","importeTotal");
        grid.getColumnByKey("cliente.id").setHeader("Nº Cliente");
        grid.getColumnByKey("cliente.id").setWidth("100px").setFlexGrow(0);
        grid.getColumnByKey("viaje.ciudad.pais.nombre").setHeader("País");
        grid.getColumnByKey("viaje.ciudad.nombre").setHeader("Ciudad");

    }

    public NumberField getNumeroClienteFilter() {
        return numeroClienteFilter;
    }

    public void setNumeroClienteFilter(NumberField numeroClienteFilter) {
        this.numeroClienteFilter = numeroClienteFilter;
    }

    public Grid<Venta> getGrid(){
        return grid;
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

	public Checkbox getActivosCheck() {
		return activosCheck;
	}

	public void setActivosCheck(Checkbox activosCheck) {
		this.activosCheck = activosCheck;
	}

	public Button getBtnEnvioMail() {
		return btnEnvioMail;
	}

	public void setBtnEnvioMail(Button btnEnvioMail) {
		this.btnEnvioMail = btnEnvioMail;
	}
}
