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
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
@Setter
public class OrdersService {

    HttpCallLogic httpCallLogic;

    Consumer<Boolean> success;

    public OrdersService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public List<OrdersFeedData> getOrderFeedData(OrderFilterHolder orderFilterHolder){
        return Arrays.stream(httpCallLogic.HttpCall("order/getAllOrders", HttpMethod.POST,orderFilterHolder, OrdersFeedData[].class,false)).toList();
    }

    @SneakyThrows
    public Long getPageCount(OrderFilterHolder orderFilterHolder){
                return httpCallLogic.HttpCall("order/getAmountOfPages", HttpMethod.POST,orderFilterHolder, Long.class,false);
    }

    @SneakyThrows
    public void saveNewOrder(Orders orders) {

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("order/saveNewOrder", HttpMethod.POST,orders, ErrorResponse.class,false),"Orders",success,true);

    }

    @SneakyThrows
    public void saveEditedData(Orders orders){

            httpCallLogic.checkResponse(
                    httpCallLogic.HttpCall("order/saveModifiedOrder", HttpMethod.POST,orders, ErrorResponse.class,false), null,success,true);

    }

    @SneakyThrows
    public Orders getSelectedOrder(Long id){

          return httpCallLogic.HttpCall("order/getOrderFromId", HttpMethod.GET,id, Orders.class,true);

    }


    }

