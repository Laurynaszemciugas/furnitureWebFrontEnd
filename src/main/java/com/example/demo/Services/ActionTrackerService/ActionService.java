package com.example.demo.Services.ActionTrackerService;


import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.ActionLogs.ActionLogFeed;
import com.example.demo.ControllerModels.CommonDtos.ActionTracker;
import com.example.demo.ControllerModels.Filter.ActionLog.ActionLogFilterHolder;
import com.example.demo.ControllerModels.Filter.Material.MaterialFilterHolder;
import com.example.demo.ControllerModels.Material.MaterialBriefDto;
import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ActionService {


    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public ActionService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }



    public List<ActionLogFeed> getActionLogFeed(ActionLogFilterHolder actionLogFilterHolder, String jwt) {

        return Arrays.stream(httpCallLogic.HttpCallWithJwt("ActionTracker/getActionLogFeed", HttpMethod.POST,actionLogFilterHolder, ActionLogFeed[].class,false,jwt)).toList();

    }

    @SneakyThrows
    public Long getAmountOfPages(ActionLogFilterHolder actionLogFilterHolder) {

        return httpCallLogic.HttpCall("ActionTracker/getAmountOfPages", HttpMethod.POST,actionLogFilterHolder, Long.class,false);

    }



}
