package com.example.demo.Services.ProductEditService;

import com.example.demo.ControllerModels.Common.ImagesData;
import com.example.demo.ControllerModels.ProductEdit.ProductEditDto;
import com.example.demo.Enums.ImageLogic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductEditService {

    public List<ImagesData> productEditDtoLoad(){

        ProductEditDto productEditDto = new ProductEditDto();

        ImagesData imagesData =new ImagesData();
        imagesData.setImageUrl("dataNotFound.png");
        List<ImagesData> list = new ArrayList<>();
        list.add(new ImagesData(null,"","image","dataNotFound.png","yes", ImageLogic.NonMain,null));



        productEditDto.setImages(list);



        return  list;

    }












}
