package com.tp.proyecto1.utils;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.services.ClienteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@UIScope
public class BuscadorClientesWindow extends Dialog implements View {

    private Grid<Cliente> grid = new Grid<>(Cliente.class);
    private NumberField idFilter = new NumberField("Nº Cliente");
    private TextField nameFilter = new TextField("Nombre");
    private TextField lastNameFilter = new TextField("Apellido");
    private NumberField dniFilter = new NumberField("DNI");
    private Button searchButton = new Button("Buscar", VaadinIcon.SEARCH.create());

    @Autowired
    private ClienteService clienteService;

    private TextField filtro;

    public BuscadorClientesWindow(TextField filtro) {
        Inject.Inject(this);
        this.filtro = filtro;
        setComponents();
        setLayout();
        setGrid();
        setListeners();
    }

    private void setListeners() {
        searchButton.addClickListener(e-> listClientes());
        grid.addItemDoubleClickListener(e->seleccionarCliente(e));
    }

    private void seleccionarCliente(ItemDoubleClickEvent<Cliente> e) {
        Cliente clienteSeleccionado = e.getItem();
        filtro.setValue(clienteSeleccionado.getId().toString());
        this.close();
    }

    private void listClientes() {
        Cliente clienteBusqueda = new Cliente();
        clienteBusqueda.setActivo(true);
        if(checkFiltros()){
            setParametrosBusqueda(clienteBusqueda);
            grid.setItems(clienteService.findClientes(clienteBusqueda));
        }else{
            new GenericDialog("Debe completar al menos un criterio de busqueda");
        }
    }

    private void setParametrosBusqueda(Cliente clienteBusqueda) {
        if(!idFilter.isEmpty()){
            clienteBusqueda.setId(idFilter.getValue().longValue());
        }
        if (!dniFilter.isEmpty()) {
            clienteBusqueda.setDni(String.valueOf(dniFilter.getValue().intValue()));
        }
        if (!nameFilter.isEmpty()) {
            clienteBusqueda.setNombre(nameFilter.getValue());
        }
        if (!lastNameFilter.isEmpty()) {
            clienteBusqueda.setApellido(lastNameFilter.getValue());
        }
    }

    private boolean checkFiltros() {
        return !idFilter.isEmpty() || !dniFilter.isEmpty() ||
                !nameFilter.isEmpty() || !lastNameFilter.isEmpty();
    }


    @Override
    public void setComponents() {

    }

    @Override
    public void setLayout() {
        HorizontalLayout hlSpace = new HorizontalLayout();
        hlSpace.setWidthFull();
        HorizontalLayout actions = new HorizontalLayout(idFilter, nameFilter, lastNameFilter, dniFilter,hlSpace, searchButton);
        this.add(actions, grid);
        this.setSizeFull();
        actions.setWidthFull();
        actions.setVerticalComponentAlignment(FlexComponent.Alignment.END, searchButton);
    }

    @Override
    public void setGrid() {

        grid.setColumns("id", "nombre", "apellido", "dni");
        grid.getColumnByKey("id").setHeader("Nº");
        grid.getColumnByKey("id").setWidth("70px").setFlexGrow(0);
        grid.getColumnByKey("dni").setHeader("DNI");
        grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);

    }
}
