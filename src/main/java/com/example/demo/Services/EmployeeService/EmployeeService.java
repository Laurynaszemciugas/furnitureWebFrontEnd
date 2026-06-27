package com.example.demo.Services.EmployeeService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.DTOS.ComboBoxMaterial;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    HttpCallLogic httpCallLogic;

    public EmployeeService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public List<ComboBoxEmployees> getMiniEmployeeData() {
        return Arrays.stream(httpCallLogic.HttpCall("employee/getMiniEmployeeData", HttpMethod.GET,null, ComboBoxEmployees[].class,false)).toList();
    }


}
