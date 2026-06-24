package com.example.demo.Common.Logic;

import com.example.demo.Common.Common;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.Warnings;
import com.example.demo.Exseptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
public class HttpCallLogic {

    Common common;
    SessionCrafter sessionCrafter;

    public HttpCallLogic(Common common) {
        this.common = common;
        this.sessionCrafter = new SessionCrafter();
    }

    String baseURL = "http://localhost:8080/api/";


    ObjectMapper mapper = new ObjectMapper();
    HttpClient client = HttpClient.newHttpClient();

    @SneakyThrows
    public <R,T> R HttpCall(String endpoint, HttpMethod httpMethod, T data, Class<R> responseType, boolean pathVariable ){

        String jwt = sessionCrafter.extractSession("JWT", String.class);

        HttpRequest.BodyPublisher bodyPublisher;

        String pathValue = "";

        if(pathVariable){
            pathValue = "/" + data;
        }

        //get full path
        String fullUrl = baseURL + endpoint + pathValue;


        if (data == null || httpMethod == HttpMethod.GET || httpMethod == HttpMethod.DELETE) {
            bodyPublisher = HttpRequest.BodyPublishers.noBody();
        }
        else {
            String jsonBody = mapper.writeValueAsString(data);
            bodyPublisher = HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8);
        }



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + endpoint + pathValue))
                .header("Authorization","Bearer " + jwt)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(httpMethod.name(), bodyPublisher)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //get data response
        String body = response.body();
        int status = response.statusCode();

        // small data show
        System.out.println("=============================" + common.dateFormatter(LocalDateTime.now(),"MMMM d, yyyy ● h:mma") + "=============================");
        System.out.println("HTTP CALL: " + httpMethod + " " + fullUrl);
        System.out.println("STATUS: " + status);
        System.out.println("BODY LENGTH: " + (body == null ? "null" : body.length()));
        String bod = body.length() > 100 ? " Body to long " : body;
        System.out.println("BODY: >>>" + bod + "<<<");

        boolean successStatus = status >= 200 && status < 300;

        if(status == 401){
            common.customNavigate("Login");
            throw new UnauthorizedException("Session expired. Please login again.");

        }

        if (!successStatus) {




            if (body == null || body.isBlank()) {
                throw new RuntimeException("HTTP error " + status + " from " + fullUrl + " with empty body");
            }

            if (responseType == ErrorResponse.class) {
                return mapper.readValue(body, responseType);
            }

            throw new RuntimeException("HTTP error " + status + " from " + fullUrl + " body: " + body);
        }

        if (body == null || body.isBlank()) {

            if (responseType.isArray()) {
                Object emptyArray = Array.newInstance(responseType.getComponentType(), 0);
                return responseType.cast(emptyArray);
            }

            return null;
        }

        if (responseType == String.class) {
            return responseType.cast(body);
        }

        return mapper.readValue(body, responseType);

    }




    public <T> void checkResponse(ErrorResponse response, String navigateInCaseOfSuccess, Consumer<T> consumer, T valueInCaseOfSuccess){

        common.customActionsForNotification(response.getMessage(),response.getWarning(),navigateInCaseOfSuccess,true);

        // only if UI needs to get some information without going to another page
        if(response.getWarning() != Warnings.ERROR && navigateInCaseOfSuccess == null){
            consumer.accept(valueInCaseOfSuccess);
        }

    }

    public <T,R> R checkResponseNoGetValue(ErrorResponse response, String navigateInCaseOfSuccess){

        common.customActionsForNotification(response.getMessage(),response.getWarning(),navigateInCaseOfSuccess,false);

        if(response.getWarning() == Warnings.OK){
            return (R) response.getMessage();

        }

        return null;

    }

    private <R> R getDefaultValue(Class<R> responseType) {

        if (responseType.isArray()) {
            Object emptyArray = Array.newInstance(responseType.getComponentType(), 0);
            return responseType.cast(emptyArray);
        }

        if (responseType == String.class) {
            return responseType.cast("");
        }

        if (responseType == Long.class) {
            return responseType.cast(1L);
        }

        if (responseType == Integer.class) {
            return responseType.cast(0);
        }

        if (responseType == Boolean.class) {
            return responseType.cast(false);
        }

        return null;
    }



}
