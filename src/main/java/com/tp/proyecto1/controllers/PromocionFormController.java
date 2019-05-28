package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.services.DestinoService;
import com.tp.proyecto1.services.PromocionService;
import com.tp.proyecto1.services.TagDestinoService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.promociones.PromocionForm;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@UIScope
public class PromocionFormController {

    private PromocionForm promocionForm;

    @Autowired
    private PromocionService promocionService;
    
    @Autowired
    private ViajeService viajesService;

    @Autowired
    private TagDestinoService tagsDestinoService;
    
    @Autowired
    private DestinoService destinosService;
    
    private ChangeHandler changeHandler;

    private Binder<Promocion> binderPromocion = new Binder<>();
    
    private Binder<Collection<Viaje>>binderViajes = new Binder<>();
    
    private Binder<Collection<Destino>>binderDestinos = new Binder<>();
    
    private Promocion promocion;

    public PromocionFormController() {
        Inject.Inject(this);
        this.promocionForm = new PromocionForm();
        setListeners();
        setComponents();
        setBinders();
    }

    private void setComponents() {
    	promocionForm.getDestinos().setItems(destinosService.findAll());
    	promocionForm.getViajes().setItems(viajesService.findAll());
    	//promocionForm.getTagsDestino().setItems(tagsDestinoService.findAll());
    }

    private void setListeners() {
    	promocionForm.getBtnSave().addClickListener(e-> savePromocion(promocion));
    	promocionForm.getBtnCancel().addClickListener(e->promocionForm.close());
    	promocionForm.getTipoPromocion().addValueChangeListener(e-> cambiarBonificador());
    }
    
    private void cambiarBonificador()
    {
    	promocionForm.getNroFloat().setPrefixComponent(null);
    	promocionForm.getNroFloat().setSuffixComponent(null);
    	 if(promocionForm.getTipoPromocion().getValue()!= null)
    	 {
    		 promocionForm.getNroFloat().setEnabled(true);
    		 if (promocionForm.getTipoPromocion().getValue().equals("Puntos"))
    		 {
    			 promocionForm.getNroFloat().setPrefixComponent(new Span("x"));
    			 promocionForm.getNroFloat().setPattern("[1-9]*");
    		 }
    		 else
    		 {
    			 promocionForm.getNroFloat().setSuffixComponent(new Span("%"));
    			 promocionForm.getNroFloat().setPattern("^100$|^[0-9]{1,2}$|^[0-9]{1,2}");// se agrega lo siguiente si queremos que tenga coma:  \\,[0-9]{1,2}$
    		 }
    	 }
    	 else
    	 {
    		 promocionForm.getNroFloat().setEnabled(false);
    	 }
	}

	private void savePromocion(Promocion promocion)
    {
    	if(promocion==null){
            promocion = setNewPromocion();
        }

        if (binderPromocion.writeBeanIfValid(promocion)) {

            promocionService.save(promocion);
        	promocionForm.close();
            Notification.show("Promocion Guardada");
            changeHandler.onChange();
        }
    }

    private Promocion setNewPromocion()
    {
    	String nombrePromocion = promocionForm.getNombre().getValue();
    	String descripcion = promocionForm.getTextAreaDescripcion().getValue();
    	LocalDate fechaVencimiento = promocionForm.getFechaVencimiento().getValue();
    	Integer nroFloat = 0;
    	if (!promocionForm.getNroFloat().getValue().equals(""))
    		nroFloat = Integer.parseInt(promocionForm.getNroFloat().getValue());
    	Integer cantidadPasajes = 0;
    	if (!promocionForm.getCantidadPasajes().getValue().equals(""))
    		cantidadPasajes = Integer.parseInt(promocionForm.getCantidadPasajes().getValue());
    	Set<Destino> destinos  = promocionForm.getDestinos().getValue();
    	Set<Viaje> viajes = promocionForm.getViajes().getValue();
    	//Set<TagDestino> tags = promocionForm.getTagsDestino().getValue();
    	Promocion promocionToAdd;
    	if (promocionForm.getTipoPromocion().getValue()=="Descuento")
    		promocionToAdd = new PromocionDescuento(nombrePromocion,descripcion,fechaVencimiento,null,nroFloat,cantidadPasajes);
    	else
    		promocionToAdd = new PromocionPuntos(nombrePromocion,descripcion,fechaVencimiento,null,nroFloat,cantidadPasajes);
    	promocionToAdd.setDestinosAfectados(destinos);
    	promocionToAdd.setViajesAfectados(viajes);
    	//promocionToAdd.setTagsDestinoAfectados(tags);
    	
    	return promocionToAdd;
    	
    }

    public void setComponentsValues(Promocion promocion) {
        this.promocion = promocion;
        binderPromocion.setBean(promocion);
        binderViajes.setBean(promocion.getViajesAfectados());
        binderDestinos.setBean(promocion.getDestinosAfectados());
        setBinders();
    }

    private void setBinders() {

    	setBinderFieldNombrePromocion(promocionForm.getNombre(), Promocion::getNombrePromocion, Promocion::setNombrePromocion, true);
    	setBinderDatePickerFechaVencimiento(promocionForm.getFechaVencimiento(), Promocion::getFechaVencimiento, Promocion::setFechaVencimiento, true);
    	setBinderFieldDescripcion(promocionForm.getTextAreaDescripcion(), Promocion::getDescripcion, Promocion::setDescripcion, false);
    	setBinderComboTipoPromocion(promocionForm.getTipoPromocion(), Promocion::getTipoPromocion, Promocion::setTipoPromocion, true);
    	setBinderFieldDoubleValue(promocionForm.getNroFloat(), Promocion::getDoubleValue, Promocion::setDoubleValue, true);
    	setBinderFieldCantidadPasajes(promocionForm.getCantidadPasajes(), Promocion::getCantidadPasajes, Promocion::setCantidadPasajes, true);
    	
    	binderPromocion.setBean(promocion);
    }
    
    private void setBinderDatePickerFechaVencimiento(DatePicker datePicker, ValueProvider<Promocion, LocalDate> valueProvider, Setter<Promocion, LocalDate> setter, boolean isRequiered){

        SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue() != null;
        Binder.Binding<Promocion, LocalDate> binding;
        if(isRequiered){
            binding = binderPromocion.forField(datePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPromocion.forField(datePicker).bind(valueProvider, setter);
        }
        promocionForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderFieldDescripcion(AbstractField field, ValueProvider<Promocion, String> valueProvider, Setter<Promocion, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Promocion, String> binding;
        if(isRequiered){
           binding = binderPromocion.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPromocion.forField(field).bind(valueProvider, setter);
        }
        promocionForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderFieldNombrePromocion(AbstractField field, ValueProvider<Promocion, String> valueProvider, Setter<Promocion, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Promocion, String> binding;
        if(isRequiered){
           binding = binderPromocion.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPromocion.forField(field).bind(valueProvider, setter);
        }
        promocionForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderFieldDoubleValue(AbstractField field, ValueProvider<Promocion, Integer> valueProvider, Setter<Promocion, Integer> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Promocion, Integer> binding;
        if(isRequiered){
        	binding = binderPromocion.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .withConverter(new StringToIntegerConverter("Debe ingresar un numero"))
                    .bind(valueProvider, setter);
        }else{
            binding = binderPromocion.forField(field).bind(valueProvider, setter);
        }
        promocionForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderFieldCantidadPasajes(AbstractField field, ValueProvider<Promocion, Integer> valueProvider, Setter<Promocion, Integer> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Promocion, Integer> binding;
        if(isRequiered){
        	binding = binderPromocion.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .withConverter(new StringToIntegerConverter("Debe ingresar un numero"))
                    .bind(valueProvider, setter);
        }else{
            binding = binderPromocion.forField(field).bind(valueProvider, setter);
        }
        promocionForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    private void setBinderComboTipoPromocion(ComboBox combo, ValueProvider<Promocion, String> valueProvider, Setter<Promocion, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> combo.getValue() != null;

        Binder.Binding<Transporte, TipoTransporte> binding;

        if(isRequiered){
            binding = binderPromocion.forField(combo)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderPromocion.forField(combo).bind(valueProvider, setter);
        }
        promocionForm.getBtnSave().addClickListener(event -> binding.validate());
    }
    
    public PromocionForm getPromocionForm() {
        return promocionForm;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}

