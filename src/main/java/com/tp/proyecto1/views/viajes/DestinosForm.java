package com.tp.proyecto1.views.viajes;

import com.tp.proyecto1.model.viajes.Continente;
import com.tp.proyecto1.model.viajes.Pais;
import com.tp.proyecto1.model.viajes.Provincia;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class DestinosForm extends Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();

	private ComboBox<Continente> comboContinente = new ComboBox<>("Continente");
	private ComboBox<Pais> comboPais = new ComboBox<>("País");
	private ComboBox<Provincia> comboProvincia = new ComboBox<>("Provincia");

	private TextField txtPais = new TextField();
	private TextField txtProvincia = new TextField();
	private TextField txtCiudad = new TextField();

	private Button btnPais = new Button("Nuevo");
	private Button btnProvincia = new Button("Nuevo");
	private Button btnCiudad = new Button("Nuevo");

	public DestinosForm() {

		setComponents();
		setLayouts();

	}

	private void setLayouts() {

		HorizontalLayout hlPais = new HorizontalLayout();
		HorizontalLayout hlProvincia = new HorizontalLayout();
		HorizontalLayout hlCiudad = new HorizontalLayout();

		hlPais.add(comboPais, txtPais, btnPais);
		hlProvincia.add(comboProvincia, txtProvincia, btnProvincia);
		hlCiudad.add(txtCiudad, btnCiudad);

		mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
		hlPais.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
		hlCiudad.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
		hlProvincia.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
		mainLayout.add(comboContinente,hlPais,hlProvincia, hlCiudad);
		mainLayout.setSizeFull();

		this.add(mainLayout);
		this.setWidth("500px");
		this.setHeight("100%");

	}

	private void setComponents() {

		comboContinente.setWidth("450px");
		txtCiudad.setWidth("400px");

		comboProvincia.setItemLabelGenerator(Provincia::getNombre);
		comboContinente.setItemLabelGenerator(Continente::getNombre);
		comboPais.setItemLabelGenerator(Pais::getNombre);

	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	public ComboBox<Continente> getComboContinente() {
		return comboContinente;
	}

	public void setComboContinente(ComboBox<Continente> comboContinente) {
		this.comboContinente = comboContinente;
	}

	public ComboBox<Pais> getComboPais() {
		return comboPais;
	}

	public void setComboPais(ComboBox<Pais> comboPais) {
		this.comboPais = comboPais;
	}

	public ComboBox<Provincia> getComboProvincia() {
		return comboProvincia;
	}

	public void setComboProvincia(ComboBox<Provincia> comboProvincia) {
		this.comboProvincia = comboProvincia;
	}

	public TextField getTxtPais() {
		return txtPais;
	}

	public void setTxtPais(TextField txtPais) {
		this.txtPais = txtPais;
	}

	public TextField getTxtProvincia() {
		return txtProvincia;
	}

	public void setTxtProvincia(TextField txtProvincia) {
		this.txtProvincia = txtProvincia;
	}

	public TextField getTxtCiudad() {
		return txtCiudad;
	}

	public void setTxtCiudad(TextField txtCiudad) {
		this.txtCiudad = txtCiudad;
	}

	public Button getBtnPais() {
		return btnPais;
	}

	public void setBtnPais(Button btnPais) {
		this.btnPais = btnPais;
	}

	public Button getBtnProvincia() {
		return btnProvincia;
	}

	public void setBtnProvincia(Button btnProvincia) {
		this.btnProvincia = btnProvincia;
	}

	public Button getBtnCiudad() {
		return btnCiudad;
	}

	public void setBtnCiudad(Button btnCiudad) {
		this.btnCiudad = btnCiudad;
	}
}
