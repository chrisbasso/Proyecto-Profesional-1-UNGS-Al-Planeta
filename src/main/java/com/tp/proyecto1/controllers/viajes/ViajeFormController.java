package com.tp.proyecto1.controllers.viajes;

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
        viajeForm.getContinenteOrigen().setItems(viajeService.findAllContinente());
        viajeForm.getContinenteDestino().setItems(viajeService.findAllContinente());
        viajeForm.getTransporte().setItems(viajeService.findAllTipoTransportes());
        viajeForm.getTagDestino().setItems(tagDestinoService.findAll());
    }

    private void setListeners() {
        viajeForm.getContinenteOrigen().addValueChangeListener(e -> setComboPaises(e.getValue(),viajeForm.getPaisOrigen()));
		viajeForm.getPaisOrigen().addValueChangeListener(e -> setComboProvincias(e.getValue(),viajeForm.getProvinciaOrigen()));
		viajeForm.getProvinciaOrigen().addValueChangeListener(e -> setComboCiudades(e.getValue(), viajeForm.getCiudadOrigen()));

		viajeForm.getContinenteDestino().addValueChangeListener(e -> setComboPaises(e.getValue(),viajeForm.getPaisDestino()));
		viajeForm.getPaisDestino().addValueChangeListener(e -> setComboProvincias(e.getValue(),viajeForm.getProvinciaDestino()));
		viajeForm.getProvinciaDestino().addValueChangeListener(e -> setComboCiudades(e.getValue(), viajeForm.getCiudadDEstino()));


		viajeForm.getBtnSave().addClickListener(e -> saveViaje(viaje));
        viajeForm.getBtnCancel().addClickListener(e -> viajeForm.close());
        viajeForm.getBtnNuevoPais().addClickListener(e -> agregarPais());
        viajeForm.getProvinciaDestino().addValueChangeListener(e -> habilitarNuevaCiudad());
        viajeForm.getBtnNuevoTag().addClickListener(e->agregarTag());

    }

    private void agregarTag() {
        if(!viajeForm.getCompNuevoTag().isEmpty()){
            TagDestino nuevoTag = new TagDestino(viajeForm.getCompNuevoTag().getValue());
            viajeService.saveTagDestino(nuevoTag);
            viajeForm.getTagDestino().setItems(tagDestinoService.findAll());
        }
    }

    private void habilitarNuevaCiudad() {

        if(viajeForm.getProvinciaDestino().getValue()!=null){
            viajeForm.getBtnNuevaCiudad().setEnabled(true);
        }else{
            viajeForm.getBtnNuevaCiudad().setEnabled(false);
        }
    }

    private void agregarPais() {
        if(!viajeForm.getCompNuevoPais().isEmpty()){
            Pais nuevoPais = new Pais(viajeForm.getCompNuevoPais().getValue());
            viajeService.savePais(nuevoPais);
            viajeForm.getProvinciaDestino().setItems(viajeService.findAllProvincias());
        }
    }

    private void setComboCiudades(Provincia provincia, ComboBox<Ciudad> comboCiudad) {
    	comboCiudad.clear();
		if (provincia != null) {
			comboCiudad.setItems(provincia.getCiudades());
		}
    }
	private void setComboPaises(Continente continente, ComboBox<Pais> comboPaises) {
    	comboPaises.clear();
		if (continente != null) {
			comboPaises.setItems(continente.getPaises());
		}
	}
	private void setComboProvincias(Pais pais, ComboBox<Provincia> comboProvincia) {
    	comboProvincia.clear();
		if (pais != null) {
			comboProvincia.setItems(pais.getProvincias());
		}
	}

    private void saveViaje(Viaje viaje) {

        if(viaje==null){
            viaje = setNewViaje();
        }

        if (binderViaje.writeBeanIfValid(viaje) &&
                binderTransporte.writeBeanIfValid(viaje.getTransporte())) {

            viajeService.save(viaje);
            viajeForm.close();
            Notification.show("Viaje Guardado");
            changeHandler.onChange();
        }
    }

    private Viaje setNewViaje() {

       // String pais = viajeForm.getProvinciaDestino().getValue();
        Ciudad ciudadDestino = viajeForm.getCiudadDEstino().getValue();
        Ciudad ciudadOrigen = viajeForm.getCiudadOrigen().getValue();
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
        Viaje viaje = new Viaje(ciudadDestino,ciudadOrigen,transporte,fechaSalida,horaSalida,precio,descipcion, true);
        viaje.getTagsDestino().addAll(tagsDestino);
        viaje.setRecomendacion(recomendacion);
        viaje.setDuracionDias(cantidadDias);
		viaje.setDuracionHoras(cantidadHoras);
        return viaje;
    }

    public void setComponentsValues(Viaje viaje) {
        this.viaje = viaje;
		viajeForm.getContinenteDestino().setItems(viajeService.findAllContinente());
		viajeForm.getContinenteDestino().setValue(viaje.getDestino().getProvincia().getPais().getContinente());
		viajeForm.getContinenteOrigen().setItems(viajeService.findAllContinente());
		viajeForm.getContinenteOrigen().setValue(viaje.getOrigen().getProvincia().getPais().getContinente());
		viajeForm.getPaisDestino().setItems(viajeService.findAllPaises());
		viajeForm.getPaisDestino().setValue(viaje.getDestino().getProvincia().getPais());
		viajeForm.getPaisOrigen().setItems(viajeService.findAllPaises());
		viajeForm.getPaisOrigen().setValue(viaje.getOrigen().getProvincia().getPais());
		viajeForm.getProvinciaDestino().setItems(viajeService.findAllProvincias());
        viajeForm.getProvinciaDestino().setValue(viaje.getDestino().getProvincia());
		viajeForm.getProvinciaOrigen().setItems(viajeService.findAllProvincias());
		viajeForm.getProvinciaOrigen().setValue(viaje.getOrigen().getProvincia());

        binderViaje.setBean(viaje);
        binderTransporte.setBean(viaje.getTransporte());
        setBinders();
    }

    private void setBinders() {

        setBinderFieldCiudad(viajeForm.getCiudadDEstino(), Viaje::getDestino, Viaje::setDestino, true);
        setBinderFieldCiudad(viajeForm.getCiudadOrigen(), Viaje::getOrigen, Viaje::setOrigen, true);
        setBinderDatePickerViaje(viajeForm.getFechaSalida(), Viaje::getFechaSalida, Viaje::setFechaSalida, true);
        setBinderTimePickerViaje(viajeForm.getHoraSalida(), Viaje::getHoraSalida, Viaje::setHoraSalida, true);
        setBinderComboTipoTransporte(viajeForm.getTransporte(), Transporte::getTipo, Transporte::setTipo, true);
        setBinderFieldTransporte(viajeForm.getCodTransporte(), Transporte::getCodTransporte, Transporte::setCodTransporte, true);
        setBinderFieldTransporte(viajeForm.getClase(), Transporte::getClase, Transporte::setClase, true);
        setBinderFieldIntegerTransporte(viajeForm.getCapacidad(), Transporte::getCapacidad, Transporte::setCapacidad, true);
        setBinderFieldDoubleViaje(viajeForm.getPrecio(), Viaje::getPrecio, Viaje::setPrecio, true);
        setBinderFieldIntegerViaje(viajeForm.getCantidadDias(), Viaje::getDuracionDias, Viaje::setDuracionDias, false);
        setBinderFieldIntegerViaje(viajeForm.getCantidadHoras(), Viaje::getDuracionHoras, Viaje::setDuracionHoras, false);
        setBinderComboTagDestino(viajeForm.getTagDestino(), Viaje::getTagsDestino, Viaje::setTagsDestino, false);
        setBinderFieldDestino(viajeForm.getTextAreaRecomendaciones(), Viaje::getRecomendacion, Viaje::setRecomendacion, false);
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

    private void setBinderFieldCiudad(ComboBox combo, ValueProvider<Viaje, Ciudad> valueProvider, Setter<Viaje, Ciudad> setter, boolean isRequiered){

        SerializablePredicate<Ciudad> predicate = value -> combo.getValue() != null;
        Binder.Binding<Viaje, Ciudad> binding;

        if(isRequiered){
            binding = binderViaje.forField(combo)
                    .withValidator(predicate, "El campo es obligatorio")
                    .bind(valueProvider, setter);
        }else{
            binding = binderViaje.forField(combo).bind(valueProvider, setter);
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

    private void setBinderFieldDestino(AbstractField field, ValueProvider<Viaje, String> valueProvider, Setter<Viaje, String> setter, boolean isRequiered){

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

    private void setBinderComboTagDestino(MultiselectComboBox combo, ValueProvider<Viaje, Set<TagDestino>> valueProvider, Setter<Viaje, Set<TagDestino>> setter, boolean isRequiered){

        Binder.Binding<Viaje, Set<TagDestino>> binding;

        binding = binderViaje.forField(combo).bind(valueProvider, setter);

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
