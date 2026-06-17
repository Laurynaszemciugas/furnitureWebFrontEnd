package com.example.demo.Common;

import com.example.demo.ControllerModels.Filter.Common.FilterMeta;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Setter
public class CurrentFilterDisplay {

    CommonComponents commonComponents;
    Common common;

    VerticalLayout v = new VerticalLayout();
    HorizontalLayout h = new HorizontalLayout();
    Map<String, FilterMeta> map = new HashMap<>();

    Consumer<String> reloadInfo;

    public CurrentFilterDisplay(CommonComponents commonComponents, Common common) {
        this.commonComponents = commonComponents;
        this.common = common;

        v.setVisible(false);
        h.setWidthFull();

    }


    public <T> void addFilter(String filterName, T filterValue, String referenceName, T filterData){

        if(filterValue == null){
            System.out.println("removed");
            map.remove(filterName);

            if(map.isEmpty()){
                v.setVisible(false);
            }

            h.removeAll();
            for (Map.Entry<String, FilterMeta> entry : map.entrySet()) {
                h.add(filterExisting(entry.getKey(),entry.getValue(),filterData));
            }

            return;
        }

        if(map.isEmpty()){
            map.put(filterName,new FilterMeta(filterValue.toString(),referenceName));
        }
        else{
            if(map.containsKey(filterName)){
                map.put(filterName, new FilterMeta(filterValue.toString(),referenceName));
            }
            else {
                map.put(filterName, new FilterMeta(filterValue.toString(),referenceName));
            }
        }


        h.removeAll();
        for (Map.Entry<String, FilterMeta> entry : map.entrySet()) {
            h.add(filterExisting(entry.getKey(),entry.getValue(),filterData));
        }

    }

    public <T> HorizontalLayout filterExisting(String filterName,FilterMeta filterMeta,T filterData){



        if(map != null|| !map.isEmpty()){
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


            try {
                Field field = filterData.getClass().getDeclaredField(filterMeta.getFieldName());
                field.setAccessible(true);
                field.set(filterData, null);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }


            // make it invisible if no more filters
            if(map.isEmpty()){
                v.setVisible(false);
            }


            reloadInfo.accept("need reload");



        });

        Span span = new Span(String.format("%s | %s",filterName,filterMeta.getValue()));
        vv.add(span,removeButton);

        h.add(vv);



        return vv;


    }

    public VerticalLayout getFilters(){

        Span span = commonComponents.spanCrafter("Active filters","stat-title");

        v.addClassName("island");
        v.setWidthFull();
        v.add(
                span,
                h
        );
        return v;
    }



    @SneakyThrows
    public <S, T> void setComponentValue(
            String referenceName,
            S filterDTO,
            Component component,
            Class<T> tClass
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
                else {
                    ((HasValue) c).setValue(value);
                }
            }
        }
    }


    @SneakyThrows
    public <T,S> void filterSetter(T getValue, T ifNull, S filterDTO, String referenceName, String filterName, Consumer<T> consumer){

        T valueItem = getValue == null ? ifNull : getValue;

        if (getValue == null) {

            Field field = filterDTO.getClass().getDeclaredField(referenceName);
            field.setAccessible(true);
            field.set(filterDTO, null);

            addFilter(filterName, getValue,referenceName,filterDTO);
            consumer.accept(valueItem);
        }
        else{
            Field field = filterDTO.getClass().getDeclaredField(referenceName);
            field.setAccessible(true);
            field.set(filterDTO, getValue);

            addFilter(filterName, getValue,referenceName,filterDTO);

            consumer.accept(valueItem);
        }


    }













}
