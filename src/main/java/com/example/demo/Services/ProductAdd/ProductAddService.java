package com.example.demo.Services.ProductAdd;

import com.example.demo.ControllerModels.CommonDtos.ExtraDetails;
import com.example.demo.ControllerModels.CommonDtos.ImagesData;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.*;
import com.example.demo.ServerDBCall.ProductAdd.ProductAddCall;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAddService {

    ProductAddCall productAddCall;

    public ProductAddService(ProductAddCall productAddCall) {
        this.productAddCall = productAddCall;
    }


    public Product productAddDtoLoad(){

        Product productEditDto = new Product();

        return  productEditDto;

    }

}
