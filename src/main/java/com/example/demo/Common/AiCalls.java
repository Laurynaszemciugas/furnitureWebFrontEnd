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
                "YOUR TASK:\n" + "Fill the values of the fields in the PROVIDED JSON.\n\n" + "CRITICAL FIELD RULE:\n" + "- The PROVIDED JSON defines the EXACT fields that must be returned.\n" + "- Return exactly the same fields that exist in the PROVIDED JSON.\n" + "- ONLY replace the values of existing fields.\n" + "- NEVER add new fields.\n" + "- NEVER remove fields.\n" + "- NEVER rename fields.\n" + "- NEVER change the JSON structure.\n" + "- NEVER create fields based only on information found in USER INPUT.\n" + "- If USER INPUT contains information for a field that does not exist in the PROVIDED JSON, IGNORE that information.\n\n" + "OUTPUT RULES:\n" + "- Return ONLY one valid JSON object.\n" + "- Do NOT return markdown.\n" + "- Do NOT return ```json.\n" + "- Do NOT return explanations.\n" + "- Do NOT return comments.\n" + "- Do NOT return text before the JSON.\n" + "- Do NOT return text after the JSON.\n" + "- Do NOT return multiple JSON objects.\n\n" + "VALUE RULES:\n" + "- Replace placeholder/default values with meaningful realistic values.\n" + "- Use USER INPUT as the primary source of information.\n" + "- Correct obvious spelling mistakes in USER INPUT when necessary.\n" + "- Infer missing information only when needed to produce a meaningful value.\n" + "- Do NOT copy placeholder values such as \"None\", 0, 0.0, or example values unless they are genuinely the correct value.\n" + "- Keep the original JSON field names exactly unchanged.\n\n" + "ENUM RULES:\n" + "- An enum field MUST contain exactly ONE enum value.\n" + "- The value MUST be one of the allowed enum values.\n" + "- Copy the enum value EXACTLY as written.\n" + "- NEVER return the list of enum values.\n" + "- NEVER return multiple enum values.\n" + "- NEVER return an array for an enum field.\n" + "- NEVER put [] around an enum value.\n" + "- NEVER put commas inside an enum value.\n" + "- NEVER convert the enum list into the field value.\n\n" + "ENUM EXAMPLE:\n" + "Allowed values:\n" + "[WOOD, METAL, PLASTIC, GLASS]\n\n" + "Correct:\n" + "\"materialType\": \"METAL\"\n\n" + "Incorrect:\n" + "\"materialType\": \"[WOOD, METAL, PLASTIC, GLASS]\"\n\n" + "Incorrect:\n" + "\"materialType\": [\"METAL\"]\n\n" + "Incorrect:\n" + "\"materialType\": \"WOOD, METAL\"\n\n" + "IMPORTANT:\n" + "The list of allowed enum values is RULE INFORMATION, NOT the value to output.\n" + "Choose ONE value from the list and output only that value.\n\n" + "NUMBER RULES:\n" + "- If the PROVIDED JSON contains a numeric placeholder written as 0, return an integer value.\n" + "- If the PROVIDED JSON contains a numeric placeholder written as 0.0, return a decimal value.\n" + "- Do NOT return numbers as strings.\n\n" + "COLOR RULES:\n" + "- If USER INPUT specifies a color, convert it to HEX.\n" + "- If no color is specified, infer a realistic color.\n" + "- materialColor must be a valid HEX color beginning with #.\n\n" + "DATE RULES:\n" + "- deliveryDate must be a realistic future date.\n" + "- Format deliveryDate as YYYY-MM-DD.\n\n" + "FINAL CHECK BEFORE OUTPUT:\n" + "- Same fields as PROVIDED JSON? YES.\n" + "- No additional fields? YES.\n" + "- No missing fields? YES.\n" + "- Every enum field contains exactly ONE enum value? YES.\n" + "- Only valid JSON? YES.\n" + "- No explanation or extra text? YES.\n\n" + "OUTPUT ONLY THE JSON OBJECT."

        );

        stringBuilder.append(
                "\nDo not explain your answer. Generate the JSON object directly."
        );



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



                    showTheValue = defaultObject;



                String text = String.format("\"%s\": \"%s\",",s.getName(),showTheValue);

                stringBuilder.append(text);
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
