package com.example.demo.ControllerModels.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardEmployeeMiniInfo {

    String topEmployee;
    long topEmployeeProduced;
    double totalPaidSalary;
    double totalUnpaidSalary;
    double totalPaidLastMonth;
    double totalUnpaidLastMonth;



    public boolean isEmpty() {
        return topEmployee == null
                && topEmployeeProduced == 0
                && totalPaidSalary == 0
                && totalUnpaidSalary == 0
                && totalPaidLastMonth == 0
                && totalUnpaidLastMonth == 0;
    }


}
