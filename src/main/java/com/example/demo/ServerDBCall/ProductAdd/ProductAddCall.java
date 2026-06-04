package com.example.demo.ServerDBCall.ProductAdd;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
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

    SessionCrafter sessionCrafter;

    public ProductAddCall() {
        this.sessionCrafter = new SessionCrafter();
    }

    public void addNewOrder(Product product) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

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








