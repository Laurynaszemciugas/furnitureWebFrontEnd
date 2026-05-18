package com.example.demo.ServerDBCall.CommonCalls;

import com.example.demo.ControllerModels.Products.ProductFeedModel;
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
public class CommonCalls {
    String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXh4QGdtYWlsLmNvbSIsImlkIjoxLCJyb2xlIjoiVVNFUiIsImlhdCI6MTc3OTEzOTMxNCwiZXhwIjoxNzc5MTc1MzE0fQ.hbCDaJA8wc-EyTpcUXCnTI3rUXVxya5tPAG384OQ7hg";
    public List<String> getMaterialNames() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/material/getMaterialNames"))
                .header("Authorization","Bearer " + JWT)
                .header("Accept", "application/json")
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

        System.out.println(response.body());

        return mapper.readValue(
                response.body(),
                new TypeReference<List<String>>() {}
        );
    }

}
