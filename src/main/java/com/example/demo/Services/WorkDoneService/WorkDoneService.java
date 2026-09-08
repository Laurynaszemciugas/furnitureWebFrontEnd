package com.example.demo.Services.WorkDoneService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.WorkDay;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.User.ProfileInformation;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class WorkDoneService {


    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public WorkDoneService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }


    public void addWorkDay() {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("WorkDay/addWorkDay", HttpMethod.GET, null, ErrorResponse.class,false),"EmployeesDashBoard",success,true);

    }


    public WorkDay getWorkDayInfo() {

        return httpCallLogic.HttpCall("WorkDay/getWorkDayInfo", HttpMethod.GET, null, WorkDay.class,false);

    }



}
