package com.example.demo.ServerCall.ProductAdd;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductAddCall {

    String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXh4QGdtYWlsLmNvbSIsImlkIjoxLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3OTAzMTY4MywiZXhwIjoxNzc5MDY3NjgzfQ.CRLX9KrX-QMOkLTlv7amcRc84tYifZ_MvfVJgQXPpBg";

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


    public List<ProductFeedModel> getAllProducts(Stock stock, Category category, int page, int size) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();


        // 2. Keep the port at 8080 since that is your backend
        String url = String.format(
                "http://localhost:8080/api/product/getProducts/%s/%s/%d/%d",
                stock,
                category,
                page,
                size
        );

        // 3. CRITICAL: Pass the Authorization header just like you did in addNewOrder
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        // 4. Safely check the HTTP response status before handing it to Jackson
        if (response.statusCode() != 200) {
            System.err.println("Backend Request Failed! Status Code: " + response.statusCode());
            System.err.println("Backend Response Body: " + response.body());
            throw new RuntimeException("Backend responded with error status: " + response.statusCode());
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<List<ProductFeedModel>>() {}
        );
    }












}








