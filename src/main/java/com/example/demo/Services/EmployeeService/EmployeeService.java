package com.example.demo.Services.EmployeeService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.CommonDtos.Employee;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.Employee.EmployeeBriefDto;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Employee.EmployeeFilterHolder;
import com.example.demo.DTOS.ComboBoxEmployees;
import com.example.demo.DTOS.ComboBoxMaterial;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
@Setter
public class EmployeeService {

    HttpCallLogic httpCallLogic;

    Consumer<Boolean> success;

    public EmployeeService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public List<ComboBoxEmployees> getMiniEmployeeData() {
        return Arrays.stream(httpCallLogic.HttpCall("employee/getMiniEmployeeData", HttpMethod.GET,null, ComboBoxEmployees[].class,false)).toList();
    }


    @SneakyThrows
    public MiniStatHolder getMiniStats(LocalDate from, LocalDate to) {
        return httpCallLogic.HttpCall("employee/getEmployeeeMiniStats", HttpMethod.GET,String.format("%s/%s",from,to), MiniStatHolder.class,true);
    }

    @SneakyThrows
    public List<EmployeeBriefDto> getEmployeeFeed(EmployeeFilterHolder employeeFilterHolder, String jwt) {
        return Arrays.stream(httpCallLogic.HttpCallWithJwt("employee/getAllEmployeeForFeed", HttpMethod.POST,employeeFilterHolder, EmployeeBriefDto[].class,false,jwt)).toList();
    }

    @SneakyThrows
    public Long getPageCount(EmployeeFilterHolder employeeFilterHolder) {
        return httpCallLogic.HttpCall("employee/getTotalPages", HttpMethod.POST,employeeFilterHolder, Long.class,false);
    }


    @SneakyThrows
    public void saveNewEmployee(Employee employee) {
        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("employee/saveNewEmployee", HttpMethod.POST, employee, ErrorResponse.class,false),null,success,true);
    }

    @SneakyThrows
    public void editExistingEmployee(Employee employee) {
        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("employee/editEmployee", HttpMethod.POST, employee, ErrorResponse.class,false),null,success,true);
    }

    @SneakyThrows
    public void deleteEmployee(Long id) {
        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("employee/deleteEmployee", HttpMethod.GET, id, ErrorResponse.class,true),null,success,true);
    }

    @SneakyThrows
    public Employee getEmployee(Long id) {
        return httpCallLogic.HttpCall("employee/getEmployee", HttpMethod.GET,id, Employee.class,true);
    }



}
