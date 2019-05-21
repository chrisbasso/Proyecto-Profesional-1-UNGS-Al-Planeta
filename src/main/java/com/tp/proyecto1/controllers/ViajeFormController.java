package com.tp.proyecto1.controllers;

import com.tp.proyecto1.model.viajes.*;
import com.tp.proyecto1.services.TagDestinoService;
import com.tp.proyecto1.services.ViajeService;
import com.tp.proyecto1.utils.ChangeHandler;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.viajes.ViajeForm;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@UIScope
public class ViajeFormController {

    private ViajeForm viajeForm;

    @Autowired
    private ViajeService viajeService;

    @Autowired
    private TagDestinoService tagDestinoService;

    private ChangeHandler changeHandler;

    private Binder<Viaje> binderViaje = new Binder<>();
    private Binder<Destino> binderDestino = new Binder<>();
    private Binder<Transporte> binderTransporte = new Binder<>();

    private Viaje viaje;

    public ViajeFormController() {
        Inject.Inject(this);
        this.viajeForm = new ViajeForm();
        setListeners();
        setComponents();
        setBinders();
    }

    private void setComponents() {
        viajeForm.getTransporte().setItems(viajeService.findAllTipoTransportes());
        viajeForm.getTagDestino().setItems(tagDestinoService.findAll());
    }

    private void setListeners() {
        viajeForm.getBtnSave().addClickListener(e-> saveViaje(viaje));
        viajeForm.getBtnCancel().addClickListener(e->viajeForm.close());
    }

    private void saveViaje(Viaje viaje) {

        if(viaje==null){
            viaje = setNewViaje();
        }

        if (binderViaje.writeBeanIfValid(viaje) &&
                binderTransporte.writeBeanIfValid(viaje.getTransporte()) &&
                binderDestino.writeBeanIfValid(viaje.getDestino())) {

            viajeService.save(viaje);
            viajeForm.close();
            Notification.show("Viaje Guardado");
            changeHandler.onChange();
        }
    }

    private Viaje setNewViaje() {
        String pais = viajeForm.getPais().getValue();
        String ciudad = viajeForm.getCiudad().getValue();
        LocalDate fechaSalida = viajeForm.getFechaSalida().getValue();
        LocalTime horaSalida = viajeForm.getHoraSalida().getValue();
        TipoTransporte tipoTransporte = viajeForm.getTransporte().getValue();
        String codTransporte = viajeForm.getCodTransporte().getValue();
        String clase = viajeForm.getClase().getValue();
        Integer capacidad = Integer.parseInt(viajeForm.getCapacidad().getValue());
        Double precio = viajeForm.getPrecio().getValue();
        Set<TagDestino> tagsDestino = new HashSet<>();
        tagsDestino.addAll(viajeForm.getTagDestino().getSelectedItems());
        String descipcion = viajeForm.getTextAreaDescripcion().getValue();
        String recomendacion = viajeForm.getTextAreaRecomendaciones().getValue();
        Integer cantidadDias = viajeForm.getCantidadDias().getValue().intValue();
        Integer cantidadHoras = viajeForm.getCantidadHoras().getValue().intValue();
        Transporte transporte = new Transporte(codTransporte,tipoTransporte, capacidad, clase);
        Destino destino = new Destino(ciudad, pais, recomendacion);
        destino.setTagsDestino(new HashSet<>());
        destino.getTagsDestino().addAll(tagsDestino);
        Viaje viaje = new Viaje(destino,transporte,fechaSalida,horaSalida,precio,descipcion, true);
		viaje.setDuracionDias(cantidadDias);
		viaje.setDuracionHoras(cantidadHoras);
        return viaje;
    }

    public void setComponentsValues(Viaje viaje) {
        this.viaje = viaje;
        binderViaje.setBean(viaje);
        binderDestino.setBean(viaje.getDestino());
        binderTransporte.setBean(viaje.getTransporte());
        setBinders();
    }

    private void setBinders() {

        setBinderFieldDestino(viajeForm.getPais(), Destino::getPais, Destino::setPais, true);
        setBinderFieldDestino(viajeForm.getCiudad(), Destino::getCiudad, Destino::setCiudad, true);
        setBinderDatePickerViaje(viajeForm.getFechaSalida(), Viaje::getFechaSalida, Viaje::setFechaSalida, true);
        setBinderTimePickerViaje(viajeForm.getHoraSalida(), Viaje::getHoraSalida, Viaje::setHoraSalida, true);
        setBinderComboTipoTransporte(viajeForm.getTransporte(), Transporte::getTipo, Transporte::setTipo, true);
        setBinderFieldTransporte(viajeForm.getCodTransporte(), Transporte::getCodTransporte, Transporte::setCodTransporte, true);
        setBinderFieldTransporte(viajeForm.getClase(), Transporte::getClase, Transporte::setClase, true);
        setBinderFieldIntegerTransporte(viajeForm.getCapacidad(), Transporte::getCapacidad, Transporte::setCapacidad, true);
        setBinderFieldDoubleViaje(viajeForm.getPrecio(), Viaje::getPrecio, Viaje::setPrecio, true);
        setBinderFieldIntegerViaje(viajeForm.getCantidadDias(), Viaje::getDuracionDias, Viaje::setDuracionDias, false);
        setBinderFieldIntegerViaje(viajeForm.getCantidadHoras(), Viaje::getDuracionHoras, Viaje::setDuracionHoras, false);
        setBinderComboTagDestino(viajeForm.getTagDestino(), Destino::getTagsDestino, Destino::setTagsDestino, false);
        setBinderFieldDestino(viajeForm.getTextAreaRecomendaciones(), Destino::getRecomendacion, Destino::setRecomendacion, false);
        setBinderFieldViaje(viajeForm.getTextAreaDescripcion(), Viaje::getDescripcion, Viaje::setDescripcion, false);

        binderViaje.setBean(viaje);
    }

	private void setBinderFieldIntegerViaje(AbstractField field, ValueProvider<Viaje, Integer> valueProvider, Setter<Viaje, Integer> setter, boolean isRequiered){

		SerializablePredicate<Integer> predicate = value -> !field.isEmpty();
		Binder.Binding<Viaje, Integer> binding;

		if(isRequiered){
			binding = binderViaje.forField(field)
					.withValidator(predicate, "El campo es obligatorio")
					.withConverter((new DoubleToIntegerConverter()))
					.bind(valueProvider, setter);
		}else{
			binding = binderViaje.forField(field).withConverter((new DoubleToIntegerConverter())).bind(valueProvider, setter);
		}
		viajeForm.getBtnSave().addClickListener(event -> binding.validate());
	}

	private void setBinderFieldViaje(AbstractField field, ValueProvider<Viaje, String> valueProvider, Setter<Viaje, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Viaje, String> binding;
        if(isRequiered){
           binding = binderViaje.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderViaje.forField(field).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderFieldDoubleViaje(AbstractField field, ValueProvider<Viaje, Double> valueProvider, Setter<Viaje, Double> setter, boolean isRequiered){

        SerializablePredicate<Double> predicate = value -> !field.isEmpty();
        Binder.Binding<Viaje, Double> binding;

        if(isRequiered){
            binding = binderViaje.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderViaje.forField(field).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderFieldTransporte(AbstractField field, ValueProvider<Transporte, String> valueProvider, Setter<Transporte, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Transporte, String> binding;
        if(isRequiered){
            binding = binderTransporte.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderTransporte.forField(field).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderFieldIntegerTransporte(AbstractField field, ValueProvider<Transporte, Integer> valueProvider, Setter<Transporte, Integer> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Transporte, Integer> binding;
        if(isRequiered){
            binding = binderTransporte.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .withConverter(new StringToIntegerConverter(""))
                    .bind(valueProvider, setter);
        }else{
            binding = binderTransporte.forField(field).withConverter(new StringToIntegerConverter("")).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    public class DoubleToIntegerConverter implements Converter<Double, Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public Result<Integer> convertToModel(Double presentation, ValueContext valueContext) {
            return Result.ok(presentation.intValue());
        }

        @Override
        public Double convertToPresentation(Integer model, ValueContext valueContext) {
            return model.doubleValue();
        }

    }

    private void setBinderFieldDestino(AbstractField field, ValueProvider<Destino, String> valueProvider, Setter<Destino, String> setter, boolean isRequiered){

        SerializablePredicate<String> predicate = value -> !field.isEmpty();
        Binder.Binding<Destino, String> binding;

        if(isRequiered){
            binding = binderDestino.forField(field)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderDestino.forField(field).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderComboTipoTransporte(ComboBox combo, ValueProvider<Transporte, TipoTransporte> valueProvider, Setter<Transporte, TipoTransporte> setter, boolean isRequiered){

        SerializablePredicate<TipoTransporte> predicate = value -> combo.getValue() != null;

        Binder.Binding<Transporte, TipoTransporte> binding;

        if(isRequiered){
            binding = binderTransporte.forField(combo)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderTransporte.forField(combo).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderComboTagDestino(MultiselectComboBox combo, ValueProvider<Destino, Set<TagDestino>> valueProvider, Setter<Destino, Set<TagDestino>> setter, boolean isRequiered){

        Binder.Binding<Destino, Set<TagDestino>> binding;

        binding = binderDestino.forField(combo).bind(valueProvider, setter);

        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderDatePickerViaje(DatePicker datePicker, ValueProvider<Viaje, LocalDate> valueProvider, Setter<Viaje, LocalDate> setter, boolean isRequiered){

        SerializablePredicate<LocalDate> predicate = value -> datePicker.getValue() != null;
        Binder.Binding<Viaje, LocalDate> binding;
        if(isRequiered){
            binding = binderViaje.forField(datePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderViaje.forField(datePicker).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }

    private void setBinderTimePickerViaje(TimePicker timePicker, ValueProvider<Viaje, LocalTime> valueProvider, Setter<Viaje, LocalTime> setter, boolean isRequiered){

        SerializablePredicate<LocalTime> predicate = value -> timePicker.getValue() != null;
        Binder.Binding<Viaje, LocalTime> binding;
        if(isRequiered){
            binding = binderViaje.forField(timePicker)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderViaje.forField(timePicker).bind(valueProvider, setter);
        }
        viajeForm.getBtnSave().addClickListener(event -> binding.validate());
    }


    public ViajeForm getViajeForm() {
        return viajeForm;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
