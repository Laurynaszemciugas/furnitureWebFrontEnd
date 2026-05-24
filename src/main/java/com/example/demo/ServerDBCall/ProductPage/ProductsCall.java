package com.example.demo.ServerDBCall.ProductPage;

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

    String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXh4QGdtYWlsLmNvbSIsImlkIjoxLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3OTY1MzgxOSwiZXhwIjoxNzc5Njg5ODE5fQ.VoN9axRnCss7YzTNtTBfsurKypgZnbbEYTw8zbk-HTo";


    public List<ProductFeedModel> getAllProducts(Stock stock, Category category, String prompt, Visibility visibility, int page, int size) throws IOException, InterruptedException {

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


}
