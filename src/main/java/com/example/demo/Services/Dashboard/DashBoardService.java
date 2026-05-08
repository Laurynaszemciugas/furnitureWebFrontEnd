package com.example.demo.Services.Dashboard;


import com.example.demo.ControllerModels.DashBoard.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                        "Screenshot 2026-04-27 001745.png",
                        "Lucas Anderson",
                        1560L,
                        2,
                        190L,
                        1
                )
        );

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "Screenshot 2026-04-27 001745.png",
                        "Olivia Martinez",
                        1340L,
                        24,
                        176L
                        ,123
                )
        );

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "Screenshot 2026-04-27 001745.png",
                        "Ethan Thomas",
                        920L,
                        10,
                        158L,
                        23
                )
        );

        topEmployeesModelList.add(
                new TopEmployeesModel(
                        "Screenshot 2026-04-27 001745.png",
                        "Sophia White",
                        1685L,
                        22,
                        198L,
                        44
                )
        );

        return topEmployeesModelList;
    }

    public List<String> quickActionList(){
        List<String> list = new ArrayList<>();

        list.add("DashBoard");
        list.add("Products");
        list.add("Add New Order");

        return list;
    }


    public List<DashBoardGraphData> dashBoardGraphDataList(){

        List<DashBoardGraphData> list = new ArrayList<>();
        list.add(new DashBoardGraphData("2025-12-10",45));
        list.add(new DashBoardGraphData("2025-02-10",100));
        list.add(new DashBoardGraphData("2025-12-10",45));

        return list;

    }

    public DashBoardPageData loadDashboardData(){
        DashBoardPageData data = new DashBoardPageData();
        data.setMiniStatOne(loadMonthlyDataMiniStatOne());
        data.setMiniStatTwo(loadMaterialStockMiniStatSecond());
        data.setMiniStatThree(loadMaterialDataMiniStatThree());
        data.setMiniStatFour(loadEmployeeDataMiniStatFourth());
        data.setGraphData(dashBoardGraphDataList());
        data.setLoadActivityList(loadActivityList());
        data.setLoadMaterialLowNoStock(loadMaterialLowNoStock());
        data.setLoadTopEmployees(loadTopEmployees());
        data.setLoadQuickActions(quickActionList());

        data.setCreatedAt(LocalDateTime.now());

        return data;
    }





}
