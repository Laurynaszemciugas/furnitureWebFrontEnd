package com.example.demo.ControllerModels.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    List<ActivityFeedModel> loadActivityList;
    List<MaterialLowNo> loadMaterialLowNoStock;
    List<TopEmployeesModel> loadTopEmployees;


}
