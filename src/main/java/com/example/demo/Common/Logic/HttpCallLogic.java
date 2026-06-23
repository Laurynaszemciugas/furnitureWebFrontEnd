package com.example.demo.Common.Logic;

import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class HttpCallLogic {

    SessionCrafter sessionCrafter;

    public HttpCallLogic() {
        this.sessionCrafter = new SessionCrafter();
    }

    String baseURL = "http://localhost:8080/api/";


    ObjectMapper mapper = new ObjectMapper();
    HttpClient client = HttpClient.newHttpClient();

    @SneakyThrows
    public <R,T> R HttpCall(String endpoint, HttpMethod httpMethod, T data, Class<R> responseType){

        String jwt = sessionCrafter.extractSession("JWT", String.class);

        HttpRequest.BodyPublisher bodyPublisher;

        if (data == null || httpMethod == HttpMethod.GET || httpMethod == HttpMethod.DELETE) {
            bodyPublisher = HttpRequest.BodyPublishers.noBody();
        }
        else {
            String jsonBody = mapper.writeValueAsString(data);
            bodyPublisher = HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8);
        }


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + endpoint))
                .header("Authorization","Bearer " + jwt)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(httpMethod.name(), bodyPublisher)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            mapper.readValue(response.body(),responseType);
        }

        return mapper.readValue(response.body(),responseType);

    }




}
