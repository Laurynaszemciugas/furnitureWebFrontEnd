package com.example.demo.ServerDBCall.OrderCalls;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderCalls {


    SessionCrafter sessionCrafter;

    public OrderCalls() {
        this.sessionCrafter = new SessionCrafter();
    }

    public List<OrdersFeedData> getOrders(
                                           OrderStatus orderStatusChoice,
                                           Double priceFromChoice,
                                           Double priceToChoice,
                                           LocalDate dateFromChoice,
                                           LocalDate dateToChoice,
                                           Long amountOfProductsChoice,
                                           int pageChoice,
                                           int pageCountChoice) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);


        ObjectMapper mapper = new ObjectMapper();

        String url = String.format(
                "http://localhost:8080/api/order/getAllOrders/%s/%f/%f/%s/%s/%d/%d/%d",
                orderStatusChoice,
                priceFromChoice,
                priceToChoice,
                dateFromChoice,
                dateToChoice,
                amountOfProductsChoice,
                pageChoice,
                pageCountChoice
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
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
                new TypeReference<List<OrdersFeedData>>() {}
        );

    }


    public Long getPageCount(
            OrderStatus orderStatusChoice,
            Double priceFromChoice,
            Double priceToChoice,
            LocalDate dateFromChoice,
            LocalDate dateToChoice,
            Long amountOfProductsChoice) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);


        ObjectMapper mapper = new ObjectMapper();

        String url = String.format(
                "http://localhost:8080/api/order/getAmountOfPages/%s/%f/%f/%s/%s/%d",
                orderStatusChoice,
                priceFromChoice,
                priceToChoice,
                dateFromChoice,
                dateToChoice,
                amountOfProductsChoice
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
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
                new TypeReference<Long>() {}
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
            throw new RuntimeException(response.body());
        }

        return response.body();


    }









}
