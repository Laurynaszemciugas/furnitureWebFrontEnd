package com.example.demo.ControllerModels;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardMaterialUsageInfo {


    private String mostUsedMaterial;
    private long totalMaterialsUsed;
    private double totalUsedMaterialCost;
    private double lastMonthTotalUsedMaterialCost;


}
