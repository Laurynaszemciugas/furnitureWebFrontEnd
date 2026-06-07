package com.example.demo.Services.Orders;

import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.OrderAdd.ConsumerData;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdersService {

    OrderCalls orderCalls;
    CommonComponents commonComponents;

    public OrdersService(OrderCalls orderCalls, CommonComponents commonComponents) {
        this.orderCalls = orderCalls;
        this.commonComponents = commonComponents;
    }

    @SneakyThrows
    public List<OrdersFeedData> getOrderFeedData(
            OrderStatus orderStatusChoice,
            Double priceFromChoice,
            Double priceToChoice,
            LocalDate dateFromChoice,
            LocalDate dateToChoice,
            Long amountOfProductsChoice,
            String promtChoice,
            int pageChoice,
            int pageCountChoice){
        return orderCalls.getOrders(orderStatusChoice,priceFromChoice,priceToChoice,dateFromChoice,dateToChoice,amountOfProductsChoice,promtChoice,pageChoice,pageCountChoice);
    }

    @SneakyThrows
    public Long getPageCount(
            OrderStatus orderStatusChoice,
            Double priceFromChoice,
            Double priceToChoice,
            LocalDate dateFromChoice,
            LocalDate dateToChoice,
            Long amountOfProductsChoice,
            String promtChoice
            ){
        return orderCalls.getPageCount(orderStatusChoice,priceFromChoice,priceToChoice,dateFromChoice,dateToChoice,amountOfProductsChoice,promtChoice);
    }

    @SneakyThrows
    public List<ConsumerData> getConsumers(){
        return orderCalls.getConsumer();
    }

    @SneakyThrows
    public void saveNewOrder(Orders orders){

        try{
            String answer = orderCalls.saveNewOrder(orders);
            commonComponents.showNotification(answer, 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            commonComponents.showNotification(e.getMessage(), 3000, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_ERROR);
        }

    }

}
