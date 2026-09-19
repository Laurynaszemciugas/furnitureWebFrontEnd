package com.example.demo.ControllerModels.Filter.EmployeeAvailableOrderFilter;

import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.Priority;
import com.example.demo.Enums.SortOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAvailableOrderFilter {

    private String promt = "ALL";
    private OrderStatus orderStatus = OrderStatus.ALL;
    private Priority priority = Priority.ALL;
    private SortOrder sortOrder = SortOrder.ALL;


    private int page = 0;
    private int pageCount = 5;
}
