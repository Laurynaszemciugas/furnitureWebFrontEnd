package com.example.demo.Services.Products;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.Common.GraphDataLongValue;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.ControllerModels.Filter.Prodcut.ProductFilterHolder;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.ControllerModels.Products.ProductFeedModel;

import com.example.demo.Pages.Reports.Common.ReportMiniStatHolder;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO.ProductLowStockList;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO.ProductPerformanceReport;
import com.example.demo.Pages.Reports.ReportsPages.ProductReportpPage.DTO.ProductReportPieChart;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
@Setter
public class ProductService {


    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public ProductService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    // good for specific data need
    @SneakyThrows
    public List<ProductFeedModel> loadProductFeedModel(ProductFilterHolder productFilterHolder, String jwt) {
        return Arrays.stream(
                httpCallLogic.HttpCallWithJwt("product/getProducts", HttpMethod.POST,productFilterHolder, ProductFeedModel[].class,false,jwt)).toList();
    }

    @SneakyThrows
    public Long loadProductPageCount(ProductFilterHolder productFilterHolder){
        return httpCallLogic.HttpCall("product/getPages", HttpMethod.POST,productFilterHolder, Long.class,false);
    }


    @SneakyThrows
    public void removeProductById(Long id){
         httpCallLogic.checkResponse(
                 httpCallLogic.HttpCall("product/removeProduct", HttpMethod.POST, id, ErrorResponse.class,false),"Products/1",success,true);
    }


    @SneakyThrows
    public List<OrderAddProducts> getProductsForAddOrder(){
        return Arrays.stream(httpCallLogic.HttpCall("product/getProductsForAddOrder", HttpMethod.GET, null, OrderAddProducts[].class,false)).toList();
    }

    @SneakyThrows
    public List<OrderAddProducts> getExisitingData(Long id){
        return Arrays.stream(httpCallLogic.HttpCall("product/getExistingData", HttpMethod.GET, id, OrderAddProducts[].class,true)).toList();
    }


    @SneakyThrows
    public MiniStatHolder getMiniStats(LocalDate from, LocalDate to){
        return httpCallLogic.HttpCall("product/getProductMiniStats", HttpMethod.GET, String.format("%s/%s",from,to), MiniStatHolder.class,true);
    }

    // report calls

    @SneakyThrows
    public ReportMiniStatHolder getMiniStatsProduct(LocalDate from, LocalDate to, String jwt){
        return httpCallLogic.HttpCallWithJwt("product/getMiniStats", HttpMethod.GET, String.format("%s/%s",from,to), ReportMiniStatHolder.class,true,jwt);
    }

    @SneakyThrows
    public List<GraphDataLongValue> getTopProducts(LocalDate from, LocalDate to,int amountOfData, String jwt){
        return Arrays.stream(httpCallLogic.HttpCallWithJwt("product/getTopProducts", HttpMethod.GET, String.format("%s/%s/%d",from,to,amountOfData), GraphDataLongValue[].class,true,jwt)).toList();
    }

    @SneakyThrows
    public List<ProductReportPieChart> getPieChartProductReport(LocalDate from, LocalDate to, String jwt){
        return Arrays.stream(httpCallLogic.HttpCallWithJwt("product/getPieChartProductReport", HttpMethod.GET, String.format("%s/%s",from,to), ProductReportPieChart[].class,true,jwt)).toList();
    }

    @SneakyThrows
    public List<ProductLowStockList> getLowStockAlerts(LocalDate from, LocalDate to, String jwt){
        return Arrays.stream(httpCallLogic.HttpCallWithJwt("product/getLowStockAlerts", HttpMethod.GET, String.format("%s/%s",from,to), ProductLowStockList[].class,true,jwt)).toList();
    }

    @SneakyThrows
    public List<ProductPerformanceReport> getProductPerformance(LocalDate from, LocalDate to, String jwt){
        return Arrays.stream(httpCallLogic.HttpCallWithJwt("product/getProductPerformance", HttpMethod.GET, String.format("%s/%s",from,to), ProductPerformanceReport[].class,true,jwt)).toList();
    }



}
