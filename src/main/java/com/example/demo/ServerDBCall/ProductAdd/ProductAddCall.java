package com.example.demo.ServerDBCall.ProductAdd;

import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
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
public class ProductAddCall {

    String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXh4QGdtYWlsLmNvbSIsImlkIjoxLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3OTI4MDY1MCwiZXhwIjoxNzc5MzE2NjUwfQ.Xh_QafCzYAU-6u0vQPnqq5YUNCFQugwFB3p3G5rHzmY";

    public void addNewOrder(Product product) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(product);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/product/saveProduct"))
                .header("Authorization","Bearer " + JWT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("Response: " + response.body());

    }














}








