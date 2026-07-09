package com.example.demo.Common;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.Filter.Common.FilterMeta;
import com.vaadin.copilot.shaded.guava.base.Objects;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Setter
public class CurrentFilterDisplay {

    CommonComponents commonComponents;
    Common common;
    SessionCrafter sessionCrafter;

    VerticalLayout v = new VerticalLayout();
    HorizontalLayout h = new HorizontalLayout();
    Map<String, FilterMeta> map = new HashMap<>();

    Consumer<String> reloadButtons;
    Consumer<Object> reloadController;

    public CurrentFilterDisplay(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;
        this.sessionCrafter = new SessionCrafter();

        v.setVisible(false);
        h.addClassName("layout-flex");
        Span span = commonComponents.spanCrafter("Active filters","stat-title");
        v.add(span);
//        h.getStyle().set("flex", "1 1 200px");
//        h.getStyle().set("max-width", "620px");
//        h.getStyle().set("min-width", "302px");
        v.setWidthFull();
        h.setWidthFull();

    }


    public <T> void addFilter(String filterName, T filterValue, String referenceName, T filterData, T nullValue){

        if(filterValue == null){
            map.remove(filterName);

            for (Map.Entry<String, FilterMeta> entry : map.entrySet()) {
                h.add(filterExisting(entry.getKey(),entry.getValue(),filterData));
            }

            return;
        }

        if(map.isEmpty()){
            map.put(filterName,new FilterMeta(filterValue.toString(),referenceName,nullValue));
        }
        else{
            if(map.containsKey(filterName)){
                map.put(filterName, new FilterMeta(filterValue.toString(),referenceName,nullValue));
            }
            else {
                map.put(filterName, new FilterMeta(filterValue.toString(),referenceName,nullValue));
            }
        }


        h.removeAll();

        for (Map.Entry<String, FilterMeta> entry : map.entrySet()) {
            System.out.println(map.size());
            h.add(filterExisting(entry.getKey(),entry.getValue(),filterData));
        }

    }

    public <T> void checkIfValueIsNullValue(String filterName, T nullValue, T filterData){

        System.out.println("checked");

        h.removeAll();
        for (Map.Entry<String, FilterMeta> entry : map.entrySet()) {

            if(entry.getKey().equals(filterName)){
                System.out.println(filterName);
                if(entry.getValue().getValue().equals(nullValue)){
                    System.out.println("remove");
                    map.remove(filterName);
                }
            }

            h.add(filterExisting(entry.getKey(),entry.getValue(),filterData));

        }
    }

    public <T,S> HorizontalLayout filterExisting(String filterName,FilterMeta filterMeta,S filterData){

        if(!map.isEmpty()){
            v.setVisible(true);
        }
        else{
            v.setVisible(false);
        }



        HorizontalLayout vv = new HorizontalLayout();
        vv.setAlignItems(FlexComponent.Alignment.CENTER);
        vv.setWidth("fill-content");
        vv.addClassName("tag-badge");

        Button removeButton = new Button(VaadinIcon.CLOSE_SMALL.create());
        removeButton.addClassName("remove-button");
        removeButton.addClickListener(e -> {
            map.remove(filterName);
            vv.getParent().ifPresent(parent -> ((HasComponents) parent).remove(vv));

            System.out.println(filterName);

            try {
                Field field = filterData.getClass().getDeclaredField(filterMeta.getFieldName());
                field.setAccessible(true);
                field.set(filterData, filterMeta.getIfNull());
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }


            // make it invisible if no more filters
            if(map.isEmpty()){
                v.setVisible(false);
            }



            reloadButtons.accept("Need reload");
            reloadController.accept(filterData);



        });

        Span span = new Span(String.format("%s | %s",filterName,filterMeta.getValue()));
        vv.add(span,removeButton);

        h.add(vv);



        return vv;


    }

    public VerticalLayout getFilters(){


        v.addClassName("island");
        v.setWidthFull();
        v.add(
                h
        );
        return v;
    }



    @SneakyThrows
    public <S, T> void setComponentValue(
            String referenceName,
            S filterDTO,
            Component component
    ) {
        if (component instanceof HasValue<?, ?> c) {

            Field field = filterDTO.getClass().getDeclaredField(referenceName);
            field.setAccessible(true);

            Object value = field.get(filterDTO);

            if (value != null) {

                if (component instanceof IntegerField f) {
                    f.setValue(((Number) value).intValue());
                }
                else if (component instanceof NumberField f) {
                    f.setValue(((Number) value).doubleValue());
                }
                else if(component instanceof DatePicker d){
                    if(value.equals(LocalDate.of(1000,12,12))){
                        d.setValue((null));
                    }
                }
                else {
                    ((HasValue) c).setValue(value);
                }
            }
        }
    }

//    public <S,T> void setComboCustomValue(){
//        materialComboBox.setValue(productFeedModelList.stream().filter(e->e.getId().equals(filterData.getProducts()))
//                .findFirst().orElse(null));
//    }


    @SneakyThrows
    public <T,S> void filterSetter(T getValue, T ifNull, Object otherNull, S filterDTO, String referenceName, String filterName, Consumer<T> consumer){

        checkIfValueIsNullValue(filterName,ifNull,filterDTO);

        T valueItem = getValue == null ? ifNull : getValue;

        T nameOfTheFilter = getValue == null ? ifNull : getValue;

        if(otherNull != null){
            nameOfTheFilter = (T) otherNull;
        }


        if (getValue == null) {

            Field field = filterDTO.getClass().getDeclaredField(referenceName);
            field.setAccessible(true);
            field.set(filterDTO, valueItem);

            addFilter(filterName, getValue,referenceName,filterDTO,ifNull);
            consumer.accept(valueItem);
        }
        else{
            Field field = filterDTO.getClass().getDeclaredField(referenceName);
            field.setAccessible(true);
            field.set(filterDTO, getValue);

            addFilter(filterName, nameOfTheFilter,referenceName,filterDTO,ifNull);

            consumer.accept(valueItem);
        }


    }

    public void clearAllData(){
        map.clear();
        h.removeAll();
        v.setVisible(false);
    }



    @SneakyThrows
    public <T> void preLoadFilters(Class<T> tClass, String sessionName){

        T stuff = sessionCrafter.extractSession(sessionName,tClass);

        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        System.out.println(stuff);

        if(stuff == null){

        }
        else{

        List<Field> fields = Arrays.stream(tClass.getDeclaredFields()).toList();

        T defaultValues = tClass.getDeclaredConstructor().newInstance();

        for(var s : fields) {

            s.setAccessible(true);

            Object givenValue = s.get(stuff);
            Object defaultVal = s.get(defaultValues);

            if (Objects.equal(defaultVal, givenValue)) {
                continue;
            } else {
                addFilter(s.getName(), s.get(stuff), s.getName(), stuff, s.get(defaultValues));
            }
        }
        }

    }










}
