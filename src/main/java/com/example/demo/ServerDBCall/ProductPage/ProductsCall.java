package com.example.demo.ServerDBCall.ProductPage;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
public class ProductsCall {


    SessionCrafter sessionCrafter;

    public ProductsCall() {
        this.sessionCrafter = new SessionCrafter();
    }

    public List<ProductFeedModel> getAllProducts(Stock stock, Category category, String prompt, Visibility visibility, int page, int size) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();


        String url = String.format(
                "http://localhost:8080/api/product/getProducts/%s/%s/%s/%s/%d/%d",
                stock,
                category,
                prompt,
                visibility,
                page,
                size
        );


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
            throw new RuntimeException("Backend responded with error status: " + response.statusCode());
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<List<ProductFeedModel>>() {}
        );
    }



    public Long getProductPages(Stock stock, Category category, String prompt, Visibility visibility) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        HttpClient client = HttpClient.newHttpClient();

        String url = String.format(
                "http://localhost:8080/api/product/getPages/%s/%s/%s/%s",
                stock,
                category,
                prompt,
                visibility
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
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
            throw new RuntimeException("Backend responded with error status: " + response.statusCode());
        }

        return Long.valueOf(response.body());

    }

    @SneakyThrows
    public String removeProduct(Long id){

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/product/removeProduct"))
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer " + JWT)
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

        return response.body();

    }


    public List<OrderAddProducts> getProductsForAddOrder() throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        ObjectMapper mapper = new ObjectMapper();


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/product/getProductsForAddOrder"))
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
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<List<OrderAddProducts>>() {}
        );

    }


    public List<OrderAddProducts> getExistingData(Long id) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        ObjectMapper mapper = new ObjectMapper();


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/product/getExistingData/" + id))
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
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<List<OrderAddProducts>>() {}
        );

    }


}
