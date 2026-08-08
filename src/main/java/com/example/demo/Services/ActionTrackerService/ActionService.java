package com.example.demo.Services.ActionTrackerService;


import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.ActionTracker;
import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Consumer;

@Service
public class ActionService {


    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public ActionService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }



}
