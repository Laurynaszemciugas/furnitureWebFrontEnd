package com.example.demo.Services;


import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.CreateReport.Report;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Setter
public class CustomReportService {

    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public CustomReportService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public void saveNewReport(Report report){

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("customReport/createNewCustomReport", HttpMethod.POST,report, ErrorResponse.class,false), null,success,true);

    }

}
