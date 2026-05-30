package com.example.demo.ServerDBCall.OrderCalls;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class OrderCalls {


    SessionCrafter sessionCrafter;

    public OrderCalls() {
        this.sessionCrafter = new SessionCrafter();
    }

    public List<Orders> getAllOders() throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        System.out.println(JWT);

        ObjectMapper mapper = new ObjectMapper();



        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/order/get"))
                .header("Authorization","Bearer " + JWT)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            System.err.println("Backend Request Failed! Status Code: " + response.statusCode());
            System.err.println("Backend Response Body: " + response.body());
            return null;
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<List<Orders>>() {}
        );

    }


    public Orders getAnOrderFromId(Long id) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        System.out.println(JWT);

        ObjectMapper mapper = new ObjectMapper();


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/order/getOrderFromId/" + id))
                .header("Authorization","Bearer " + JWT)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            System.err.println("Backend Request Failed! Status Code: " + response.statusCode());
            System.err.println("Backend Response Body: " + response.body());
            return null;
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<Orders>() {}
        );

    }


    public String saveModifiedOrder(Orders order) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        System.out.println(JWT);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/order/saveModifiedOrder"))
                .header("Authorization","Bearer " + JWT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            System.err.println("Backend Request Failed! Status Code: " + response.statusCode());
            System.err.println("Backend Response Body: " + response.body());
            return null;
        }

        return response.body();


    }









}
