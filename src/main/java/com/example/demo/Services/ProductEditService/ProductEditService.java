package com.example.demo.Services.ProductEditService;

import com.example.demo.Common.Logic.HttpCallLogic;
import com.example.demo.ControllerModels.CommonDtos.ExtraDetails;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.ControllerModels.Error.ErrorResponse;
import com.example.demo.Enums.*;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ProductEditService {


    HttpCallLogic httpCallLogic;

    Consumer<Boolean> success;

    public ProductEditService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }


    @SneakyThrows
    public Product productEditDtoLoad(Long id){

              return  httpCallLogic.HttpCall("product/getProductToId", HttpMethod.GET,id, Product.class,true);
    }


    @SneakyThrows
    public void updateProductEdit(Product product){

        httpCallLogic.checkResponse(
                httpCallLogic.HttpCall("product/editProduct", HttpMethod.POST,product, ErrorResponse.class,false),"Products/1",success,true);

    }












}
