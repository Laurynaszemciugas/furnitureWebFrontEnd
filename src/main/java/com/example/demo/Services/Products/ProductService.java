package com.example.demo.Services.Products;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.Common.MiniStatHolder;
import com.example.demo.ControllerModels.Filter.Order.OrderFilterHolder;
import com.example.demo.ControllerModels.Filter.Prodcut.ProductFilterHolder;
import com.example.demo.ControllerModels.Orders.OrderAddProducts;
import com.example.demo.ControllerModels.Orders.OrdersFeedData;
import com.example.demo.ControllerModels.Products.ProductFeedModel;
import com.example.demo.ControllerModels.Products.ProductPageData;
import com.example.demo.ControllerModels.Products.ProductPageMiniStat;
import com.example.demo.Enums.Category;
import com.example.demo.Enums.Stock;
import com.example.demo.Enums.Visibility;
import com.example.demo.ServerDBCall.ProductAdd.ProductAddCall;
import com.example.demo.ServerDBCall.ProductPage.ProductsCall;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {


    HttpCallLogic httpCallLogic;

    public ProductService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    // good for specific data need
    @SneakyThrows
    public List<ProductFeedModel> loadProductFeedModel(ProductFilterHolder productFilterHolder) throws IOException, InterruptedException {
        return Arrays.stream(
                httpCallLogic.HttpCall("product/getProducts", HttpMethod.POST,productFilterHolder, ProductFeedModel[].class,false)).toList();
    }

    @SneakyThrows
    public Long loadProductPageCount(ProductFilterHolder productFilterHolder){
        return httpCallLogic.HttpCall("product/getPages", HttpMethod.POST,productFilterHolder, Long.class,false);
    }


    @SneakyThrows
    public String removeProductById(Long id){
        return httpCallLogic.HttpCall("product/removeProduct", HttpMethod.POST, id, String.class,false);
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


}
