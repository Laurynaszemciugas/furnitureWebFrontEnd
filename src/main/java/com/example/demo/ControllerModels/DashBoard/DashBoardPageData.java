package com.example.demo.ControllerModels.DashBoard;

import com.example.demo.ControllerModels.Common.GraphDataDateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardPageData {

    DashBoardMonthlyOrdersCompleted miniStatOne;
    DashBoardMaterialStock miniStatTwo;
    DashBoardMaterialUsageInfo miniStatThree;
    DashBoardEmployeeMiniInfo miniStatFour;
    List<GraphDataDateValue> graphData;
    List<ActivityFeedModel> loadActivityList;
    List<MaterialLowNo> loadMaterialLowNoStock;
    List<TopEmployeesModel> loadTopEmployees;
    List<String> loadQuickActions;

    LocalDateTime createdAt;

    public boolean isDataStale(){

        if (createdAt == null) {
            System.out.println("no data found");
            return true;
        }


        long minutes = ChronoUnit.MINUTES.between(createdAt, LocalDateTime.now());

        return minutes > 1;
    }


}
