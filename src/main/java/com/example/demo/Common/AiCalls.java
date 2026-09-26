package com.example.demo.Common;


import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import lombok.SneakyThrows;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AiCalls {


    ObjectMapper mapper = new ObjectMapper();


    public<T> String classToStringConverter(T value, Class<T> tClass, String userPrompt) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("\n");
        stringBuilder.append("USER INPUT:\n");
        stringBuilder.append(userPrompt);
        stringBuilder.append("\n");
        stringBuilder.append("END USER INPUT.\n\n");




        stringBuilder.append("{");


        T defaultValues = tClass.getDeclaredConstructor().newInstance();

        StringBuilder builderEnum = new StringBuilder();

        for(var s : tClass.getDeclaredFields()){

            s.setAccessible(true);

            Object providedObject = s.get(value);
            Object defaultObject = s.get(defaultValues);

            if(Objects.equals(providedObject,null)){

                Object showTheValue;

                if(s.getType().isEnum()){
                    Object[] values = s.getType().getEnumConstants();

                    builderEnum.append(String.format("\"%s\": \"%s\" %s",s.getName(),"is an enum its values ,",Arrays.toString(values)) );

                    stringBuilder.append(builderEnum);
                }



                stringBuilder.append("\"")
                        .append(s.getName())
                        .append("\":");

                // IMPORTANT:
                // Jackson decides whether this is String, List, Object,
                // Number, Boolean, Enum, etc.
                stringBuilder.append(
                        mapper.writeValueAsString(defaultObject)
                );



                //String text = String.format("\"%s\": \"%s\",",s.getName(),showTheValue);

               // stringBuilder.append(text);
            }

        }

        stringBuilder.append("}");
        stringBuilder.append(" JSON ONLY.");


        System.out.println("=============================");
        System.out.println(stringBuilder);
        System.out.println("=============================");



        return String.valueOf(stringBuilder);
    }


    // binding data



    @SneakyThrows
    public<T> void bind(T form, T dto) {

        if(dto.getClass() != ErrorResponse.class) {
            for (var s : dto.getClass().getDeclaredFields()) {

                s.setAccessible(true);

                String name = s.getName();

                Field field;

                try {
                    field = form.getClass().getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    continue;
                }
                field.setAccessible(true);

                Object component = field.get(form);

                if (!(component instanceof HasValue<?, ?>)) {
                    continue;
                }


                Object value = s.get(dto);

                if (value == null) {
                    continue;
                }

                setComponentValue((HasValue<?, ?>) component, value);

            }
        }


    }



    public void setComponentValue(
            HasValue component,
            Object value
    ) {

        if (component instanceof IntegerField) {
            component.setValue(((Number) value).intValue());
        }

        else if (component instanceof NumberField) {
            component.setValue(((Number) value).doubleValue());
        }

        else if (component instanceof ComboBox) {
            component.setValue(value);
        }

        else if (component instanceof DatePicker) {
            component.setValue(value);
        }

        else {
            component.setValue(value);
        }
    }









}
