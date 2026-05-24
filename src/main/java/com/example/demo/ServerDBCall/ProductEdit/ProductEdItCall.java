package com.example.demo.ServerDBCall.ProductEdit;

import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Service
public class ProductEdItCall {

    String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXh4QGdtYWlsLmNvbSIsImlkIjoxLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3OTY1MzgxOSwiZXhwIjoxNzc5Njg5ODE5fQ.VoN9axRnCss7YzTNtTBfsurKypgZnbbEYTw8zbk-HTo";



    public Product getProductAccordingToId(Long id) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();


        String url = "http://localhost:8080/api/product/getProductToId/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Authorization","Bearer " + JWT)
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
                new TypeReference<Product>() {}
        );
    }

    public void updateProductEdit(Product product) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(product);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/product/editProduct"))
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
            throw new RuntimeException("Backend responded with error status: " + response.statusCode());
        }

        System.out.println(response.body());

    }









}
