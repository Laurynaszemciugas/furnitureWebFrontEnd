package com.example.demo.ControllerModels.Filter.ActionLog;

import com.example.demo.Enums.ActionDesciptionEnum;
import com.example.demo.Enums.ActionTrackerEnum;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActionLogFilterHolder {

    private ActionTrackerEnum whoMadeTheAction = ActionTrackerEnum.ALL;
    private String promt = "ALL";

    private ActionDesciptionEnum actionType = ActionDesciptionEnum.ALL;
    private LocalDate dateFrom = LocalDate.of(1000,12,12);
    private LocalDate dateTo = LocalDate.of(1000,12,12);


    private int page = 0;
    private int pageCount = 5;

}
