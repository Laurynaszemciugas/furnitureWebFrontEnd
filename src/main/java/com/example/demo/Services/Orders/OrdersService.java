package com.example.demo.Services.Orders;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.OrderAdd.ConsumerData;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.Warnings;
import com.example.demo.ServerDBCall.OrderCalls.OrderCalls;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
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
    HttpCallLogic httpCallLogic;

    Consumer<Boolean> success;
    ObjectMapper mapper = new ObjectMapper();

    public OrdersService(OrderCalls orderCalls, CommonComponents commonComponents, Common common, HttpCallLogic httpCallLogic) {
        this.orderCalls = orderCalls;
        this.commonComponents = commonComponents;
        this.common = common;
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public List<OrdersFeedData> getOrderFeedData(OrderFilterHolder orderFilterHolder){
        return orderCalls.getOrders(orderFilterHolder);
    }

    @SneakyThrows
    public Long getPageCount(OrderFilterHolder orderFilterHolder){
        return orderCalls.getPageCount(orderFilterHolder);
    }

    @SneakyThrows
    public List<ConsumerData> getConsumers(){
        return orderCalls.getConsumer();
    }

    @SneakyThrows
    public void saveNewOrder(Orders orders) {

        check(httpCallLogic.HttpCall("order/saveNewOrder", HttpMethod.POST,orders, ErrorResponse.class),"Orders");
//        try {
//
//            ErrorResponse answer = orderCalls.saveNewOrder(orders);
//            common.customActionsForNotification(answer.getMessage(),answer.getWarning(),"Orders");
//
//        } catch (RuntimeException ex) {
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            ErrorResponse error = mapper.readValue(
//                    ex.getMessage(),
//                    ErrorResponse.class
//            );
//
//            common.customActionsForNotification(error.getMessage(),error.getWarning(),"Orders");
//        }
    }

    @SneakyThrows
    public void saveEditedData(Orders orders){


            check(httpCallLogic.HttpCall("order/saveModifiedOrder", HttpMethod.POST,orders, ErrorResponse.class),null);
           // common.customActionsForNotification(answer.getMessage(),answer.getWarning(),"Orders");







    }


    public void check(ErrorResponse response, String navigateInCaseOfSuccess){

        System.out.println(response.getWarning() + " " + response.getMessage());
        common.customActionsForNotification(response.getMessage(),response.getWarning(),navigateInCaseOfSuccess);

        // only if UI needs to get some information without going to another page
        if(response.getWarning() != Warnings.ERROR && navigateInCaseOfSuccess == null){
            success.accept(true);
        }

    }







    }

