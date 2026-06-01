package com.example.demo.Services.Orders;

import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdersService {

    OrderCalls orderCalls;

    public OrdersService(OrderCalls orderCalls) {
        this.orderCalls = orderCalls;
    }

    @SneakyThrows
    public List<OrdersFeedData> getOrderFeedData(
            OrderStatus orderStatusChoice,
            Double priceFromChoice,
            Double priceToChoice,
            LocalDate dateFromChoice,
            LocalDate dateToChoice,
            Long amountOfProductsChoice,
            int pageChoice,
            int pageCountChoice){
        return orderCalls.getOrders(orderStatusChoice,priceFromChoice,priceToChoice,dateFromChoice,dateToChoice,amountOfProductsChoice,pageChoice,pageCountChoice);
    }

}
