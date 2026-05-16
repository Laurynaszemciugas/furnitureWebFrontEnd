package com.example.demo.ServerCall.ProductAdd;

import com.example.demo.ControllerModels.CommonDtos.Product;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class ProductAddCall {


    public void addNewOrder(Product product) throws IOException, InterruptedException {





        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(product);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/product/saveProduct")) // your endpoint
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
