package com.tp.proyecto1.utils;

import com.tp.proyecto1.model.clientes.Cliente;
import com.tp.proyecto1.services.ClienteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BuscadorClientesComponent extends HorizontalLayout {

    private TextField filtro = new TextField();
    private Button searchButton = new Button(VaadinIcon.SEARCH.create());
    private Label compDescripcion;

    @Autowired
    ClienteService clienteService;

    Cliente cliente;

    public BuscadorClientesComponent(Label compDescripcion) {
        Inject.Inject(this);
        this.compDescripcion = compDescripcion;
        setLayout();
        setListeners();

    }

    private void setListeners() {

        filtro.addValueChangeListener(e->buscarCliente());
        searchButton.addClickListener(e->openBuscadorCliente());

    }

    private void openBuscadorCliente() {
        BuscadorClientesWindow buscador = new BuscadorClientesWindow(filtro);
        buscador.open();
    }

    private void buscarCliente() {

        if(!filtro.isEmpty()){
            Optional<Cliente> cliente = clienteService.findById(Long.parseLong(filtro.getValue()));

            if(cliente.isPresent()){
                compDescripcion.setText(cliente.get().getNombreyApellido());
                this.cliente = cliente.get();
            }else{
                filtro.clear();
                new GenericDialog("Cliente no encontrado");
            }
        }

    }

    private void setLayout() {
        this.setDefaultVerticalComponentAlignment(Alignment.END);
        this.add(filtro);
        filtro.setSuffixComponent(searchButton);
        filtro.setPattern("[0-9]*");
        filtro.setPreventInvalidInput(true);
        this.setSpacing(false);
    }

    public TextField getFiltro() {
        return filtro;
    }

    public void setFiltro(TextField filtro) {
        this.filtro = filtro;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public Label getCompDescripcion() {
        return compDescripcion;
    }

    public void setCompDescripcion(Label compDescripcion) {
        this.compDescripcion = compDescripcion;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
