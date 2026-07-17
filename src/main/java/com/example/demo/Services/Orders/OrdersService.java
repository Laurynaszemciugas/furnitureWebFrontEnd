package com.example.demo.Services.Orders;

import com.example.demo.Common.Common;
import com.example.demo.Common.CommonComponents;
import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.Common.GraphDataDateValue;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.CommonDtos.Orders;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.OrderAdd.ConsumerData;
import com.example.demo.ControllerModels.Orders.NewOrderFeedData;
import com.example.demo.ControllerModels.Orders.OrderReportPieChart;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.Warnings;
import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
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
    public List<OrdersFeedData> getOrderFeedData(OrderFilterHolder orderFilterHolder,String jwt){
        return Arrays.stream(httpCallLogic.HttpCallWithJwt("order/getAllOrders", HttpMethod.POST,orderFilterHolder, OrdersFeedData[].class,false,jwt)).toList();
    }

    @SneakyThrows
    public List<OrdersFeedData> getNewOrderFeed(OrderFilterHolder orderFilterHolder){
        return Arrays.stream(httpCallLogic.HttpCall("order/getAllNewOrders", HttpMethod.POST,orderFilterHolder, OrdersFeedData[].class,false)).toList();
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


    @SneakyThrows
    public MiniStatHolder getMiniStats(LocalDate fromDate, LocalDate toDate) {

        return httpCallLogic.HttpCall("order/getMiniStats", HttpMethod.GET,String.format("%s/%s",fromDate,toDate), MiniStatHolder.class,true);

    }

    @SneakyThrows
    public Long getNewOrderCount() {

        return httpCallLogic.HttpCall("order/getNewOrderCount", HttpMethod.GET,"", Long.class,false);

    }

    @SneakyThrows
    public List<NewOrderFeedData> getSelectedNewOrdersGridData(Long id) {

        return Arrays.stream(httpCallLogic.HttpCall("order/getGridStuff", HttpMethod.GET,id, NewOrderFeedData[].class,true)).toList();

    }

    @SneakyThrows
    public void rejectNewOrder(Long id){

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("order/rejectNewOrder", HttpMethod.GET,id, ErrorResponse.class,true), null,success,true);

    }

    @SneakyThrows
    public void acceptNewOrder(Long id){

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("order/acceptNewOrder", HttpMethod.GET,id, ErrorResponse.class,true), null,success,true);

    }

    // Order report
    @SneakyThrows
    public OrderReportPieChart getReportPagePieChart(LocalDate fromDate, LocalDate toDate) {

        return httpCallLogic.HttpCall("order/getOrderByStatus", HttpMethod.GET,String.format("%s/%s",fromDate,toDate), OrderReportPieChart.class,true);

    }

    @SneakyThrows
    public List<GraphDataDateValue> getReportPageLineChart(LocalDate fromDate, LocalDate toDate) {

        return Arrays.stream(httpCallLogic.HttpCall("order/getOrderByLineChart", HttpMethod.GET,String.format("%s/%s",fromDate,toDate), GraphDataDateValue[].class,true)).toList();

    }

    @SneakyThrows
    public ReportMiniStatHolder getOrderMiniStatData(LocalDate fromDate, LocalDate toDate) {

        return httpCallLogic.HttpCall("order/getOrderMiniStatData", HttpMethod.GET,String.format("%s/%s",fromDate,toDate), ReportMiniStatHolder.class,true);

    }


    }

