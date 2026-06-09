package com.example.demo.Services.Orders;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.OrderAdd.ConsumerData;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Service
@Setter
public class OrdersService {

    OrderCalls orderCalls;
    CommonComponents commonComponents;
    Common common;

    Consumer<String> orderIsSaved;

    public OrdersService(OrderCalls orderCalls, CommonComponents commonComponents, Common common) {
        this.orderCalls = orderCalls;
        this.commonComponents = commonComponents;
        this.common = common;
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
    public void saveNewOrder(Orders orders) {

        try {

            ErrorResponse answer = orderCalls.saveNewOrder(orders);
            common.customActionsForNotification(answer.getMessage(),answer.getWarning(),"Orders");

        } catch (RuntimeException ex) {

            ObjectMapper mapper = new ObjectMapper();

            ErrorResponse error = mapper.readValue(
                    ex.getMessage(),
                    ErrorResponse.class
            );

            common.customActionsForNotification(error.getMessage(),error.getWarning(),"Orders");
        }
    }

    @SneakyThrows
    public void saveEditedData(Orders orders){

        try{

            ErrorResponse answer = orderCalls.saveModifiedOrder(orders);
            common.customActionsForNotification(answer.getMessage(),answer.getWarning(),"Orders");
            orderIsSaved.accept("yep");

        } catch (RuntimeException ex) {
            ObjectMapper mapper = new ObjectMapper();

            ErrorResponse error = mapper.readValue(
                    ex.getMessage(),
                    ErrorResponse.class
            );

            common.customActionsForNotification(error.getMessage(),error.getWarning(),"Orders");
        }


    }





    }

