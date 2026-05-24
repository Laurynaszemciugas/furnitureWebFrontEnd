package com.example.demo.ServerDBCall.OrderCalls;

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


    String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXh4QGdtYWlsLmNvbSIsImlkIjoxLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3OTYxNzQwNywiZXhwIjoxNzc5NjUzNDA3fQ.OdmsAtbgCQHJMmhEXhJ-ee6C8OPvigTudY1zXHrOdZU";

    public List<Orders> getAllOders() throws IOException, InterruptedException {

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


    public List<Product> getProducts() throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();



        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/order/getP"))
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
                new TypeReference<List<Product>>() {}
        );

    }


}
