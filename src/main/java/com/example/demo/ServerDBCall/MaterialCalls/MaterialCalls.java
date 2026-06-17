package com.example.demo.ServerDBCall.MaterialCalls;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.ControllerModels.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialMiniStat;
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
public class MaterialCalls {

    SessionCrafter sessionCrafter;

    public MaterialCalls() {
        this.sessionCrafter = new SessionCrafter();
    }

    public List<MaterialBriefDto> getMaterials(MaterialFilterHolder materialFilterHolder) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);


        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(materialFilterHolder);


        HttpClient client = HttpClient.newHttpClient();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/material/getAllMaterialForFeed"))
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
            return null;
        }

        return mapper.readValue(
                response.body(),
                new TypeReference<List<MaterialBriefDto>>() {}
        );

    }


    public MaterialMiniStat getMiniStatData(LocalDate fromDate, LocalDate toDate) throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);


        ObjectMapper mapper = new ObjectMapper();

        String url = String.format("http://localhost:8080/api/material/getMaterialMiniStats/%s/%s",fromDate,toDate);


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
                new TypeReference<MaterialMiniStat>() {}
        );

    }



}
