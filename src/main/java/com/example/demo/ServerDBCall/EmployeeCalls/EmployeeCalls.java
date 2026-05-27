package com.example.demo.ServerDBCall.EmployeeCalls;

import com.example.demo.Common.Logic.SessionCrafter;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.DTOS.ComboBoxEmployees;
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
public class EmployeeCalls {

    SessionCrafter sessionCrafter;

    public EmployeeCalls() {
        this.sessionCrafter = new SessionCrafter();
    }




    public List<ComboBoxEmployees> getMiniEmployeeData() throws IOException, InterruptedException {

        String JWT = sessionCrafter.extractSession("JWT", String.class);

        System.out.println(JWT);

        ObjectMapper mapper = new ObjectMapper();



        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/employee/getMiniEmployeeData"))
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
                new TypeReference<List<ComboBoxEmployees>>() {}
        );

    }

}
