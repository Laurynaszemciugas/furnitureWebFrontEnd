package com.example.demo.Services.WorkDoneService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Entity.WorkDay;
import com.example.demo.Entity.WorkDone;
import com.example.demo.DTOS.Error.ErrorResponse;
import com.example.demo.DTOS.WorkDay.WorkDayMiniStats;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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


    public List<WorkDone> allInfoAboutSpecificWorkDay() {

        return Arrays.stream(httpCallLogic.HttpCall("WorkDay/allInfoAboutSpecificWorkDay", HttpMethod.GET, null, WorkDone[].class,false)).toList();

    }

    public WorkDayMiniStats getQuickActionWorkHoursMiniStats(Long id) {

        return httpCallLogic.HttpCall("WorkDone/getQuickActionWorkHoursMiniStats", HttpMethod.GET, id, WorkDayMiniStats.class,true);

    }

    public List<WorkDay> allInfoAccordingToEmployee(Long id, LocalDate from, LocalDate to, int page) {

        return Arrays.stream(httpCallLogic.HttpCall("WorkDone/allInfoAccordingToEmployee", HttpMethod.GET, String.format("%d/%s/%s/%d",id,from,to,page), WorkDay[].class,true)).toList();

    }

    public Long getTotalPagesInQuickActions(Long id, LocalDate from, LocalDate to) {

        return httpCallLogic.HttpCall("WorkDone/getTotalPagesInQuickActions", HttpMethod.GET,String.format("%d/%s/%s",id,from,to), Long.class,true);

    }




}
