package com.example.demo.Services.DashBoard;


import com.example.demo.ControllerModels.DashBoard.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardService {


    public DashBoardMonthlyOrdersCompleted loadMonthlyDataMiniStatOne(){

        DashBoardMonthlyOrdersCompleted ordersCompletedCompleted = new DashBoardMonthlyOrdersCompleted();
        ordersCompletedCompleted.setThisMonthOrders(0);
        ordersCompletedCompleted.setPreviousMonthOrders(200000);


        return ordersCompletedCompleted;
    }

    public DashBoardMaterialStock loadMaterialStockMiniStatSecond(){

        DashBoardMaterialStock dashBoardMaterialStock = new DashBoardMaterialStock();

        dashBoardMaterialStock.setLowMaterial(20);
        dashBoardMaterialStock.setNoStockMaterial(10);

        return dashBoardMaterialStock;
    }

    public DashBoardMaterialUsageInfo loadMaterialDataMiniStatThree(){

        DashBoardMaterialUsageInfo materialData = new DashBoardMaterialUsageInfo();
        materialData.setMostUsedMaterial("wood");
        materialData.setTotalMaterialsUsed(10);
        materialData.setTotalUsedMaterialCost(40);
        materialData.setLastMonthTotalUsedMaterialCost(2);

        return materialData;

    }

    public DashBoardEmployeeMiniInfo loadEmployeeDataMiniStatFourth(){

        DashBoardEmployeeMiniInfo employeeData = new DashBoardEmployeeMiniInfo();
        employeeData.setTopEmployee("John");
        employeeData.setTopEmployeeProduced(1200L);
        employeeData.setTotalPaidSalary(5000.0);
        employeeData.setTotalUnpaidSalary(800.0);
        employeeData.setTotalPaidLastMonth(7000);
        employeeData.setTotalUnpaidLastMonth(600.0);

        return employeeData;

    }

    public List<ActivityFeedModel> loadActivityList(){

        List<ActivityFeedModel> activityFeedModelList = new ArrayList<>();

        activityFeedModelList.add(new ActivityFeedModel("Created new orderCreated new orderCreated new orderCreated new order", "John", 15L,"green"));
        activityFeedModelList.add(new ActivityFeedModel("Deleted product", "Sarah", 40L,"red"));
        activityFeedModelList.add(new ActivityFeedModel("Updated price", "Mike", 5L,"yellow"));

        return activityFeedModelList;
    }


    public List<MaterialLowNo> loadMaterialLowNoStock(){

        List<MaterialLowNo> materialLowNoList = new ArrayList<>();

        materialLowNoList.add(new MaterialLowNo(123,"Steel Rod", 1L, 20L, 3.50));
        materialLowNoList.add(new MaterialLowNo(3,"Aluminum Sheet", 12L, 20L, 4.20));
        materialLowNoList.add(new MaterialLowNo(123,"Copper Wire", 0L, 15L, 6.10));
        materialLowNoList.add(new MaterialLowNo(1,"Plastic Granules", 8L, 25L, 2.30));

        return materialLowNoList;
    }

    public List<TopEmployeesModel> loadTopEmployees(){

        List<TopEmployeesModel> topEmployeesModelList = new ArrayList<>();

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "images/profile6.png",
                        "Lucas Anderson",
                        1560L,
                        4450.90,
                        190L
                )
        );

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "images/profile7.png",
                        "Olivia Martinez",
                        1340L,
                        3890.40,
                        176L
                )
        );

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "images/profile8.png",
                        "Ethan Thomas",
                        920L,
                        2800.00,
                        158L
                )
        );

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "images/profile9.png",
                        "Sophia White",
                        1685L,
                        4720.35,
                        198L
                )
        );

        return topEmployeesModelList;
    }


    public DashBoardPageData loadDashboardData(){
        DashBoardPageData data = new DashBoardPageData();
        data.setMiniStatOne(loadMonthlyDataMiniStatOne());
        data.setMiniStatTwo(loadMaterialStockMiniStatSecond());
        data.setMiniStatThree(loadMaterialDataMiniStatThree());
        data.setMiniStatFour(loadEmployeeDataMiniStatFourth());
        data.setLoadActivityList(loadActivityList());
        data.setLoadMaterialLowNoStock(loadMaterialLowNoStock());

        return data;
    }





}
