package com.example.demo.Services.ProductEditService;

import com.example.demo.ControllerModels.CommonDtos.ExtraDetails;
import com.example.demo.ControllerModels.CommonDtos.Materials;
import com.example.demo.ControllerModels.CommonDtos.ImagesData;
import com.example.demo.ControllerModels.CommonDtos.Product;
import com.example.demo.Enums.*;
import com.example.demo.ServerDBCall.ProductEdit.ProductEdItCall;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductEditService {

    ProductEdItCall productEdItCall;

    public ProductEditService(ProductEdItCall productEdItCall) {
        this.productEdItCall = productEdItCall;
    }

    @SneakyThrows
    public Product productEditDtoLoad(Long id){

        Product productEditDto = productEdItCall.getProductAccordingToId(id);

        return  productEditDto;

    }


    @SneakyThrows
    public void updateProductEdit(Product product){

        productEdItCall.updateProductEdit(product);

    }












}
