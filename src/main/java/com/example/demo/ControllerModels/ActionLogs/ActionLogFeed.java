package com.example.demo.ControllerModels.ActionLogs;

import com.example.demo.ControllerModels.CommonDtos.User;
import com.example.demo.Enums.ActionDesciptionEnum;
import com.example.demo.Enums.ActionTrackerEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionLogFeed {


    private String actionName;
    private User whoMadeIt;
    private ActionTrackerEnum typeOfActionRecorded;
    private ActionDesciptionEnum action;
    private LocalDateTime created;


}
