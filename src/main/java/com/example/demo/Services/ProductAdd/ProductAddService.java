package com.example.demo.Services.ProductAdd;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.Entity.Product;
import com.example.demo.DTOS.Error.ErrorResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ProductAddService {

    HttpCallLogic httpCallLogic;
    Consumer<Boolean> success;

    public ProductAddService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    @SneakyThrows
    public void addNewOrder(Product product){

        httpCallLogic.checkResponse(
        httpCallLogic.HttpCall("product/saveProduct", HttpMethod.POST,product, ErrorResponse.class,false),"Products/1",success,true);

    }



}
