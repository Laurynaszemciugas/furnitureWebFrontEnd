package com.example.demo.Common;


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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class AiCalls {


    public <T> T fillDataAutomatically(String prompt, Class<T> referenceToDataNeeded) throws IOException, InterruptedException {

        T value = null;


            ObjectMapper mapper = new ObjectMapper();
            HttpClient client = HttpClient.newHttpClient();

            String json = mapper.writeValueAsString(
                    Map.of(
                            "model", "qwen2.5:3b",
                            "prompt", prompt,
                            "stream", false
                    )
            );



            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .timeout(Duration.ofSeconds(20))
                    .build();


            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("================================");
            System.out.println(response.body());
            System.out.println("=================================");

            AiResponse aiResponse = mapper.readValue(response.body(), AiResponse.class);

            value = mapper.readValue(aiResponse.getResponse(), referenceToDataNeeded);






        return value;

    }





    public<T> String classToStringConverter(T value, Class<T> tClass, String userPrompt) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("\n");
        stringBuilder.append("USER INPUT:\n");
        stringBuilder.append(userPrompt);
        stringBuilder.append("\n");
        stringBuilder.append("END USER INPUT.\n\n");

        stringBuilder.append(
                "IMPORTANT RULES:\n" +
                        "- Return ONLY valid JSON.\n" +
                        "- Return exactly the fields required by the provided JSON schema.\n" +
                        "- Do not add or remove fields.\n" +
                        "- Do not rename fields.\n" +
                        "- Do not return null values.\n" +
                        "- Do not return empty strings.\n" +
                        "- Generate a meaningful value for every field.\n" +
                        "- Generate realistic values based on the USER INPUT.\n" +
                        "- Do not copy placeholder, default, example, or schema values as actual values.\n" +
                        "- Do not use None unless the field genuinely cannot have a meaningful value.\n" +
                        "- Do not use 0 or 0.0 when a realistic value can be inferred.\n" +
                        "- Infer reasonable missing information from the material described by the USER INPUT.\n" +
                        "- Use the USER INPUT as the primary source of information.\n" +
                        "- Correct obvious spelling mistakes when interpreting the USER INPUT.\n" +
                        "- If provided json values is 0 it is INTEGER, if 0.0 it is Double\n" +

                        "For every enum field:\n" +
                        "- Return EXACTLY ONE value.\n" +
                        "- The returned value MUST be one of the listed enum values.\n" +
                        "- NEVER return the list of allowed values.\n" +
                        "- NEVER include square brackets [] in an enum value.\n" +
                        "- NEVER include commas in an enum value.\n" +
                        "- NEVER return multiple enum values.\n" +
                        "- NEVER return an array for an enum field.\n" +
                        "- NEVER return the enum options as a string.\n" +
                        "- Copy the selected enum value EXACTLY as written." +
                        "WRONG:\n" +
                        "\"materialType\": \"[WOOD, METAL, PLASTIC]\"\n" +
                        "\n" +
                        "WRONG:\n" +
                        "\"materialType\": [\"WOOD\"]\n" +
                        "\n" +
                        "WRONG:\n" +
                        "\"materialType\": \"WOOD, METAL\"\n" +
                        "\n" +
                        "CORRECT:\n" +
                        "\"materialType\": \"WOOD\"\n" +
                        "\n" +
                        "If the allowed values are:\n" +
                        "[WOOD, METAL, PLASTIC, GLASS]\n" +
                        "\n" +
                        "and the material is wood, you MUST return:\n" +
                        "\"materialType\": \"WOOD\"" +

                        "COLOR RULES:\n" +
                        "- If a color is mentioned in the USER INPUT, convert it to HEX format.\n" +
                        "- If no color is mentioned, infer a realistic color appropriate for the material.\n" +
                        "- materialColor must always be a valid HEX color at the start add #.\n\n" +


                        "DATE RULES:\n" +
                        "- deliveryDate must be a realistic future delivery date.\n" +
                        "- Use YYYY-MM-DD format.\n\n"


        );


        stringBuilder.append(
                "\nDo not explain your answer. Generate the JSON object directly."
        );



        stringBuilder.append("{");


        T defaultValues = tClass.getDeclaredConstructor().newInstance();

        for(var s : tClass.getDeclaredFields()){

            s.setAccessible(true);

            Object providedObject = s.get(value);
            Object defaultObject = s.get(defaultValues);

            if(Objects.equals(providedObject,null)){

                Object showTheValue;

                if(s.getType().isEnum()){
                    Object[] values = s.getType().getEnumConstants();
                    showTheValue = Arrays.toString(values);


                }
                else{
                    showTheValue = defaultObject;
                }


                String text = String.format("\"%s\": \"%s\",",s.getName(),showTheValue);

                stringBuilder.append(text);
            }

        }

        stringBuilder.append("}");
        stringBuilder.append(" Fill the values with realistic data.");


        System.out.println("=============================");
        System.out.println(stringBuilder);
        System.out.println("=============================");



        return String.valueOf(stringBuilder);
    }


    // binding data



    @SneakyThrows
    public<T> void bind(T form, T dto) {

        for(var s : dto.getClass().getDeclaredFields()){

            s.setAccessible(true);

            String name = s.getName();

            Field field = form.getClass().getDeclaredField(name);
            field.setAccessible(true);

            Object component = field.get(form);

            if(!(component instanceof HasValue<?,?>)){
                continue;
            }


            Object value = s.get(dto);

            if(value == null){
                continue;
            }

            setComponentValue((HasValue<?, ?>) component,value);

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
