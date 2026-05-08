package com.example.demo.ControllerModels.DashBoard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityFeedModel {

    private String actionDescription;
    private String whoMadeIt;
    private long howLongAgoMinutes;
    private String color;

}
